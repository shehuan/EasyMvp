package com.shehuan.easymvp.mvp.net;

import android.content.Context;
import android.widget.Toast;

import com.shehuan.easymvp.App;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class CommonObserver<E> implements Observer<E> {
    private Context mContext;
    private boolean showLoading;

    private Disposable disposable;

    public CommonObserver(Context context, boolean showLoading) {
        mContext = context;
        this.showLoading = showLoading;
    }

    public CommonObserver() {
        mContext = App.getContext();
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(E data) {
        _onNext(data);
    }

    @Override
    public void onError(Throwable e) {
        String code;
        String message;
        if (e instanceof ApiException) {
            code = e.getMessage().split("#")[0];
            message = e.getMessage().split("#")[1];
        } else {
            code = "-1";
            message = "请检查网络连接或稍后再试！";
        }

        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        _onError(code);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void _onNext(E t);

    protected abstract void _onError(String code);
}
