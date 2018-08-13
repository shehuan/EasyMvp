package com.shehuan.test.test;

import com.shehuan.test.easymvp.base.BaseView;
import com.shehuan.test.easymvp.net.exception.ResponseException;

import java.util.List;

public interface SampleContract {
    interface View extends BaseView {
        void onBannerSuccess(List<BannerBean> data);

        void onBannerError(ResponseException e);

        void onFriendSuccess(List<FriendBean> data);

        void onFriendError(ResponseException e);
    }

    interface Presenter {
        void getBannerData();

        void getFriendData();
    }
}
