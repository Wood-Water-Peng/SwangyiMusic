package com.example.jackypeng.swangyimusic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.UserInfoConstants;
import com.example.jackypeng.swangyimusic.rx.bean.LoginResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserLoginInfoBean;
import com.example.jackypeng.swangyimusic.rx.contract.LoginContract;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.LoginModel;
import com.example.jackypeng.swangyimusic.rx.presenter.LoginPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.widget.LoginRegistDialog;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;
import com.example.jackypeng.swangyimusic.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jackypeng on 2018/4/24.
 * <p>
 * 登录场景可能会有取消功能，当前暂不考虑
 * <p>
 * 用Retrofit不适合取消场景
 */

public class LoginActivity extends BaseActivity<LoginContract.Model, LoginContract.Presenter> implements LoginContract.View {
    private LoginRegistDialog loginRegistDialog;

    @BindView(R.id.activity_login_regist_btn_login)
    Button btn_login;
    @BindView(R.id.et_activity_login_phone)
    EditText et_phone;
    @BindView(R.id.et_activity_login_psw)
    EditText et_psw;

    private boolean isCancelFlag;  //当前请求是否被取消

    @OnClick(R.id.activity_login_regist_btn_login)
    public void tryLogin() {
        String str_phone = et_phone.getText().toString().trim();
        String str_psw = et_psw.getText().toString().trim();
        if (TextUtils.isEmpty(str_phone) || TextUtils.isEmpty(str_psw)) {
            ToastUtil.getInstance().toast("账号或密码不能为空");
            return;
        }
        showLoginingDialog();
        mPresenter.login(str_phone, str_psw);
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected BaseModel getModel() {
        return new LoginModel();
    }

    @Override
    protected LoginContract.Presenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getStatusBarStatus() {
        return TRANS_STATUS_BAR;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getLoginResultView(LoginResultBean resultBean) {
        switch (resultBean.getCode()) {
            case 200:      //成功
                onLoginSuccess(resultBean);
                break;
            case 501:     //账号错误
                onLoginFailed();
                break;
            case 502:     //密码错误
                onLoginFailed();
                break;
        }
    }

    private void onLoginFailed() {
        dismissLoginingDialog();
        ToastUtil.getInstance().toast("账号或密码错误");
    }

    private void onLoginSuccess(LoginResultBean resultBean) {
        /**
         * 1.跳转到MainActivity(由于Main的启动模式为singleTask，夹在中间的RegistLoginActivity会被自动弹出)
         */
        saveUserLoginInfo(resultBean);
        dismissLoginingDialog();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void saveUserLoginInfo(LoginResultBean resultBean) {
        UserLoginInfoBean.getInstance().setUserId(resultBean.getAccount().getId());
        UserLoginInfoBean.getInstance().setUserIsLogined(true);
        SharePreferenceUtil.writeString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_ID, resultBean.getAccount().getId());
        SharePreferenceUtil.writeBoolean(UserInfoConstants.USER_INFO, UserInfoConstants.IS_LOGINED, true);
        SharePreferenceUtil.writeString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_NICK_NAME, resultBean.getProfile().getNickname());
        SharePreferenceUtil.writeString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_AVATAR_URL, resultBean.getProfile().getAvatarUrl());
        SharePreferenceUtil.writeString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_BACKGROUND_URL, resultBean.getProfile().getBackgroundUrl());
    }

    private void showLoginingDialog() {
        loginRegistDialog = new LoginRegistDialog(this);
        loginRegistDialog.setTextTip("正在登录...");
        loginRegistDialog.show();
    }

    private void dismissLoginingDialog() {
        if (loginRegistDialog != null) {
            loginRegistDialog.dismiss();
        }
    }

}
