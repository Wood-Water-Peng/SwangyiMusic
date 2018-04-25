package com.example.jackypeng.swangyimusic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.UserInfoConstants;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.activity.Login_Regist_Activity;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jackypeng on 2018/4/22.
 * <p>
 * 接受登录成功的广播，则
 */

public class LeftMenuFragment extends BaseFragment {

    @BindView(R.id.fragment_left_menu_btn_login)
    Button btn_login;
    @BindView(R.id.fragment_left_menu_login_wrapper)
    View login_wrapper;
    @BindView(R.id.fragment_left_menu_unlogin_wrapper)
    View unlogin_wrapper;
    @BindView(R.id.fragment_left_menu_user_bg)
    SimpleDraweeView iv_user_bg;
    @BindView(R.id.fragment_left_menu_user_avatar)
    SimpleDraweeView iv_user_avatar;
    @BindView(R.id.fragment_left_menu_user_nickname)
    TextView tv_user_nickname;

    @OnClick(R.id.fragment_left_menu_btn_login)
    public void skip2LoginActivity() {     //跳转到登录界面
        getActivity().startActivity(new Intent(getContext(), Login_Regist_Activity.class));
        getContext().sendBroadcast(new Intent(BroadcastConstants.CLOSE_DRAWER));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_left_menu;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        /**
         * 1.只要登录成功了，就直接从sp中获取数据
         */
        boolean isUserLogin = SharePreferenceUtil.readBoolean(UserInfoConstants.USER_INFO, UserInfoConstants.IS_LOGINED);
        if (isUserLogin) {
            login_wrapper.setVisibility(View.VISIBLE);
            initLoginView();
        } else {
            unlogin_wrapper.setVisibility(View.VISIBLE);
        }

    }

    private void initLoginView() {
        String user_bg = SharePreferenceUtil.readString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_BACKGROUND_URL);
        String user_avatar = SharePreferenceUtil.readString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_AVATAR_URL);
        String user_nickname = SharePreferenceUtil.readString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_NICK_NAME);
        iv_user_bg.setImageURI(user_bg);
        iv_user_avatar.setImageURI(user_avatar);
        tv_user_nickname.setText(user_nickname);
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
