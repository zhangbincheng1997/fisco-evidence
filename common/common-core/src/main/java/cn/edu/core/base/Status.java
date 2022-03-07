package cn.edu.core.base;

import lombok.Getter;

@Getter
public enum Status {

    // 服务器
    SUCCESS(0, "SUCCESS"),
    FAILURE(250, "FAILURE"),
    FORBIDDEN(403, "拒绝执行"),
    NOT_FOUND(404, "请求失败"),
    BIND_EXCEPTION(999, "参数错误"),

    // 工具
    FILE_UPLOAD_ERROR(2000, "IPFS上传失败！"),
    FILE_DOWNLOAD_ERROR(2001, "IPFS下载失败！"),

    // 接口安全
    TOKEN_NOT_FOUND(10000, "缺少参数：accessToken"),
    TOKEN_INVALID(10001, "accessToken无效！"),
    APP_ID_ERROR(10010, "应用ID错误！"),
    APP_SECRET_ERROR(10011, "应用密钥错误！"),
    APP_DISABLE(10012, "应用不可用！"),

    // FISCO
    FISCO_ERROR(20000, "FISCO：操作失败！"),
    FISCO_CRUD_ERROR(20001, "FISCO：CRUD操作失败！"),
    FISCO_DECODE_FAILED(20002, "FISCO：事件日志解析失败！"),

    // 存证
    EVIDENCE_SAVE_PARAM_ERROR(30000, "存证hash/存证文件url/存证文本，三选一！"),
    EVIDENCE_EXIST(30001, "存证已经存在区块链！"),
    EVIDENCE_NOT_EXIST(30002, "存证不存在区块链！"),
    EVIDENCE_VERIFY_ERROR(30003, "数据库和区块链信息不一致，可能发生篡改！"),

    ;

    private final Integer code;
    private final String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
