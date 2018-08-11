package com.shehuan.easymvp;

import com.shehuan.easymvp.model.BannerBean;
import com.shehuan.easymvp.mvp.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CommonApis {
    String BASE_URL = "http://www.wanandroid.com/";

    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> banner();
}
