package cn.edu.server.controller;

import cn.edu.core.base.Result;
import cn.edu.core.utils.Constants;
import cn.edu.server.facade.EvidenceFacade;
import cn.edu.server.pojo.dto.EvidenceSaveDTO;
import cn.edu.server.pojo.dto.page.EvidencePageQuery;
import cn.edu.server.pojo.vo.EvidenceQueryVO;
import cn.edu.server.pojo.vo.EvidenceSaveVO;
import cn.edu.server.pojo.vo.EvidenceTxVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@Api(tags = "【存证服务】")
@RestController // 单个方法参数检查
@RequestMapping(Constants.API + "/evidence")
@AllArgsConstructor
public class EvidenceController {

    private final EvidenceFacade evidenceFacade;

    @ApiOperation("存证上链")
    @PostMapping("/save")
    public Result<EvidenceSaveVO> save(@RequestBody @Valid EvidenceSaveDTO DTO) {
        return Result.success(evidenceFacade.save(DTO));
    }

    @ApiOperation("实时存证（默认10条记录）")
    @GetMapping("/txlist")
    public Result<List<EvidenceTxVO>> txlist(@RequestParam(value = "size", defaultValue = "10") Integer size) {
        return Result.success(evidenceFacade.txlist(size));
    }

    @ApiOperation("存证查询（分页，不保真）")
    @GetMapping("/queryPage")
    public Result<List<EvidenceQueryVO>> queryPage(@Valid EvidencePageQuery query) {
        Page<EvidenceQueryVO> page = evidenceFacade.queryPage(query);
        return Result.success(page.getRecords(), page.getTotal());
    }

    @ApiOperation("存证查询（存证ID，保真）")
    @GetMapping("/queryByTxId")
    public Result<EvidenceQueryVO> queryByTxId(@RequestParam("txId") String txId) {
        return Result.success(evidenceFacade.queryByTxId(txId));
    }

    @ApiOperation("存证查询（存证Hash，保真）")
    @GetMapping("/queryByHash")
    public Result<EvidenceQueryVO> queryByHash(@RequestParam("hash") String hash) {
        return Result.success(evidenceFacade.queryByHash(hash));
    }
}
