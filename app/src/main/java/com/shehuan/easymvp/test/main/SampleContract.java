package com.shehuan.easymvp.test.main;

import android.graphics.Bitmap;

import com.shehuan.easymvp.base.BaseView;
import com.shehuan.easymvp.base.net.exception.ResponseException;
import com.shehuan.easymvp.test.bean.BannerBean;
import com.shehuan.easymvp.test.bean.FriendBean;

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
