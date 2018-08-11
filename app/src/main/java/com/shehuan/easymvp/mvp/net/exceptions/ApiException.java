package com.shehuan.easymvp.mvp.net.exceptions;


/**
 * 自定义的接口异常类，
 */
public class ApiException extends RuntimeException {

    private String errorCode;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
