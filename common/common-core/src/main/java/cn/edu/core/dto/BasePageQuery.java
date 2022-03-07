package cn.edu.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BasePageQuery {

    @ApiModelProperty(value = "页面，默认1", example = "1")
    private Integer page = 1;

    @ApiModelProperty(value = "大小，默认10", example = "10")
    private Integer limit = 10;
}
