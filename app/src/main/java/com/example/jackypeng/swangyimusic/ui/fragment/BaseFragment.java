package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jackypeng on 2018/3/6.
 */

public abstract class BaseFragment<M extends BaseModel, P extends BasePresenter> extends Fragment {
    private View rootView;
    private Unbinder unbinder;
    protected P mPresenter;
    private boolean isViewPrepared;
    private boolean hasFetchData;  //标识是否已经触发懒加载

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            tryLazyFetchData();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        tryLazyFetchData();
    }

    private void tryLazyFetchData() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }

    protected void lazyFetchData(){

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        unbinder = ButterKnife.bind(this, rootView);
        bindMVP();
        onInitView(savedInstanceState);
        return rootView;
    }

    private void bindMVP() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachModel(getModel());
            mPresenter.attachView(getViewImpl());
        }
    }

    protected abstract int getLayoutId();

    protected abstract BaseView getViewImpl();

    protected abstract void onInitView(Bundle savedInstanceState);

    protected abstract M getModel();

    protected abstract P getPresenter();
}
