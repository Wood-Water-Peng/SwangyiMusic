package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.download_music.SimpleDownloadTaskListener;
import com.example.jackypeng.swangyimusic.network.DownloadMusicTask;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.rx.view.widget.DownloadButton;
import com.example.jackypeng.swangyimusic.ui.widget.DownloadTipDialog;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
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
    private Context mContext;

    public FragmentDownloadingAdapter(Context context) {
        this.mContext = context;
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
        itemHolder.itemView.setTag(track);
        itemHolder.initUI(track);
        //1.更新界面，设置监听器，拿到下载任务中的数据
        if (runningQueues.size() == 0) {
//            Log.i(TAG, "---runningQueues==0---");
//            itemHolder.tv_song_name.setText(track.getMusicName());
//            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
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
                            Log.i(TAG, "---onCompleted---:" + downloadTrack.getMusicName());
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.FINISHED);
                            refreshData(downloadTrack.getMusicId());
                        }
                    }

                    @Override
                    public void onPause(MusicDownloadTrack downloadTrack) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onPause---:" + downloadTrack.getMusicName());
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
//                            refreshData(downloadTrack.getMusicId());
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(MusicDownloadTrack downloadTrack, int errorCode) {
                        if (itemHolder.itemView.getTag() == downloadTrack) {
                            Log.i(TAG, "---onError---:" + downloadTrack.getMusicName());
                            itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
                            refreshData(downloadTrack.getMusicId());
                        }
                    }
                });
            } else if (hasWaittingMusicId(track.getMusicId())) {
//                Log.i(TAG, "---WaittingMusic---:" + track.getMusicName());
                itemHolder.downloadButton.setStatus(DownloadStatusConstants.WAITING);


            } else {
//                Log.i(TAG, "---PausedMusic---:" + track.getMusicName());
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

                //表示任务正在等待中
                if (itemHolder.downloadButton.getmStatus() == DownloadStatusConstants.WAITING) {
                    DownloadManager.getInstance().removeWaitingTask(track.getMusicId());
                    Log.i(TAG, "---移除任务---:" + track.getMusicName());
                    itemHolder.downloadButton.setStatus(DownloadStatusConstants.PAUSED);
                    return;
                }

                //任务暂停中，创建DownloadMusicTask，加入下载队列
                if (itemHolder.downloadButton.getmStatus() == DownloadStatusConstants.PAUSED) {
                    DownloadMusicTask musicTask = new DownloadMusicTask(track);
                    DownloadManager.getInstance().enqueueTask(musicTask);
                    itemHolder.downloadButton.setStatus(DownloadStatusConstants.WAITING);
                    notifyDataSetChanged();
                }
            }
        });

        itemHolder.iv_rubbish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTipDialog tipDialog = new DownloadTipDialog(mContext);
                final String cur_music_id = track.getMusicId();
                final String cur_music_name = track.getMusicName();
                tipDialog.setMessage("确定不再下载该任务吗？");
                tipDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cur_music_id.equals(track.getMusicId())) {
                            Log.i(TAG, "任务还存在:" + cur_music_name);
                            DownloadManager.getInstance().removeWaitingTask(cur_music_id);
                            DownloadDBManager.getInstance().deleteInfo(cur_music_id);
                            refreshData(cur_music_id);
                        } else {
                            ToastUtil.getInstance().toast("任务已取消");
                            Log.i(TAG, "任务已取消---cur_track:" + cur_music_id);
                        }
                    }
                });

                tipDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                tipDialog.show();
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

    void refreshData(String id) {
//        this.allUnfinishedTasks = DownloadDBManager.getInstance().getLoadingSongList();
        removeFinishedTrack(id);
        notifyDataSetChanged();
    }

    void removeFinishedTrack(String musicID) {
        int toRemovedIndex = -1;
        for (int i = 0; i < allUnfinishedTasks.size(); i++) {
            if (allUnfinishedTasks.get(i).getMusicId().equals(musicID)) {
                toRemovedIndex = i;
                break;
            }
        }
        Log.i(TAG, "remove finished track:" + allUnfinishedTasks.get(toRemovedIndex).getMusicName());
        allUnfinishedTasks.remove(toRemovedIndex);
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

    //暂停下载的任务和移除等待的任务
    public void pauseAll() {
        List<DownloadMusicTask> runningQueues = DownloadManager.getInstance().getRunningQueues();
        for (DownloadMusicTask track : runningQueues) {
            track.cancelTask();
        }
        DownloadManager.getInstance().getRunningQueues().clear();
        DownloadManager.getInstance().getWaitingQueues().clear();
        notifyDataSetChanged();
    }

    public void tryClearAll() {

        DownloadTipDialog dialog = new DownloadTipDialog(mContext);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearAll();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setMessage("确定清空所有" + getItemCount() + "条任务吗");
        dialog.show();
    }

    private void clearAll() {
        /**
         * 1.取消正在下载的任务
         * 2.清除正在等待的任务
         * 3.清除数据库记录
         */
        if (runningQueues.size() > 0) {
            for (DownloadMusicTask task : runningQueues) {
                task.cancelTask();
            }
        }
        waitingQueues.clear();
        DownloadDBManager.getInstance().deleteDownloadindList();
        allUnfinishedTasks.clear();
        notifyDataSetChanged();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView icon;
        TextView tv_song_name;
        ProgressBar progressBar;
        DownloadButton downloadButton;
        ImageView iv_rubbish;

        ItemHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.fragment_downloading_item_icon);
            tv_song_name = (TextView) itemView.findViewById(R.id.fragment_downloading_item_song_name);
            progressBar = (ProgressBar) itemView.findViewById(R.id.fragment_downloading_item_progressbar);
            downloadButton = (DownloadButton) itemView.findViewById(R.id.fragment_downloading_item_download_button);
            iv_rubbish = (ImageView) itemView.findViewById(R.id.fragment_downloading_item_clear);
        }

        //根据状态和数据更新界面
        private void initUI(MusicDownloadTrack track) {
            tv_song_name.setText(track.getMusicName());
            downloadButton.setStatus(DownloadStatusConstants.PAUSED);
            progressBar.setProgress((int) ((track.getDownload_size() * 1.0f / track.getTotal_size()) * 100));
        }
    }
}
