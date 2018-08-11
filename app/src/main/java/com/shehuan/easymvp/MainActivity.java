package com.shehuan.easymvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shehuan.easymvp.model.BannerBean;
import com.shehuan.easymvp.mvp.net.CommonObserver;
import com.shehuan.easymvp.mvp.net.RequestManager;
import com.shehuan.easymvp.mvp.net.RetrofitManager;
import com.shehuan.easymvp.mvp.net.exceptions.ResponseException;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Disposable disposable = RequestManager.getInstance()
                .execute(RetrofitManager.getInstance().create(CommonApis.class).banner(), new CommonObserver<List<BannerBean>>() {
                    @Override
                    protected void onSuccess(List<BannerBean> data) {
                        Log.e("mvp_test_success", data.toString());
                    }

                    @Override
                    protected void onError(ResponseException exception) {
                        Log.e("mvp_test_error", exception.toString());
                    }
                });


    }
}
