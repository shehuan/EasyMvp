package com.shehuan.easymvp.mvp.net.exceptions;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

public class ExceptionHandler {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseException handle(Throwable e) {
        ResponseException responseException;
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            responseException = new ResponseException(apiException, Integer.valueOf(apiException.getErrorCode()), apiException.getMessage());
            responseException.getMessage();
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    responseException = new ResponseException(e, httpException.code(), "网络连接错误");
                    break;
            }
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            responseException = new ResponseException(e, ERROR.PARSE_ERROR, "解析错误");
        } else if (e instanceof ConnectException) {
            responseException = new ResponseException(e, ERROR.NET_ERROR, "连接失败");
        } else if (e instanceof ConnectTimeoutException || e instanceof java.net.SocketTimeoutException) {
            responseException = new ResponseException(e, ERROR.TIMEOUT_ERROR, "网络连接超时");
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            responseException = new ResponseException(e, ERROR.SSL_ERROR, "证书验证失败");
        } else {
            responseException = new ResponseException(e, ERROR.UNKNOWN_ERROR, "未知错误");
        }
        return responseException;
    }


    /**
     * 约定异常
     */
    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN_ERROR = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NET_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
    }
}
