package com.shehuan.easymvp.test.main;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.shehuan.easymvp.R;
import com.shehuan.easymvp.base.activity.BaseMvpActivity;
import com.shehuan.easymvp.base.net.exception.ResponseException;
import com.shehuan.easymvp.test.bean.BannerBean;
import com.shehuan.easymvp.test.bean.FriendBean;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<SamplePresenterImpl> implements SampleContract.View {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected SamplePresenterImpl initPresenter() {
        return new SamplePresenterImpl(this);
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
