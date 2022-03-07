package cn.edu.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EvidenceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 上传者地址
     */
    private String owner;

    /**
     * 区块高度
     */
    private Long blockHeight;

    /**
     * 上链时间
     */
    private Long blockTime;

    /**
     * 存证ID
     */
    private String txId;

    /**
     * 存证hash
     */
    private String evidenceHash;

    /**
     * 存证名称
     */
    private String evidenceName;

    /**
     * 存证数据IPFS地址
     */
    private String dataUrl;

    /**
     * 存证数据类型：0纯hash 1文件 2文本
     */
    private Integer dataType;

    /**
     * 扩展信息
     */
    private String ext;


}
