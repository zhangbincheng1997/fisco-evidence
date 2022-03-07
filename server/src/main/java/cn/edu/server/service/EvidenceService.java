package cn.edu.server.service;

import cn.edu.core.base.GlobalException;
import cn.edu.core.base.Status;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.utils.Numeric;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.ResourceBundle;

// 交易回执状态 https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/api.html

@Slf4j
@Service
public class EvidenceService {

    public static final String ADDRESS = ResourceBundle.getBundle("contract").getString("contract.evidenceAddress");
    private final Client client;
    private final EvidenceFactory evidenceFactory;

    public EvidenceService(Client client, CryptoKeyPair credential) {
        this.client = client;
        this.evidenceFactory = new EvidenceFactory(ADDRESS, client, credential);
    }

    public TransactionReceipt createApp(String appId) {
        TransactionReceipt receipt = evidenceFactory.createApp(appId);
        log.info(JSON.toJSONString(receipt));
        if (!receipt.isStatusOK()) {
            throw new GlobalException(Status.FISCO_ERROR.getCode(), receipt.getStatusMsg());
        }
        return receipt;
    }

    public TransactionReceipt createUser(String appId, String address) {
        TransactionReceipt receipt = evidenceFactory.createUser(appId, address);
        log.info(JSON.toJSONString(receipt));
        if (!receipt.isStatusOK()) {
            throw new GlobalException(Status.FISCO_ERROR.getCode(), receipt.getStatusMsg());
        }
        return receipt;
    }

    public TransactionReceipt save(String appId, String hash, String name, String dataUrl, Integer dataType, String ext) {
        byte[] hashBytes32 = Numeric.hexStringToByteArray(hash);
        BigInteger _dataType = BigInteger.valueOf(dataType);
        TransactionReceipt receipt = evidenceFactory.save(appId, hashBytes32, name, dataUrl, _dataType, ext);
        log.info(JSON.toJSONString(receipt));
        if (!receipt.isStatusOK()) {
            throw new GlobalException(Status.FISCO_ERROR.getCode(), receipt.getStatusMsg());
        }
        return receipt;
    }

    public EV query(String appId, String hash) {
        byte[] hashBytes32 = Numeric.hexStringToByteArray(hash);
        try {
            Tuple5<byte[], String, String, BigInteger, String> tuple = evidenceFactory.query(appId, hashBytes32);
            return new EV(tuple);
        } catch (ContractException e) {
            throw new GlobalException(Status.FISCO_ERROR.getCode(), e.getMessage());
        }
    }

    public boolean exist(String appId, String hash) {
        byte[] hashBytes32 = Numeric.hexStringToByteArray(hash);
        try {
            Tuple5<byte[], String, String, BigInteger, String> tuple = evidenceFactory.query(appId, hashBytes32);
            return true;
        } catch (ContractException e) {
            return false;
        }
    }

    public EvidenceSaveEvent getEvidenceSaveEvent(String transHash) {
        // 获取交易回执
        TransactionReceipt receipt = client.getTransactionReceipt(transHash).getTransactionReceipt().orElse(null);
        // 交易不存在
        if (receipt == null) throw new GlobalException(Status.EVIDENCE_NOT_EXIST);
        // 交易失败
        if (!receipt.isStatusOK()) throw new GlobalException(Status.EVIDENCE_NOT_EXIST);
        // 不是存证服务
        if (!receipt.getTo().equals(EvidenceService.ADDRESS)) throw new GlobalException(Status.EVIDENCE_NOT_EXIST);
        List<EvidenceFactory.EvidenceSaveEventResponse> resultList = evidenceFactory.getEvidenceSaveEvents(receipt);
        // 交易日志不匹配
        if (resultList.size() != 1) throw new GlobalException(Status.EVIDENCE_NOT_EXIST);
        return new EvidenceSaveEvent(resultList.get(0));
    }

    @Data
    public static class EV {
        private String hash;
        private String name;
        private String dataUrl;
        private Integer dataType;
        private String ext;

        public EV(Tuple5<byte[], String, String, BigInteger, String> tuple) {
            this.hash = Numeric.toHexStringNoPrefix(tuple.getValue1());
            this.name = tuple.getValue2();
            this.dataUrl = tuple.getValue3();
            this.dataType = tuple.getValue4().intValue();
            this.ext = tuple.getValue5();
        }
    }

    @Data
    public static class EvidenceSaveEvent {
        private String appId;
        private String hash;

        public EvidenceSaveEvent(EvidenceFactory.EvidenceSaveEventResponse response) {
            this.appId = response.appId;
            this.hash = Numeric.toHexStringNoPrefix(response.hash);
        }
    }
}
