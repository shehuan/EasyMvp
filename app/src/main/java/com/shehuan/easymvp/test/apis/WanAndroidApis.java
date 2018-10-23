package com.shehuan.easymvp.test.apis;

import com.shehuan.easymvp.base.BaseResponse;
import com.shehuan.easymvp.test.Url;
import com.shehuan.easymvp.test.bean.BannerBean;
import com.shehuan.easymvp.test.bean.FriendBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WanAndroidApis {
    String BASE_URL = Url.WAN_ANDROID_UTL;

    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> banner();

    @GET("friend/json")
    Observable<BaseResponse<List<FriendBean>>> friend();
}
