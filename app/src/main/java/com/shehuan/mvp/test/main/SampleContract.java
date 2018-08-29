package com.shehuan.mvp.test.main;

import android.graphics.Bitmap;

import com.shehuan.mvp.easymvp.base.BaseView;
import com.shehuan.mvp.easymvp.net.exception.ResponseException;
import com.shehuan.mvp.test.model.BannerBean;
import com.shehuan.mvp.test.model.FriendBean;

import java.util.List;

public interface SampleContract {
    interface View extends BaseView {
        void onBannerSuccess(List<BannerBean> data);

        void onBannerError(ResponseException e);

        void onFriendSuccess(List<FriendBean> data);

        void onFriendError(ResponseException e);

        void onDecodeBitmapSuccess(Bitmap bitmap);

        void onZipExecuteSuccess(String data);

        void onOrderExecuteSuccess(List<FriendBean> data);
    }

    interface Presenter {
        void getBannerData();

        void getFriendData();

        void decodeBitmap();

        void getZipExecuteData();

        void getOrderExecuteData();
    }
}
