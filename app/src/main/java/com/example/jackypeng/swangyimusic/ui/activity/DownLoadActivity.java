package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.fragment.BaseFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.DownloadFinishedFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.DownloadingFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/19.
 * <p>
 * 下载管理界面
 */

public class DownLoadActivity extends BaseActivity {

    @BindView(R.id.activity_download_viewpager)
    ViewPager viewPager;
    @BindView(R.id.activity_download_sliding_tab)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.activity_download_toolbar)
    Toolbar toolbar;

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        initToolBar();
        initViewPager();
    }

    private void initToolBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.mipmap.actionbar_back);
            ab.setTitle("下载管理");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
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
        return R.layout.activity_download;
    }

    private void initViewPager() {
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("下载完成");
        titles.add("下载中");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment baseFragment = null;
                switch (position) {
                    case 0:
                        baseFragment = new DownloadFinishedFragment();
                        break;
                    case 1:
                        baseFragment = new DownloadingFragment();
                        break;
                }
                return baseFragment;
            }

            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        slidingTabLayout.setViewPager(viewPager);
    }
}
