package com.shehuan.easymvp.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shehuan.easymvp.base.activity.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    public BaseActivity context;
    private Unbinder unbinder;

    private boolean isViewCreated; // 界面是否已创建完成
    protected boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求, isNeedReload()返回false的时起作用
    protected boolean isHidden = true; // 记录当前fragment的是否隐藏

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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        tryLoadData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(initLayoutResID(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.isVisibleToUser = isVisibleToUser;

        tryLoadData();
    }

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        if (!hidden) {
            tryLoadData1();
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof BaseFragment && ((BaseFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseFragment && ((BaseFragment) child).isVisibleToUser) {
                ((BaseFragment) child).tryLoadData();
            }
        }
    }

    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     *
     * @return
     */
    protected boolean isNeedReload() {
        return false;
    }

    /**
     * ViewPager场景下，尝试请求数据
     */
    public void tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload() || !isDataLoaded)) {
            loadData();
            isDataLoaded = true;
            dispatchParentVisibleState();
        }
    }

    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     */
    private void dispatchParentHiddenState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseFragment && !((BaseFragment) child).isHidden) {
                ((BaseFragment) child).tryLoadData1();
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     *
     * @return
     */
    private boolean isParentHidden() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return false;
        } else if (fragment instanceof BaseFragment && !((BaseFragment) fragment).isHidden) {
            return false;
        }
        return true;
    }

    /**
     * show()、hide()场景下，尝试请求数据
     */
    public void tryLoadData1() {
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)) {
            loadData();
            isDataLoaded = true;
            dispatchParentHiddenState();
        }
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        isViewCreated = false;
        isVisibleToUser = false;
        isDataLoaded = false;
        isHidden = true;
        super.onDestroy();
    }
}
