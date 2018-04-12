package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends AppCompatActivity {

    private Unbinder unbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            View view = LayoutInflater.from(this).inflate(layoutId, null);
            setContentView(view);
        }
        unbinder = ButterKnife.bind(this);
        bindMVP();
        onInitView(savedInstanceState);
    }

    protected abstract void onInitView(Bundle savedInstanceState);

    private void bindMVP() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.attachModel(getModel());
            mPresenter.attachView(getViewImpl());
        }
    }

    protected abstract BaseView getViewImpl();

    protected abstract BaseModel getModel();

    protected abstract P getPresenter();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
