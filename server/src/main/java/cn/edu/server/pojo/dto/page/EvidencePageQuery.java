package cn.edu.server.pojo.dto.page;

import cn.edu.core.dto.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(callSuper = true)
public class EvidencePageQuery extends PageQuery {

    @ApiModelProperty(value = "上传者地址")
    @Length(min = 42, max = 42, message = "上传者地址长度为42")
    private String owner;

    @ApiModelProperty(value = "存证类型：0纯hash/1文件/2文本，不填表示全部", example = "0")
    private Integer type;

    @ApiModelProperty(value = "存证开始时间戳（毫秒）", example = "0")
    private Long start;

    @ApiModelProperty(value = "存证结束时间戳（毫秒）", example = "0")
    private Long end;
}
