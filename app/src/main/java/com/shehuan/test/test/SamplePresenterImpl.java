package com.shehuan.test.test;

import android.content.Context;

import com.shehuan.test.easymvp.base.BasePresenter;
import com.shehuan.test.easymvp.net.BaseObserver;
import com.shehuan.test.easymvp.net.RequestManager;
import com.shehuan.test.easymvp.net.RetrofitManager;
import com.shehuan.test.easymvp.net.exception.ResponseException;


import java.util.List;

import io.reactivex.disposables.Disposable;


public class SamplePresenterImpl extends BasePresenter<SampleContract.View> implements SampleContract.Presenter {
    public SamplePresenterImpl(Context context, SampleContract.View view) {
        super(context, view);
    }

    @Override
    public void getBannerData() {
        Disposable disposable = RequestManager.getInstance()
                .execute(RetrofitManager.getInstance().create(CommonApis.class).banner(), new BaseObserver<List<BannerBean>>(mContext, true) {
                    @Override
                    protected void onSuccess(List<BannerBean> data) {
                        mView.onBannerSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {
                        mView.onBannerError(e);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getFriendData() {
        Disposable disposable = RequestManager.getInstance()
                .execute(RetrofitManager.getInstance().create(CommonApis.class).friend(), new BaseObserver<List<FriendBean>>(mContext, true) {
                    @Override
                    protected void onSuccess(List<FriendBean> data) {
                        mView.onFriendSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {
                        mView.onFriendError(e);
                    }
                });
        addDisposable(disposable);
    }
}
