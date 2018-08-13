package com.shehuan.test.test;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.shehuan.test.R;
import com.shehuan.test.easymvp.base.activity.BaseMvpActivity;
import com.shehuan.test.easymvp.net.exception.ResponseException;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<SamplePresenterImpl> implements SampleContract.View {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected void fetchData() {
        mPresenter = new SamplePresenterImpl(mContext, this);
        mPresenter.getBannerData();
        mPresenter.getFriendData();
        mPresenter.decodeBitmap();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onBannerSuccess(List<BannerBean> data) {
        Log.e("banner", "success");
    }

    @Override
    public void onBannerError(ResponseException e) {
        Log.e("banner", "error");
    }

    @Override
    public void onFriendSuccess(List<FriendBean> data) {
        Log.e("friend", "success");
    }

    @Override
    public void onFriendError(ResponseException e) {
        Log.e("friend", "error");
    }

    @Override
    public void onDecodeBitmapSuccess(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
