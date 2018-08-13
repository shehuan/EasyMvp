package com.shehuan.test.easymvp.net;

import android.content.Context;
import android.widget.Toast;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.shehuan.test.App;
import com.shehuan.test.R;
import com.shehuan.test.easymvp.base.activity.BaseActivity;
import com.shehuan.test.easymvp.net.exception.ResponseException;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<E> implements Observer<E> {
    private WeakReference<Context> wrContext;
    private Disposable disposable;
    private BaseNiceDialog dialog;

    public BaseObserver(Context context, boolean showLoading) {
        wrContext = new WeakReference<>(context);
        if (showLoading) {
            initLoading();
        }
    }

    public BaseObserver() {
        wrContext = new WeakReference<>(App.getContext());
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        showLoading();
    }

    @Override
    public void onNext(E data) {
        hideLoading();
        onSuccess(data);
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        ResponseException responseException = (ResponseException) e;
        Toast.makeText(wrContext.get(), responseException.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

    private void initLoading() {
        dialog = NiceDialog.init()
                .setLayoutId(R.layout.loading_layout)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0);
    }

    private void showLoading() {
        if (dialog != null) {
            dialog.show(((BaseActivity) wrContext.get()).getSupportFragmentManager());
        }
    }

    private void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
