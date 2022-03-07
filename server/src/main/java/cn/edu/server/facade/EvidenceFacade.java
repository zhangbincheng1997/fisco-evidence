package cn.edu.server.facade;

import cn.edu.core.base.GlobalException;
import cn.edu.core.base.Status;
import cn.edu.ipfs.components.IPFSService;
import cn.edu.mbg.entity.EvidenceDetail;
import cn.edu.mbg.service.EvidenceDetailService;
import cn.edu.server.enums.EvidenceType;
import cn.edu.server.pojo.dto.EvidenceSaveDTO;
import cn.edu.server.pojo.dto.page.EvidencePageQuery;
import cn.edu.server.pojo.vo.EvidenceQueryVO;
import cn.edu.server.pojo.vo.EvidenceSaveVO;
import cn.edu.server.pojo.vo.EvidenceTxVO;
import cn.edu.server.service.EvidenceService;
import cn.edu.web.context.AppContext;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EvidenceFacade {

    private final Client client;
    private final CryptoSuite cryptoSuite;
    private final EvidenceDetailService detailService;
    private final EvidenceService evidenceService;
    private final IPFSService ipfsService;

    public EvidenceSaveVO save(EvidenceSaveDTO DTO) {
        // 获取存证信息
        String evidenceHash = DTO.getEvidenceHash();
        String evidenceFileUrl = DTO.getEvidenceFileUrl();
        String evidenceText = DTO.getEvidenceText();
        String evidenceName = DTO.getEvidenceName();
        String ext = DTO.getExt();
        String dataUrl = "";
        EvidenceType dataType = EvidenceType.HASH;
        // 存证文件hash 或 存证文件url 或 存证文本
        if (StringUtils.isEmpty(evidenceHash) && StringUtils.isEmpty(evidenceFileUrl) && StringUtils.isEmpty(evidenceText)) {
            throw new GlobalException(Status.EVIDENCE_SAVE_PARAM_ERROR);
        }
        // 区块链代为出示原文
        byte[] content = null;
        if (StringUtils.isNotEmpty(evidenceFileUrl)) {
            dataType = EvidenceType.FILE;
            content = HttpUtil.downloadBytes(evidenceFileUrl);
        } else if (StringUtils.isNotEmpty(evidenceText)) {
            dataType = EvidenceType.TEXT;
            content = evidenceText.getBytes();
        }
        // IPFS文件上传
        if (content != null) {
            dataUrl = ipfsService.addFile(content);
            evidenceHash = cryptoSuite.getHashImpl().hashBytes(content); // 生成最终存证hash
        }
        // 打印日志
        String appId = AppContext.get();
        log.info("appId: {}, hash: {}", appId, evidenceHash);
        // 存证去重
        boolean isExist = evidenceService.exist(appId, evidenceHash);
        if (isExist) {
            throw new GlobalException(Status.EVIDENCE_EXIST);
        }
        // 存证上链
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair(DTO.getSk());
        EvidenceService service = new EvidenceService(client, keyPair);
        TransactionReceipt receipt = service.save(appId, evidenceHash, evidenceName, dataUrl, dataType.getCode(), ext);
        // 本地存储
        EvidenceDetail evidence = new EvidenceDetail();
        evidence.setAppId(appId);
        evidence.setOwner(keyPair.getAddress());
        evidence.setBlockHeight(Long.valueOf(receipt.getBlockNumber().substring(2), 16));
        evidence.setBlockTime(Long.valueOf(client.getBlockByHash(receipt.getBlockHash(), true).getBlock().getTimestamp().substring(2), 16));
        evidence.setTxId(receipt.getTransactionHash());
        evidence.setEvidenceHash(evidenceHash);
        evidence.setEvidenceName(evidenceName);
        evidence.setDataUrl(dataUrl);
        evidence.setDataType(dataType.getCode());
        evidence.setExt(ext);
        detailService.save(evidence);
        // 返回存证信息
        return Convert.convert(EvidenceSaveVO.class, evidence);
    }

    public List<EvidenceTxVO> txlist(long size) {
        size = size > 10 ? 10 : size;
        size = size < 0 ? 1 : size;
        Page<EvidenceDetail> page = new Page<>(0, size);
        LambdaQueryWrapper<EvidenceDetail> wrapper = Wrappers.<EvidenceDetail>lambdaQuery().orderByDesc(EvidenceDetail::getId);
        detailService.page(page, wrapper);
        return Convert.convert(new TypeReference<List<EvidenceTxVO>>() {
        }, page.getRecords());
    }

    // 直接查询数据库，不保真
    @SuppressWarnings("unchecked")
    public Page<EvidenceQueryVO> queryPage(EvidencePageQuery query) {
        Page<EvidenceDetail> page = new Page<>(query.getPage(), query.getLimit());
        LambdaQueryWrapper<EvidenceDetail> wrapper = Wrappers.<EvidenceDetail>lambdaQuery()
                .eq(EvidenceDetail::getAppId, AppContext.get()) // 限制AppId
                .eq(query.getOwner() != null, EvidenceDetail::getOwner, query.getOwner())
                .eq(query.getType() != null, EvidenceDetail::getDataType, query.getType())
                .eq(StringUtils.isNotEmpty(query.getKeyword()), EvidenceDetail::getEvidenceHash, query.getKeyword())
                .or().like(StringUtils.isNotEmpty(query.getKeyword()), EvidenceDetail::getEvidenceName, query.getKeyword())
                .ge(query.getStart() != null, EvidenceDetail::getBlockTime, query.getStart())
                .le(query.getEnd() != null, EvidenceDetail::getBlockTime, query.getEnd())
                .orderByDesc(query.isSort(), EvidenceDetail::getId);
        detailService.page(page, wrapper);
        List<EvidenceQueryVO> resultList = page.getRecords()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return new Page<EvidenceQueryVO>().setRecords(resultList).setTotal(page.getTotal());
    }

    private EvidenceQueryVO convert(EvidenceDetail evidence) {
        EvidenceQueryVO item = Convert.convert(EvidenceQueryVO.class, evidence);
        if (EvidenceType.FILE == EvidenceType.of(evidence.getDataType())) {
            item.setEvidenceFileUrl(ipfsService.getGateway() + "/" + evidence.getDataUrl());
        } else if (EvidenceType.TEXT == EvidenceType.of(evidence.getDataType())) {
            item.setEvidenceText(new String(ipfsService.catFile(evidence.getDataUrl())));
        }
        return item;
    }

    public EvidenceQueryVO queryByTxId(String txId) {
        // 交易哈希 -> 区块链事件日志 -> appId、hash
        EvidenceService.EvidenceSaveEvent saveEvent = evidenceService.getEvidenceSaveEvent(txId);
        String appId = saveEvent.getAppId();
        String hash = saveEvent.getHash();
        return query(appId, hash);
    }

    public EvidenceQueryVO queryByHash(String hash) {
        return query(AppContext.get(), hash);
    }

    public EvidenceQueryVO query(String appId, String hash) {
        // 先查区块链
        EvidenceService.EV ev = evidenceService.query(appId, hash);
        if (ev == null) {
            throw new GlobalException(Status.EVIDENCE_NOT_EXIST);
        }
        // 再查数据库
        EvidenceDetail evidence = detailService.getOne(Wrappers.<EvidenceDetail>lambdaQuery()
                .eq(EvidenceDetail::getAppId, appId)
                .eq(EvidenceDetail::getEvidenceHash, hash));
        // 确认数据库和区块链信息一致
        if (evidence == null || !Objects.equals(evidence.getEvidenceHash(), ev.getHash())
                || !Objects.equals(evidence.getEvidenceName(), ev.getName())
                || !Objects.equals(evidence.getDataUrl(), ev.getDataUrl())
                || !Objects.equals(evidence.getDataType(), ev.getDataType())
                || !Objects.equals(evidence.getExt(), ev.getExt())) {
            throw new GlobalException(Status.EVIDENCE_VERIFY_ERROR);
        }
        return convert(evidence);
    }
}
