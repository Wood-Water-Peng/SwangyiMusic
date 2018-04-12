package com.example.jackypeng.swangyimusic.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.download_music.DownloadInfo;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.download_music.DownloadService;
import com.example.jackypeng.swangyimusic.download_music.DownloadTaskListener;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.rx.view.widget.DownloadButton;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class FragmentDownloadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "FragmentDownloadingAdapter";
    private DownloadService.DownloadBinder downloadBinder;
    private List<DownloadInfoEntity> loadingQueue;
    private List<DownloadInfoEntity> waitingQueue = new ArrayList<>();
    private List<DownloadInfoEntity> pausedQueue;
    private List<DownloadInfoEntity> totalQueue = new ArrayList<>();

    public FragmentDownloadingAdapter(Context context) {
        context.bindService(new Intent(context, DownloadService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                downloadBinder = (DownloadService.DownloadBinder) service;
                initData();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void initData() {
        //只能有一个数据源，从数据库中获取,然后和服务中正在下载的任务进行比较，动态的添加监听器，更新进度
//        loadingQueue = downloadBinder.getLoadingQueue();
//        waitingQueue = downloadBinder.getWaitingQueue();
//        pausedQueue = downloadBinder.getPausedQueue();
//        totalQueue.addAll(loadingQueue);
//        totalQueue.addAll(waitingQueue);
//        totalQueue.addAll(pausedQueue);
//        notifyDataSetChanged();    //刷新界面
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_downloading_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        final DownloadInfoEntity entity = waitingQueue.get(position);
        itemHolder.itemView.setTag(entity);
        //1.更新界面
        itemHolder.initUI(entity);
        //2.设置监听器
        entity.setListener(new DownloadTaskListener() {
            @Override
            public void onPrepare(DownloadInfoEntity downloadTask) {

            }

            @Override
            public void onStart(DownloadInfoEntity downloadTask) {
                DownloadDBManager.getInstance().insertInfo(downloadTask);
                if (itemHolder.itemView.getTag() == downloadTask) {
                    itemHolder.tv_song_name.setText(downloadTask.getSongName() + "--准备开始下载");
                    Log.i(TAG, "onStart:" + downloadTask.getSongName());
                }
            }

            @Override
            public void onDownloading(DownloadInfoEntity downloadTask) {
                //跟新进度
                if (itemHolder.itemView.getTag() == downloadTask) {
                    DownloadDBManager.getInstance().updateInfo(downloadTask);
                    itemHolder.progressBar.setProgress((int) (downloadTask.getLoadedSize() * 1.0 / downloadTask.getTotalSize()));
                }
                Log.i(TAG, "onDownloading:" + downloadTask.getLoadedSize());

            }

            @Override
            public void onPause(DownloadInfoEntity downloadTask) {
                DownloadDBManager.getInstance().updateInfo(downloadTask);
            }

            @Override
            public void onCancel(DownloadInfoEntity downloadTask) {

            }

            @Override
            public void onCompleted(DownloadInfoEntity downloadTask) {
                DownloadDBManager.getInstance().updateInfo(downloadTask);
                if (itemHolder.itemView.getTag() == downloadTask) {
                    itemHolder.tv_song_name.setText(downloadTask.getSongName() + "--下载完成");
                    itemHolder.downloadButton.setStatus(DownloadStatusConstants.FINISHED);
                    itemHolder.progressBar.setProgress((int) (1.0 * entity.getLoadedSize() / entity.getTotalSize()));
                    Log.i(TAG, "onCompleted:" + downloadTask.getSongName());
                }
            }

            @Override
            public void onError(DownloadInfoEntity downloadTask, int errorCode) {
                DownloadDBManager.getInstance().updateInfo(downloadTask);
            }
        });

        //设置点击事件
        itemHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (entity.getStatus()) {
                    case DownloadStatusConstants.PAUSED:
                        downloadBinder.try2DownloadSong(entity);
                        break;
                    case DownloadStatusConstants.DOWNLOADING:
                        break;
                    case DownloadStatusConstants.WAITING:
                        break;
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return waitingQueue.size();
    }

    public void setData(List<DownloadInfoEntity> downloadInfoEntities, DownloadService.DownloadBinder binder) {
        if (downloadInfoEntities != null) {
            waitingQueue = downloadInfoEntities;
            downloadBinder = binder;
            notifyDataSetChanged();
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView icon;
        TextView tv_song_name;
        ProgressBar progressBar;
        DownloadButton downloadButton;

        ItemHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.fragment_downloading_item_icon);
            tv_song_name = (TextView) itemView.findViewById(R.id.fragment_downloading_item_song_name);
            progressBar = (ProgressBar) itemView.findViewById(R.id.fragment_downloading_item_progressbar);
            downloadButton = (DownloadButton) itemView.findViewById(R.id.fragment_downloading_item_download_button);
        }

        //根据状态和数据更新界面
        private void initUI(DownloadInfoEntity entity) {
            switch (entity.getStatus()) {
                case DownloadStatusConstants.PAUSED:
                    downloadButton.setStatus(DownloadStatusConstants.PAUSED);
                    tv_song_name.setText(entity.getSongName() + "暂停中");
                    int progress = (int) (1.0f * entity.getLoadedSize() / entity.getTotalSize());
                    progressBar.setProgress(progress);
                    break;
                case DownloadStatusConstants.DOWNLOADING:
                    downloadButton.setStatus(DownloadStatusConstants.DOWNLOADING);
                    tv_song_name.setText(entity.getSongName() + "下载中");
                    progressBar.setProgress((int) (1.0 * entity.getLoadedSize() / entity.getTotalSize()));
                    break;
                case DownloadStatusConstants.WAITING:
                    downloadButton.setStatus(DownloadStatusConstants.WAITING);
                    tv_song_name.setText(entity.getSongName() + "等待中");
                    progressBar.setProgress((int) (1.0 * entity.getLoadedSize() / entity.getTotalSize()));
                    break;
            }
        }
    }
}
