package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

/**
 * Created by jackypeng on 2018/4/24.
 */

public class RegistActivity extends BaseActivity {
    @Override
    protected void onInitView(Bundle savedInstanceState) {

    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }
}
