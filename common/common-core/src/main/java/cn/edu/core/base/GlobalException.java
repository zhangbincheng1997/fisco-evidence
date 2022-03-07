package cn.edu.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    private Integer code;
    private String message;

    public GlobalException(Status status) {
        super("GlobalException{code=" + status.getCode() + ", message=" + status.getMessage() + "}");
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public GlobalException(Integer code, String message) {
        super("GlobalException{code=" + code + ", message=" + message + "}");
        this.code = code;
        this.message = message;
    }
}