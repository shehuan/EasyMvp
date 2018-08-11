package com.shehuan.easymvp.mvp.net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        Request request = chain.request();

        // 添加请求头参数
        if (!request.url().toString().contains("login")) {
            request = buildRequest(request);
        }

        response = chain.proceed(request);
        try {
            ResponseBody responseBody = response.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }

            String bodyString = buffer.clone().readString(charset);
            JSONObject jsonObject = new JSONObject(bodyString);
            String code = jsonObject.getString("code");
            // 根据解析到的响应code,做特殊处理
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Request buildRequest(Request request) {
        Request.Builder requestBuilder = request.newBuilder()
                .header("xxxxx", "yyyyy");
        return requestBuilder.build();
    }
}
