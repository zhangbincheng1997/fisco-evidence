package cn.edu.server.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EvidenceTxVO {

    @ApiModelProperty(value = "区块高度")
    private Long blockHeight;
    @ApiModelProperty(value = "上链时间")
    private Date blockTime;

    @ApiModelProperty(value = "存证ID")
    private String txId;
    @ApiModelProperty(value = "存证hash")
    private String evidenceHash;
}
