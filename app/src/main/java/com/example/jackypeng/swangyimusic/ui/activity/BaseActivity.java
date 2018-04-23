package com.example.jackypeng.swangyimusic.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.fragment.BottomControllFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends AppCompatActivity {

    /**
     * 定义状态栏和导航栏状态
     */

    protected static final int NORMAL_STATUS_BAR = 1;
    protected static final int NORMAL_NAV_BAR = 2;
    protected static final int TRANS_STATUS_BAR = 3;
    protected static final int TRANS_NAV_BAR = 4;
    protected static final int HIDE_STATUS_BAR = 5;
    protected static final int HIDE_NAV_BAR = 6;
    private Unbinder unbinder;
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId == 0) throw new IllegalArgumentException("layout id cannot be 0");
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        if (hasBottomController()) {
            View rootView = LayoutInflater.from(this).inflate(R.layout.activity_parent_container, null);
            ((FrameLayout) rootView.findViewById(R.id.content)).addView(contentView);
            BottomControllFragment bottomControllFragment = new BottomControllFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.bottom_controller, bottomControllFragment).commitNowAllowingStateLoss();
            setContentView(rootView);
        } else {
            setContentView(contentView);
        }
        unbinder = ButterKnife.bind(this);
        bindMVP();
        onInitView(savedInstanceState);
        initStatusAndNav();
    }

    protected void initStatusAndNav() {
        if (Build.VERSION.SDK_INT >= 19) {   //4.4以上
            int statusBarStatus = getStatusBarStatus();
            int navBarStatus = getNavBarStatus();
            if (statusBarStatus == TRANS_STATUS_BAR) {       //状态栏透明
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：
                //Activity布局全屏显示，但状态栏不会被隐藏覆盖，
                //状态栏依然可见，Activity顶端布局部分会被状态遮住。
                //SYSTEM_UI_FLAG_LAYOUT_STABLE ：
                //防止状态栏隐藏，保证你使用fitSystemWindows时候,系统UI边界
                //始终不会变，依然存在可见.即使你隐藏了所有，他依然存在，增加了UI稳定性，
                getWindow().getDecorView().setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);//状态栏颜色设置为透明。
            }

            if (navBarStatus == HIDE_NAV_BAR) {    //隐藏导航栏
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                decorView.setSystemUiVisibility(option);
            }
        }
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

    protected boolean hasBottomController() {
        return false;
    }

    protected int getStatusBarStatus() {
        return NORMAL_STATUS_BAR;
    }

    protected int getNavBarStatus() {
        return NORMAL_NAV_BAR;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
