package com.shehuan.test.easymvp.net;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final int DEFAULT_TIMEOUT = 10;

    private OkHttpClient okHttpClient;

    private RetrofitManager() {

    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    /**
     * 根据不同的ApiService接口创建ApiService对象
     *
     * @param service
     * @param <S>
     * @return
     */
    public <S> S create(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient(true))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl(service))
                .build();
        return retrofit.create(service);
    }

    /**
     * 解析接口中的BASE_URL，解决BASE_URL不一致的问题，如果所有的BASE_URL都一致，则可不用该方法
     *
     * @param service
     * @param <S>
     * @return
     */
    private <S> String getBaseUrl(Class<S> service) {

        try {
            Field field = service.getField("BASE_URL");
            return (String) field.get(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * OkHttp拦截器相关配置
     *
     * @param flag 是否打印响应日志
     * @return
     */
    private OkHttpClient getOkHttpClient(boolean flag) {
        if (okHttpClient == null) {
            //配置超时拦截器
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            if (flag) {
                //配置log打印拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(loggingInterceptor);
            }

            //请求相应拦截器
//            builder.addInterceptor(new CommonInterceptor());

            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
