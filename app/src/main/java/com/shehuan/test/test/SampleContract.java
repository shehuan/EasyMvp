package com.shehuan.test.test;

import android.graphics.Bitmap;

import com.shehuan.test.easymvp.base.BaseView;
import com.shehuan.test.easymvp.net.exception.ResponseException;

import java.util.List;

public interface SampleContract {
    interface View extends BaseView {
        void onBannerSuccess(List<BannerBean> data);

        void onBannerError(ResponseException e);

        void onFriendSuccess(List<FriendBean> data);

        void onFriendError(ResponseException e);

        void onDecodeBitmapSuccess(Bitmap bitmap);
    }

    interface Presenter {
        void getBannerData();

        void getFriendData();

        void decodeBitmap();
    }
}
