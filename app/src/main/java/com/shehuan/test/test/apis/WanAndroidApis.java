package com.shehuan.test.test.apis;

import com.shehuan.test.easymvp.base.BaseResponse;
import com.shehuan.test.test.Url;
import com.shehuan.test.test.model.BannerBean;
import com.shehuan.test.test.model.FriendBean;

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
