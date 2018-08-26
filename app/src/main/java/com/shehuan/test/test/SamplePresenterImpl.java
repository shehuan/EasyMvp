package com.shehuan.test.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.shehuan.test.R;
import com.shehuan.test.easymvp.base.BasePresenter;
import com.shehuan.test.easymvp.base.BaseResponse;
import com.shehuan.test.easymvp.net.BaseObserver;
import com.shehuan.test.easymvp.net.RequestManager;
import com.shehuan.test.easymvp.net.RetrofitManager;
import com.shehuan.test.easymvp.net.exception.ResponseException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class SamplePresenterImpl extends BasePresenter<SampleContract.View> implements SampleContract.Presenter {
    public SamplePresenterImpl(Context context, SampleContract.View view) {
        super(context, view);
    }

    @Override
    public void getBannerData() {
        Disposable disposable = RequestManager.getInstance()
                .execute(RetrofitManager.getInstance().create(CommonApis.class).banner(), new BaseObserver<List<BannerBean>>(context, true) {
                    @Override
                    protected void onSuccess(List<BannerBean> data) {
                        view.onBannerSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {
                        view.onBannerError(e);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getFriendData() {
        Disposable disposable = RequestManager.getInstance()
                .execute(RetrofitManager.getInstance().create(CommonApis.class).friend(), new BaseObserver<List<FriendBean>>(context, true) {
                    @Override
                    protected void onSuccess(List<FriendBean> data) {
                        view.onFriendSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {
                        view.onFriendError(e);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void decodeBitmap() {
        Disposable disposable = RequestManager.getInstance().execute(new RequestManager.TaskListener<Bitmap>() {
            @Override
            public Bitmap doTask() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            }
        }, new BaseObserver<Bitmap>() {
            @Override
            protected void onSuccess(Bitmap data) {
                view.onDecodeBitmapSuccess(data);
            }

            @Override
            protected void onError(ResponseException e) {

            }
        });
        addDisposable(disposable);
    }

    @Override
    public void getZipData() {
        RequestManager.getInstance().zipExecute(RetrofitManager.getInstance().create(CommonApis.class).banner(),
                RetrofitManager.getInstance().create(CommonApis.class).friend(), new RequestManager.ZipResultListener<List<BannerBean>, List<FriendBean>, String>() {
                    @Override
                    public String zipResult(BaseResponse<List<BannerBean>> baseResponse1, BaseResponse<List<FriendBean>> baseResponse2) {

                        return baseResponse1.getData().size() + "#" + baseResponse2.getData().size();
                    }
                }, new BaseObserver<String>() {
                    @Override
                    protected void onSuccess(String data) {
                        view.onZipDataSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {

                    }
                });
    }

    @Override
    public void getLinkData() {
        RequestManager.getInstance().linkExecute(RetrofitManager.getInstance().create(CommonApis.class).banner(),
                new RequestManager.LinkListener<List<BannerBean>, List<FriendBean>>() {
                    @Override
                    public Observable<BaseResponse<List<FriendBean>>> link(BaseResponse<List<BannerBean>> baseResponse) {
                        return RetrofitManager.getInstance().create(CommonApis.class).friend();
                    }
                }, new BaseObserver<List<FriendBean>>() {
                    @Override
                    protected void onSuccess(List<FriendBean> data) {
                        view.onLinkSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {

                    }
                });
    }
}
