package cn.edu.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageQuery extends BasePageQuery {

    @ApiModelProperty(value = "false升序/true降序，默认false")
    private boolean sort;

    @ApiModelProperty(value = "关键词")
    private String keyword;
}

