package cn.edu.core.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class IdsRequest {

    @ApiModelProperty(value = "IDs", example = "0")
    @NotNull(message = "IDs不能为空")
    private List<Long> ids;
}
