package cn.edu.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    @ApiModelProperty(value = "用户名称")
    @NotEmpty(message = "用户名称非空")
    private String userName;

    @ApiModelProperty(value = "扩展信息")
    private String ext = "";
}
