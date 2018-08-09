package com.shehuan.easymvp.mvp.net.exceptions;

public class ResponseException extends Exception {
    public String code;
    public String message;

    public ResponseException(Throwable throwable, int code) {
        super(throwable);
        this.code = String.valueOf(code);
    }
}
