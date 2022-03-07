package cn.edu.core.base;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;
    private Integer total;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message, T data, Integer total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public Result() {
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getMessage();
    }

    public static <T> Result<T> success() {
        return new Result<>(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(T data, Long total) {
        return new Result<>(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage(), data, total.intValue());
    }

    public static <T> Result<T> failure() {
        return new Result<>(Status.FAILURE.getCode(), Status.FAILURE.getMessage(), null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(Status.FAILURE.getCode(), message, null);
    }

    public static <T> Result<T> failure(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> failure(Status status) {
        return new Result<>(status.getCode(), status.getMessage(), null);
    }

    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failure();
        }
    }

    public Result<T> valid() {
        if (!Status.SUCCESS.getCode().equals(this.code)) {
            throw new GlobalException(this.code, this.message);
        }
        return this;
    }
}
