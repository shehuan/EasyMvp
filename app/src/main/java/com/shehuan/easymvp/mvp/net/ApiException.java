package com.shehuan.easymvp.mvp.net;


import android.text.TextUtils;

public class ApiException extends RuntimeException {

    public ApiException(String code, String exception) {
        super(code + "#" + getExceptionMessage(exception));
    }

    private static String getExceptionMessage(String exception) {
        if (TextUtils.isEmpty(exception)) {
            return "请稍后再试！";
        }
        return exception;
    }
}
