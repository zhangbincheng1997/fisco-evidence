package cn.edu.server.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class InitDTO {

    @ApiModelProperty(value = "表名", required = true)
    @NotEmpty(message = "表名非空")
    private String tableName;

    @ApiModelProperty(value = "主键名", required = true)
    @NotEmpty(message = "主键名非空")
    private String keyFieldName;

    @ApiModelProperty(value = "字段名列表", required = true)
    @NotEmpty(message = "字段名列表非空")
    private List<String> valueFields;
}
