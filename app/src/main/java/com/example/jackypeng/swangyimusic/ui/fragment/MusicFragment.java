package com.example.jackypeng.swangyimusic.ui.fragment;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/16.
 */

public class MusicFragment extends BaseFragment {

    @BindView(R.id.fragment_music_sliding_tab)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.fragment_music_viewpager)
    ViewPager viewPager;
    private static final String TAG = "MusicFragment";
    private FragmentStatePagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "---onCreateView---");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "---setUserVisibleHint---:" + isVisibleToUser);
        /**
         * 只有该Fragment可见的时候，才让其ViewPager中的Fragment去加载数据
         */
        if (isVisibleToUser && list.size() > 0) {  //该Fragment可见，调用FreshFragment去加载数据
            Fragment fragment = list.get(0);
            if (fragment instanceof DiscoverFragment) {
                ((DiscoverFragment) fragment).fetchData();
            }
        }

    }

    private List<BaseFragment> list = new ArrayList<>();

    private void init() {
        final ArrayList<String> titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.fragment_discover_title));
        titles.add("歌单");
        titles.add("排行榜");
        viewPager.setOffscreenPageLimit(3);
        adapter = new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment baseFragment = null;
                switch (position) {
                    case 0:
                        baseFragment = new DiscoverFragment();
                        break;
                    case 1:
                        baseFragment = new RankingListFragment();
                        break;
                    case 2:
                        baseFragment = new SongMenuFragment();
                        break;
                }
                list.add(baseFragment);
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
        };
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
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
