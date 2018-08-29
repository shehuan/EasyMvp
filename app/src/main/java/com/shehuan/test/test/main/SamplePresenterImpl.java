package com.shehuan.test.test.main;

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
import com.shehuan.test.test.CommonApis;
import com.shehuan.test.test.model.BannerBean;
import com.shehuan.test.test.model.FriendBean;

import java.util.List;

import io.reactivex.Observable;


public class SamplePresenterImpl extends BasePresenter<SampleContract.View> implements SampleContract.Presenter {
    public SamplePresenterImpl(Context context, SampleContract.View view) {
        super(context, view);
    }

    @Override
    public void getBannerData() {
        RequestManager.getInstance().execute(this, RetrofitManager.getInstance().create(CommonApis.class).banner(),
                new BaseObserver<List<BannerBean>>(context, true, true) {
                    @Override
                    protected void onSuccess(List<BannerBean> data) {
                        view.onBannerSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {
                        view.onBannerError(e);
                    }
                });
    }

    @Override
    public void getFriendData() {
        RequestManager.getInstance().execute(this, RetrofitManager.getInstance().create(CommonApis.class).friend(),
                new BaseObserver<List<FriendBean>>(true) {
                    @Override
                    protected void onSuccess(List<FriendBean> data) {
                        view.onFriendSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {
                        view.onFriendError(e);
                    }
                });
    }

    @Override
    public void decodeBitmap() {
        RequestManager.getInstance().commonExecute(this, new RequestManager.ExecuteListener<Bitmap>() {
            @Override
            public Bitmap onExecute() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return BitmapFactory.decodeResource(context.getResources(), R.mipmap.cat);
            }
        }, new BaseObserver<Bitmap>(context, true, true) {
            @Override
            protected void onSuccess(Bitmap data) {
                view.onDecodeBitmapSuccess(data);
            }

            @Override
            protected void onError(ResponseException e) {

            }
        });
    }

    @Override
    public void getZipExecuteData() {
        RequestManager.getInstance().zipExecute(this, RetrofitManager.getInstance().create(CommonApis.class).banner(),
                RetrofitManager.getInstance().create(CommonApis.class).friend(),
                new RequestManager.ZipExecuteListener<List<BannerBean>, List<FriendBean>, String>() {
                    @Override
                    public String onExecuteResult(BaseResponse<List<BannerBean>> baseResponse1, BaseResponse<List<FriendBean>> baseResponse2) {

                        return baseResponse1.getData().size() + "#" + baseResponse2.getData().size();
                    }
                },
                new BaseObserver<String>(true) {
                    @Override
                    protected void onSuccess(String data) {
                        view.onZipExecuteSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {

                    }
                });
    }

    @Override
    public void getOrderExecuteData() {
        RequestManager.getInstance().orderExecute(this, RetrofitManager.getInstance().create(CommonApis.class).banner(),
                new RequestManager.OrderExecuteListener<List<BannerBean>, List<FriendBean>>() {
                    @Override
                    public Observable<BaseResponse<List<FriendBean>>> onExecuteResult(BaseResponse<List<BannerBean>> baseResponse) {
                        return RetrofitManager.getInstance().create(CommonApis.class).friend();
                    }
                }, new BaseObserver<List<FriendBean>>(true) {
                    @Override
                    protected void onSuccess(List<FriendBean> data) {
                        view.onOrderExecuteSuccess(data);
                    }

                    @Override
                    protected void onError(ResponseException e) {

                    }
                });
    }
}
