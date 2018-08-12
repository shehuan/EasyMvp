package com.shehuan.test.easymvp.base;


import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseView> {
    protected V mView;
    protected Context mContext;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(Context context, V view) {
        mView = view;
        mContext = context;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    public void detach() {
        mView = null;
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
