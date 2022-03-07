package cn.edu.server.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EvidenceQueryVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "应用ID")
    private String appId;
    @ApiModelProperty(value = "上传者地址")
    private String owner;

    @ApiModelProperty(value = "区块高度")
    private Long blockHeight;
    @ApiModelProperty(value = "上链时间")
    private Date blockTime;

    @ApiModelProperty(value = "存证ID")
    private String txId;
    @ApiModelProperty(value = "存证hash")
    private String evidenceHash;
    @ApiModelProperty(value = "存证文件url（当dataType=1时显示）")
    private String evidenceFileUrl;
    @ApiModelProperty(value = "存证文本（当dataType=2时显示）")
    private String evidenceText;
    @ApiModelProperty(value = "存证名称")
    private String evidenceName;
    @ApiModelProperty(value = "扩展信息")
    private String ext;
    @ApiModelProperty(value = "存证数据类型：0纯hash 1文件 2文本")
    private Integer dataType;
}
