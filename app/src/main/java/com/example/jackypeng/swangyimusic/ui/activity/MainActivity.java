package com.example.jackypeng.swangyimusic.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;
import com.example.jackypeng.swangyimusic.ui.fragment.BaseFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.BottomControllFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.FriendFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.HomeFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.MusicFragment;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.example.jackypeng.swangyimusic.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String PLAYING_QUEUE_CONTROLLER = "playing_queue_controller";
    private Unbinder unbinder;

    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.bar_music)
    ImageView bar_music;
    @BindView(R.id.bar_home)
    ImageView bar_home;
    @BindView(R.id.bar_friends)
    ImageView bar_friends;

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


    private List<ImageView> tabs = new ArrayList<>();


    private void init() {
        initTabs();
        initBottomFragment();
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

    private void initBottomFragment() {
        BottomControllFragment bottomControllFragment = new BottomControllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_bottom_controller, bottomControllFragment).commit();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        MusicPlayer.getInstance().init();    //启动播放服务
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart_width:" + viewPager.getMeasuredWidth());
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume_width:" + viewPager.getMeasuredWidth());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
