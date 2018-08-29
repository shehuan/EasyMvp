package com.shehuan.test.easymvp.net.exception;

/**
 * 自定义的响应异常类
 */
public class ResponseException extends Exception {
    public String errorCode;
    public String errorMessage;

    public ResponseException(Throwable throwable, int errorCode, String errorMessage) {
        super(throwable);
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = errorMessage;
    }

    public ResponseException(Throwable throwable, String errorCode, String errorMessage) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "[" + errorCode + ", " + errorMessage + "]";
    }
}
