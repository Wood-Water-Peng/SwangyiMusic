package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

/**
 * Created by jackypeng on 2018/4/18.
 */

public class RadioDetailFragment extends BaseFragment{
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_radio_detail;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
