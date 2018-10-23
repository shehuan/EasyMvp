package com.shehuan.easymvp.base.net;

import android.content.Context;
import android.widget.Toast;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.shehuan.easymvp.App;
import com.shehuan.easymvp.R;
import com.shehuan.easymvp.base.activity.BaseActivity;
import com.shehuan.easymvp.base.net.exception.ResponseException;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<E> implements Observer<E> {
    private WeakReference<Context> wrContext;
    private Disposable disposable;
    private BaseNiceDialog dialog;

    private boolean showErrorTip;

    /**
     * @param context      由于loading通过DialogFragment实现，无法使用Application Context，需要使用Activity Context
     * @param showLoading  是否显示加载中loading
     * @param showErrorTip 发生异常时，是否使用Toast提示
     */
    public BaseObserver(Context context, boolean showLoading, boolean showErrorTip) {
        wrContext = new WeakReference<>(context);
        this.showErrorTip = showErrorTip;
        if (showLoading) {
            initLoading();
        }
    }

    public BaseObserver(boolean showErrorTip) {
        this.showErrorTip = showErrorTip;
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
        if (showErrorTip) {
            Toast.makeText(wrContext.get(), responseException.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

    /**
     * 初始化loading
     */
    private void initLoading() {
        dialog = NiceDialog.init()
                .setLayoutId(R.layout.loading_layout)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0);
    }

    /**
     * 显示loading
     */
    private void showLoading() {
        if (dialog != null) {
            dialog.show(((BaseActivity) wrContext.get()).getSupportFragmentManager());
        }
    }

    /**
     * 取消loading
     */
    private void hideLoading() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
