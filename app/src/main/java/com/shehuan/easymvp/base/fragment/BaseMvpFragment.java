package com.shehuan.easymvp.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shehuan.easymvp.base.BasePresenter;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P presenter;

    // 初始化Presenter
    protected abstract P initPresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detach();
        }
        super.onDestroy();
    }
}
