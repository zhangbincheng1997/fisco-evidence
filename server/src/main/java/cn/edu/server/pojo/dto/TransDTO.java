package cn.edu.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransDTO {

    @ApiModelProperty(value = "交易hash")
    private String transHash = "";
}
