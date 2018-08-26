package com.shehuan.test.easymvp.base;


import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseView> {
    protected V view;
    protected Context context;
    private CompositeDisposable compositeDisposable;

    public BasePresenter(Context context, V view) {
        this.context = context;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void removeDisposable(Disposable disposable) {
        compositeDisposable.remove(disposable);
    }

    public void clearDisposable() {
        view = null;
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
