package com.example.jackypeng.swangyimusic.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentDownloadingAdapter;
import com.example.jackypeng.swangyimusic.download_music.DownloadService;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/19.
 * <p>
 * 正在下载的歌曲，必须从下载服务中获取数据
 */

public class DownloadingFragment extends BaseFragment {
    @BindView(R.id.fragment_downloading_recyclerview)
    RecyclerView recyclerView;
    private FragmentDownloadingAdapter downloadingAdapter;
    private DownloadService.DownloadBinder binder;
    private List<DownloadInfoEntity> downloadInfoEntities;

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
        bindDownloadService();
    }

    private void bindDownloadService() {
        Intent intent = new Intent(getContext(), DownloadService.class);
        //服务的生命周期和App相同
        MainApplication.getAppContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (DownloadService.DownloadBinder) service;
                downloadInfoEntities = binder.getWaitingQueue();
                downloadingAdapter.setData(downloadInfoEntities,binder);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void initViewPager() {
        downloadingAdapter = new FragmentDownloadingAdapter(getContext());
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
