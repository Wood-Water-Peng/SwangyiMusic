package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.fragment.BaseFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.BottomControllFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicAlbumFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicArtistFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicFolderFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicSongFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class LocalMusicActivity extends BaseActivity {

    @BindView(R.id.activity_local_music_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_local_music_sliding_tab)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.activity_local_music_viewpager)
    ViewPager viewPager;

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("单曲");
        titles.add("歌手");
        titles.add("专辑");
        titles.add("文件夹");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment baseFragment = null;
                switch (position) {
                    case 0:
                        baseFragment = new LocalMusicSongFragment();  //单曲
                        break;
                    case 1:
                        baseFragment = new LocalMusicArtistFragment(); //歌手
                        break;
                    case 2:
                        baseFragment = new LocalMusicAlbumFragment(); //专辑
                        break;
                    case 3:
                        baseFragment = new LocalMusicFolderFragment(); //文件夹
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
        return R.layout.activity_local_music;
    }

    @Override
    protected boolean hasBottomController() {
        return true;
    }
}
