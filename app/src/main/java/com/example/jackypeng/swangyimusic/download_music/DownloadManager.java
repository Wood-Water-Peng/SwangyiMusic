package com.example.jackypeng.swangyimusic.download_music;

import android.util.Log;

import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.network.DownloadMusicTask;
import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayingLrcBean;
import com.example.jackypeng.swangyimusic.rx.contract.MusicUrlContract;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.rx.model.PlayingMusicInfoModel;
import com.example.jackypeng.swangyimusic.rx.presenter.PlayingMusicInfoPresenter;
import com.example.jackypeng.swangyimusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/20.
 */

public class DownloadManager {
    public static final String TAG = "DownloadManager";
    private static DownloadManager instance;
    private Dispatcher dispatcher;


    public static DownloadManager getInstance() {
        synchronized (DownloadManager.class) {
            if (instance == null) {
                synchronized (DownloadManager.class) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    private DownloadManager() {
        dispatcher = new Dispatcher();
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     *
     */
    public void startDownload(DownloadInfo info, DownloadTaskListener listener) {
        /**
         *
         *1.根据songId去获取歌曲的下载链接
         */
        if (info == null) {
            throw new IllegalStateException("DownloadInfo cannot be null");
        }
        MusicDownloadTrack track = new MusicDownloadTrack();
        track.setMusicId(info.getSongId());
        track.setMusicName(info.getSongName());
        track.setStatus(DownloadStatusConstants.WAITING);
        getDispatcher().enqueue(new DownloadMusicTask(track, listener));
    }

    synchronized public void enqueueTask(DownloadMusicTask task) {
        MusicDownloadTrack track = task.getTrack();
        track.setStatus(DownloadStatusConstants.WAITING);
        DownloadDBManager.getInstance().insertInfo(track);
        Log.i(TAG, "id:" + track.getMusicId() + "---" + track.getMusicName() + "---插入数据库");
        getDispatcher().enqueue(task);
    }

    synchronized public void enqueueTasks(List<DownloadInfo> selectedItems) {
        //创建出相应的DownloadMusicTask
        List<DownloadMusicTask> downloadTasks = new ArrayList<>();
        List<MusicDownloadTrack> downloadTracks = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            DownloadInfo downloadInfo = selectedItems.get(i);
            MusicDownloadTrack track = new MusicDownloadTrack();
            track.setMusicId(downloadInfo.getSongId());
            track.setMusicName(downloadInfo.getSongName());
            track.setStatus(DownloadStatusConstants.WAITING);
            downloadTasks.add(new DownloadMusicTask(track));
            //在下载记录数据库创建一个下载记录
            downloadTracks.add(track);
//            Log.i(TAG, "num:" + i + "---id:" + downloadInfo.getSongId() + "---" + downloadInfo.getSongName() + "---插入数据库");
//            DownloadDBManager.getInstance().insertInfo(downloadTrack);
        }
        DownloadDBManager.getInstance().insertListTrack(downloadTracks);

        for (int i = 0; i < downloadTasks.size(); i++) {
            getDispatcher().enqueue(downloadTasks.get(i));
        }

    }

    public List<DownloadMusicTask> getRunningQueues() {
        return getDispatcher().getRunningQueues();
    }

    public List<DownloadMusicTask> getWaitingQueues() {
        return getDispatcher().getWaitingQueues();
    }

    synchronized public boolean isTaskInRunningQueue(String musicId) {
        List<DownloadMusicTask> runningQueues = getDispatcher().getRunningQueues();
        for (DownloadMusicTask task : runningQueues) {
            if (task.getTrack().getMusicId().equals(musicId))
                return true;
        }
        return false;
    }

    synchronized public boolean isTaskInWaitingQueue(String musicId) {
        List<DownloadMusicTask> waitingQueues = getDispatcher().getWaitingQueues();
        for (DownloadMusicTask task : waitingQueues) {
            if (task.getTrack().getMusicId().equals(musicId))
                return true;
        }
        return false;
    }

    synchronized public void removeWaitingTask(String musicId) {
        List<DownloadMusicTask> waitingQueues = getDispatcher().getWaitingQueues();
        DownloadMusicTask toRemovedTask = null;
        for (DownloadMusicTask task : waitingQueues) {
            if (task.getTrack().getMusicId().equals(musicId)) {
                toRemovedTask = task;
            }
        }
        if (toRemovedTask != null) {
            waitingQueues.remove(toRemovedTask);
        }
    }
}
