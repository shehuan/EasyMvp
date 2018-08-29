package com.shehuan.mvp.test.main;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.shehuan.mvp.R;
import com.shehuan.mvp.easymvp.base.activity.BaseMvpActivity;
import com.shehuan.mvp.easymvp.net.exception.ResponseException;
import com.shehuan.mvp.test.model.BannerBean;
import com.shehuan.mvp.test.model.FriendBean;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<SamplePresenterImpl> implements SampleContract.View {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected SamplePresenterImpl initPresenter() {
        return new SamplePresenterImpl(context, this);
    }

    @Override
    protected void loadData() {
        presenter.getBannerData();
        presenter.getFriendData();
        presenter.decodeBitmap();
        presenter.getZipExecuteData();
        presenter.getOrderExecuteData();
    }

    @Override
    protected int initLayoutResID() {
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
        Log.e("DecodeBitmap", "success");
    }

    @Override
    public void onZipExecuteSuccess(String data) {
        Log.e("ZipExecute", "success");
    }

    @Override
    public void onOrderExecuteSuccess(List<FriendBean> data) {
        Log.e("OrderExecute", "success");
    }
}
