package com.shehuan.easymvp.base.net;

import android.widget.Toast;

import com.shehuan.easymvp.App;
import com.shehuan.easymvp.base.net.exception.ResponseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<E> implements Observer<E> {
    private Disposable disposable;

    private boolean showErrorTip;

    /**
     * @param showErrorTip 发生异常时，是否使用Toast提示
     */
    public BaseObserver(boolean showErrorTip) {
        this.showErrorTip = showErrorTip;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(E data) {
        onSuccess(data);
    }

    @Override
    public void onError(Throwable e) {
        ResponseException responseException = (ResponseException) e;
        if (showErrorTip) {
            Toast.makeText(App.getContext(), responseException.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
        onError(responseException);
    }

    @Override
    public void onComplete() {

    }

    public Disposable getDisposable() {
        return disposable;
    }

    protected abstract void onSuccess(E data);

    protected abstract void onError(ResponseException e);
}
