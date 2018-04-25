package com.example.jackypeng.swangyimusic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jackypeng on 2018/4/24.
 */

public class Login_Regist_Activity extends BaseActivity {

    @BindView(R.id.activity_login_regist_btn_login)
    Button btn_login;

    @OnClick(R.id.activity_login_regist_btn_login)
    public void onBtnLoginClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }

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
        return R.layout.activity_login_regist;
    }

    @Override
    protected int getStatusBarStatus() {
        return TRANS_STATUS_BAR;
    }
}
