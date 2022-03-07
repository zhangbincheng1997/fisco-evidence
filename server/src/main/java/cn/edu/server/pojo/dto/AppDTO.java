package cn.edu.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AppDTO {

    @ApiModelProperty(value = "应用名称")
    @NotEmpty(message = "应用名称非空")
    private String appName;

    @ApiModelProperty(value = "拓展信息")
    private String ext;
}
