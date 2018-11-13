package com.shehuan.easymvp.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;
    private Unbinder unbinder;

    // 初始化布局文件id
    protected abstract @LayoutRes
    int initLayoutResID();

    // 初始化数据
    protected abstract void initData();

    // 初始化控件
    protected abstract void initView();

    // 页面初始化数据请求、内容加载
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutResID());
        context = this;
        unbinder = ButterKnife.bind(this);

        initData();
        initView();
        loadData();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
