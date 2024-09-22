package com.strr.base.model;

/**
 * 结果
 */
public class Result<T> {
    private static final int SUCCESS_CODE = 200;
    private static final int FAIL_CODE = 500;

    private Integer code;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> error() {
        return new Result<T>().code(FAIL_CODE);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>().code(FAIL_CODE).msg(msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<T>().code(code).msg(msg);
    }

    public static <T> Result<T> ok() {
        return new Result<T>().code(SUCCESS_CODE);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>().code(SUCCESS_CODE).data(data);
    }
}
