package cn.edu.web.model;

import lombok.Data;

@Data
public class Log {

    /**
     * IP
     */
    private String ip;

    /**
     * URI
     */
    private String uri;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 操作APP
     */
    private String app;

    /**
     * 类名
     */
    private Object className;

    /**
     * 方法名
     */
    private Object methodName;

    /**
     * 请求参数名
     */
    private Object parameterNames;

    /**
     * 请求参数值
     */
    private Object argList;
}
