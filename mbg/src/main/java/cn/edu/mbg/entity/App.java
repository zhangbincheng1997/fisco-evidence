package cn.edu.mbg.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用密钥（32位）
     */
    private String appSecret;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 扩展信息
     */
    private String ext;

    /**
     * 是否可用
     */
    private Boolean enabled;


}
