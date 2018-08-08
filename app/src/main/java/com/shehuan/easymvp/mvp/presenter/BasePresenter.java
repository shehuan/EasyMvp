package com.shehuan.easymvp.mvp.presenter;

import android.content.Context;

import com.shehuan.easymvp.mvp.iview.IBaseView;

import io.reactivex.disposables.Disposable;


public class BasePresenter<V extends IBaseView> {
    public V mView;
    public Context mContext;
    protected Disposable disposable;

    public BasePresenter(Context context, V view) {
        mView = view;
        mContext = context;
    }

    public void detach() {
        mView = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
