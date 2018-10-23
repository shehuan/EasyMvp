package com.shehuan.easymvp.base;


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

    /**
     * 保存RxJava绑定关系
     */
    public void addDisposable(Disposable disposable) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 取消单个RxJava绑定
     */
    public void removeDisposable(Disposable disposable) {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.remove(disposable);
        }
    }

    /**
     * 取消当前Presenter的全部RxJava绑定，置空view
     */
    public void detach() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        view = null;
    }
}
