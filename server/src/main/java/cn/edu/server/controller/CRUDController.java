package cn.edu.server.controller;

import cn.edu.core.base.GlobalException;
import cn.edu.core.base.Result;
import cn.edu.core.base.Status;
import cn.edu.core.utils.Constants;
import cn.edu.server.pojo.dto.InitDTO;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.precompiled.crud.TableCRUDService;
import org.fisco.bcos.sdk.contract.precompiled.crud.common.Entry;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.model.RetCode;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Validated
@Api(tags = "【区块链CRUD服务】")
@RestController // 单个方法参数检查
@RequestMapping(Constants.API + "/crud")
@AllArgsConstructor
public class CRUDController {

    private final Client client;
    private final TableCRUDService crudService;
    private final CryptoSuite cryptoSuite;

    @ApiOperation("【创建】")
    @PostMapping("/create")
    public Result<TransactionReceipt> create(@RequestBody @Valid InitDTO DTO) throws ContractException {
        RetCode retCode = crudService.createTable(DTO.getTableName(), DTO.getKeyFieldName(), DTO.getValueFields());
        return Result.success(retCode.getTransactionReceipt());
    }

    @ApiOperation("【描述】")
    @GetMapping("/desc")
    public Result<List<Map<String, String>>> desc(@RequestParam("tableName") String tableName) throws ContractException {
        List<Map<String, String>> list = crudService.desc(tableName);
        return Result.success(list);
    }

    @ApiOperation("【插入】")
    @PostMapping("/insert")
    public Result<TransactionReceipt> insert(@RequestParam("sk") @Length(min = 64, max = 64, message = "密钥长度为64") String sk,
                                             @RequestParam("tableName") String tableName,
                                             @RequestParam("key") String key,
                                             @RequestBody Object object) throws ContractException {
        TableCRUDService service = new TableCRUDService(client, cryptoSuite.createKeyPair(sk));
        RetCode retCode = service.insert(tableName, key, new Entry(Convert.convert(new TypeReference<Map<String, String>>() {
        }, object)));
        if (!retCode.getMessage().equalsIgnoreCase("success")) {
            throw new GlobalException(Status.FISCO_CRUD_ERROR.getCode(), retCode.getMessage());
        }
        return Result.success(retCode.getTransactionReceipt());
    }

    @ApiOperation("【更新】")
    @PostMapping("/update")
    public Result<TransactionReceipt> update(@RequestParam("sk") @Length(min = 64, max = 64, message = "密钥长度为64") String sk,
                                             @RequestParam("tableName") String tableName,
                                             @RequestParam("key") String key,
                                             @RequestBody Object object) throws ContractException {
        TableCRUDService service = new TableCRUDService(client, cryptoSuite.createKeyPair(sk));
        RetCode retCode = service.update(tableName, key, new Entry(Convert.convert(new TypeReference<Map<String, String>>() {
        }, object)), null);
        if (!retCode.getMessage().equalsIgnoreCase("success")) {
            throw new GlobalException(Status.FISCO_CRUD_ERROR.getCode(), retCode.getMessage());
        }
        return Result.success(retCode.getTransactionReceipt());
    }

    @ApiOperation("【删除】")
    @PostMapping("/remove")
    public Result<TransactionReceipt> remove(@RequestParam("sk") @Length(min = 64, max = 64, message = "密钥长度为64") String sk,
                                             @RequestParam("tableName") String tableName,
                                             @RequestParam("key") String key) throws ContractException {
        TableCRUDService service = new TableCRUDService(client, cryptoSuite.createKeyPair(sk));
        RetCode retCode = service.remove(tableName, key, null);
        if (!retCode.getMessage().equalsIgnoreCase("success")) {
            throw new GlobalException(Status.FISCO_CRUD_ERROR.getCode(), retCode.getMessage());
        }
        return Result.success(retCode.getTransactionReceipt());
    }

    @ApiOperation("【查询】")
    @GetMapping("/select")
    public Result<Object> select(@RequestParam("tableName") String tableName,
                                 @RequestParam("key") String key) throws ContractException {
        List<Map<String, String>> list = crudService.select(tableName, key, null);
        return Result.success(list);
    }
}
