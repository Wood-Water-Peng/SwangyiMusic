package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.fragment.BaseFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.BottomControllFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.FriendFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.HomeFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.LeftMenuFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.MusicFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final String PLAYING_QUEUE_CONTROLLER = "playing_queue_controller";

    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.bar_music)
    ImageView bar_music;
    @BindView(R.id.bar_home)
    ImageView bar_home;
    @BindView(R.id.bar_menu)
    ImageView bar_menu;
    @BindView(R.id.bar_friends)
    ImageView bar_friends;
    @BindView(R.id.activity_main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_bottom_controller)
    FrameLayout bottom_frameLayout;

    @OnClick(R.id.bar_friends)
    public void switch2friends() {
        viewPager.setCurrentItem(2);
    }

    @OnClick(R.id.bar_home)
    public void switch2Home() {
        viewPager.setCurrentItem(1);
    }

    @OnClick(R.id.bar_music)
    public void switch2Music() {
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.bar_menu)
    public void openLeftMenu() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }


    private List<ImageView> tabs = new ArrayList<>();

    private void init() {
        initBottomController();
        initLeftFragment();
        initTabs();
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment baseFragment = null;
                switch (position) {
                    case 0:
                        baseFragment = new MusicFragment();
                        break;
                    case 1:
                        baseFragment = new HomeFragment();
                        break;
                    case 2:
                        baseFragment = new FriendFragment();
                        break;
                }
                return baseFragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected:" + position);
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1);
    }

    private void initLeftFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_left_menu, new LeftMenuFragment()).commitAllowingStateLoss();
    }

    private void initBottomController() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_bottom_controller, new BottomControllFragment()).commitAllowingStateLoss();
    }


    private void initTabs() {
        tabs.add(bar_music);    //1
        tabs.add(bar_home);    //2
        tabs.add(bar_friends);  //3
    }

    private void switchTabs(int index) {
        for (int i = 0; i < tabs.size(); i++) {
            if (index == i) {
                tabs.get(i).setSelected(true);
            } else {
                tabs.get(i).setSelected(false);
            }
        }
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        init();
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.light_gray));
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        drawerLayout.closeDrawers();
    }

    @Override
    protected int getNavBarStatus() {
        return HIDE_NAV_BAR;
    }

    @Override
    protected int getStatusBarStatus() {
        return TRANS_STATUS_BAR;
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
        return R.layout.activity_main;
    }

}
