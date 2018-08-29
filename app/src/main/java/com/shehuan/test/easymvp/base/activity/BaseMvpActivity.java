package com.shehuan.test.easymvp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shehuan.test.easymvp.base.BasePresenter;
import com.shehuan.test.easymvp.base.BaseView;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    protected P presenter;

    // 初始化Presenter
    protected abstract P initPresenter();

    // 默认数据请求
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        loadData();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroy();
    }

}
