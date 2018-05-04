package com.example.jackypeng.swangyimusic.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentDownloadingAdapter;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.download_music.DownloadService;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.network.DownloadMusicTask;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.widget.SmartLoadingLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jackypeng on 2018/3/19.
 * <p>
 * 正在下载的歌曲，必须从下载服务管理器(@DownloadManager)中获取
 */

public class DownloadingFragment extends BaseFragment {
    @BindView(R.id.fragment_downloading_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_downloading_sll)
    SmartLoadingLayout smartLoadingLayout;
    @BindView(R.id.fragment_download_start_all)
    TextView tv_start_all;
    @BindView(R.id.fragment_download_pause_all)
    TextView tv_pause_all;
    private FragmentDownloadingAdapter downloadingAdapter;


    @OnClick(R.id.fragment_download_start_all)
    public void startAll() {
        //将暂停中的任务全部加入下载队列
        downloadingAdapter.startAll();
    }

    @OnClick(R.id.fragment_download_pause_all)
    public void pauseAll() {
        //将下载和等待队列中的任务全部移除
        downloadingAdapter.pauseAll();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_downloading;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        initViewPager();
        fetchDataFromDownloadManager();
    }

    /**
     * 从下载管理器获取数据
     * 1.从DownloadManager中获取
     * <p>
     * 2.从数据库中获取
     */
    private void fetchDataFromDownloadManager() {
        List<MusicDownloadTrack> loadingSongList = DownloadDBManager.getInstance().getLoadingSongList();
        if (loadingSongList == null || loadingSongList.size() == 0) {
            smartLoadingLayout.onEmpty();
        } else {
            smartLoadingLayout.onSuccess();
        }
    }

    private void initViewPager() {
        downloadingAdapter = new FragmentDownloadingAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(downloadingAdapter);
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
