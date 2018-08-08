package com.shehuan.easymvp.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


import com.shehuan.easymvp.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMvpActivity extends BaseActivity {

    protected List<BasePresenter> mPresenters = new ArrayList<>();

    protected abstract void fetchData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchData();
    }

    protected void addPresenter(BasePresenter presenter) {
        mPresenters.add(presenter);
    }

    @Override
    protected void onDestroy() {
        for (BasePresenter p : mPresenters) {
            p.detach();
        }
        super.onDestroy();
    }
}
