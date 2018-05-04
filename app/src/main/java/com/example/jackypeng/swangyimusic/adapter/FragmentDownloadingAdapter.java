package com.example.jackypeng.swangyimusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.download_music.SimpleDownloadTaskListener;
import com.example.jackypeng.swangyimusic.network.DownloadMusicTask;
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
    public List<DownloadMusicTask> runningQueues = new ArrayList<>();
    public List<DownloadMusicTask> waitingQueues = new ArrayList<>();
    private List<MusicDownloadTrack> allUnfinishedTasks = new ArrayList<>();


    public FragmentDownloadingAdapter() {
        this.allUnfinishedTasks = DownloadDBManager.getInstance().getLoadingSongList();
        runningQueues = DownloadManager.getInstance().getRunningQueues();
        waitingQueues = DownloadManager.getInstance().getWaitingQueues();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_downloading_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        final MusicDownloadTrack track = allUnfinishedTasks.get(position);
        /**
         * 显示顺序以数据库中的条目为准
         *
         * 1.if runningTask==null
         *      界面时静态的
         *   else
         *      if(track.getMusicName==runningTask.getInfo.getMusicName){
         *          该音乐正在下载
         *          1.更新界面
         *          2.设置监听器
         *      }
         * 2.
         */
        itemHolder.tv_song_name.setText(track.getMusicName());
        itemHolder.itemView.setTag(track);
        //1.更新界面，设置监听器，拿到下载任务中的数据
        if (runningQueues.size() == 0) {
//            Log.i(TAG, "---runningQueues==0---");
            itemHolder.tv_song_name.setText(track.getMusicName());
            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
        } else {
            DownloadMusicTask runningTask = getRunnigTask(track.getMusicId());  //拿到正在下载的数据
            if (runningTask != null) {  //正在下载的歌曲
                Log.i(TAG, "正在下载:" + track.getMusicName());
                runningTask.setListener(new SimpleDownloadTaskListener() {
                    @Override
                    public void onStart(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PREPARING);
                        }
                    }

                    @Override
                    public void onDownloading(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            long download_size = downloadTrack.getDownload_size();
                            long total_size = downloadTrack.getTotal_size();
                            itemHolder.progressBar.setProgress((int) ((download_size * 1.0f / total_size) * 100));
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.DOWNLOADING);
                        }

                    }

                    @Override
                    public void onCompleted(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onCompleted---");
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.FINISHED);
                            refreshData();
                        }
                    }

                    @Override
                    public void onError(MusicDownloadTrack downloadInfo, int errorCode) {

                    }
                });
            } else if (hasWaittingMusicId(track.getMusicId())) {
//                Log.i(TAG, "---hasWaittingMusicId---:" + track.getMusicName());
                itemHolder.tv_song_name.setText(track.getMusicName());
                itemHolder.downloadButton.setStatus(DownloadStatusConstants.WAITING);
            } else {
                itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
            }
        }

        itemHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //表示下载任务正在被执行，取消任务
                if (itemHolder.downloadButton.getmStatus() == DownloadStatusConstants.DOWNLOADING) {
                    DownloadMusicTask runningTask = getRunnigTask(track.getMusicId());
                    if (runningTask != null) {
                        Log.i(TAG, "---暂停任务---:" + runningTask.getTrack().getMusicName());
                        runningTask.cancelTask();
                    }
                    return;
                }
                //点击之后，创建DownloadMusicTask，加入下载队列
                if (itemHolder.downloadButton.getmStatus() != DownloadStatusConstants.PAUSED) {
                    return;
                }
                DownloadMusicTask musicTask = new DownloadMusicTask(track, new SimpleDownloadTaskListener() {
                    @Override
                    public void onStart(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onStart---");
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PREPARING);
                        }
                    }

                    @Override
                    public void onPrepare(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onPrepare---");
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.WAITING);
                        }
                    }

                    @Override
                    public void onDownloading(MusicDownloadTrack downloadTrack) {
//                        Log.i(TAG, "---onDownloading---");
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            long download_size = downloadTrack.getDownload_size();
                            long total_size = downloadTrack.getTotal_size();
                            itemHolder.progressBar.setProgress((int) ((download_size * 1.0f / total_size) * 100));
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.DOWNLOADING);
                        }
                    }

                    @Override
                    public void onCompleted(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onCompleted---");
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.FINISHED);
                            refreshData();
                        }
                    }

                    @Override
                    public void onPause(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onPause---");
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
                        }
                    }

                    @Override
                    public void onError(MusicDownloadTrack downloadTrack, int errorCode) {

                    }
                });
                DownloadManager.getInstance().enqueueTask(musicTask);
            }
        });
    }

    //判断等待队列中是否有这首歌
    synchronized boolean hasWaittingMusicId(String id) {
        for (DownloadMusicTask task : waitingQueues) {
            if (task.getTrack().getMusicId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    //判断下载队列中是否有这首歌
    synchronized boolean hasRunningMusicId(String id) {
        for (DownloadMusicTask task : runningQueues) {
            if (task.getTrack().getMusicId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    //判断下载队列中是否有这首歌
    synchronized DownloadMusicTask getRunnigTask(String id) {
        for (DownloadMusicTask task : runningQueues) {
            if (task.getTrack().getMusicId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    void refreshData() {
        this.allUnfinishedTasks = DownloadDBManager.getInstance().getLoadingSongList();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return allUnfinishedTasks.size();
    }

    public void startAll() {
        Log.i(TAG, "---startAll---");
        for (MusicDownloadTrack track : allUnfinishedTasks) {
            String musicId = track.getMusicId();
            if (!hasRunningMusicId(musicId) && !hasWaittingMusicId(musicId)) {
                DownloadMusicTask musicTask = new DownloadMusicTask(track);
                DownloadManager.getInstance().enqueueTask(musicTask);
            }
        }
        notifyDataSetChanged();
    }

    public void pauseAll() {

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
