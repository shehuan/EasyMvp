package com.shehuan.easymvp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shehuan.easymvp.base.BasePresenter;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;

    // 初始化Presenter
    protected abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroy();
    }

}
