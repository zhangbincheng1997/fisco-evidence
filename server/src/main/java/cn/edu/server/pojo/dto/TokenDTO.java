package cn.edu.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TokenDTO {

    @ApiModelProperty(value = "应用ID")
    @NotEmpty(message = "应用ID非空")
    private String appId;

    @ApiModelProperty(value = "应用密钥")
    @NotEmpty(message = "应用密钥非空")
    private String appSecret;
}
