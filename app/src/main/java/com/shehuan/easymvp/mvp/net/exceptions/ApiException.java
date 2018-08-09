package com.shehuan.easymvp.mvp.net.exceptions;


/**
 * 自定义的异常类，
 */
public class ApiException extends RuntimeException {

    private String code;

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
