package com.shehuan.test.test;

import com.shehuan.test.easymvp.base.BaseResponse;
import com.shehuan.test.test.model.BannerBean;
import com.shehuan.test.test.model.FriendBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CommonApis {
    String BASE_URL = Url.WAN_ANDROID_UTL;

    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> banner();

    @GET("friend/json")
    Observable<BaseResponse<List<FriendBean>>> friend();
}
