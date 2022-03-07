package cn.edu.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
public class EvidenceSaveDTO {

    @ApiModelProperty(value = "账户密钥")
    @NotEmpty(message = "账户密钥非空")
    @Length(min = 64, max = 64, message = "账户密钥长度为64")
    private String sk;

    @ApiModelProperty(value = "存证hash（存证hash/存证文件url/存证文本，三选一）")
    @Length(min = 64, max = 64, message = "存证hash长度为64")
    private String evidenceHash;

    @ApiModelProperty(value = "存证文件url（存证hash/存证文件url/存证文本，三选一），适用于需要区块链代为出示原文的场景")
    @URL(message = "url格式错误")
    private String evidenceFileUrl;

    @ApiModelProperty(value = "存证文本（存证hash/存证文件url/存证文本，三选一），适用于需要区块链代为出示原文的场景")
    private String evidenceText;

    @ApiModelProperty(value = "存证名称")
    @Length(max = 10, message = "存证名称长度不超过10")
    private String evidenceName = "";

    @ApiModelProperty(value = "扩展信息")
    @Length(max = 100, message = "扩展信息长度不超过100")
    private String ext = "";
}
