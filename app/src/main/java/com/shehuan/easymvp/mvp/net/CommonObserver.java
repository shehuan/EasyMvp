package com.shehuan.easymvp.mvp.net;

import android.content.Context;
import android.widget.Toast;

import com.shehuan.easymvp.App;
import com.shehuan.easymvp.mvp.net.exceptions.ResponseException;

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
        ResponseException responseException = (ResponseException) e;
        Toast.makeText(mContext, responseException.message, Toast.LENGTH_SHORT).show();
        _onError(responseException.code);
    }

    @Override
    public void onComplete() {

    }

    public Disposable getDisposable() {
        return disposable;
    }

    protected abstract void _onNext(E t);

    protected abstract void _onError(String code);
}
