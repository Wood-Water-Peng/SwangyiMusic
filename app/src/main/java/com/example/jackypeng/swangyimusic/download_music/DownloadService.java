package com.example.jackypeng.swangyimusic.download_music;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.util.DownloadUtil;
import com.example.jackypeng.swangyimusic.util.L;
import com.example.jackypeng.swangyimusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class DownloadService extends Service {

    private List<DownloadInfoEntity> loadingQueue = new ArrayList<>();    //任务---正在下载
    private List<DownloadInfoEntity> waitingQueue = new ArrayList<>();    //任务---等待中
    private List<DownloadInfoEntity> pausedQueue = new ArrayList<>();    //任务---暂停中，从数据库中获取

    public static final String ADD_TASK = "add_task";
    public static final String RESUME_TASK = "resume_task";
    public static final String REMOVE_TASK = "remove_task";
    public static final String PAUSE_TASK = "pause_task";
    private ExecutorService service;
    private DownloadBinder mBinder;

    public class DownloadBinder extends Binder {
        public List<DownloadInfoEntity> getLoadingQueue() {
            return DownloadService.this.getLoadingQueue();
        }

        public List<DownloadInfoEntity> getWaitingQueue() {
            return DownloadService.this.getWaitingQueue();
        }

        public List<DownloadInfoEntity> getPausedQueue() {
            return DownloadService.this.getWaitingQueue();
        }

        public void try2DownloadSong(DownloadInfoEntity entity) {
            DownloadService.this.try2DownloadSong(entity);
        }
    }

    private void try2DownloadSong(DownloadInfoEntity entity) {
        loadingQueue.add(entity);
        service.submit(new DownloadSongTask(entity));
        String msg = entity + "---" + entity.getSongName() + "  开始下载";
        ToastUtil.getInstance().toast(msg);
    }

    /**
     * 公共方法，供外部调用
     */
    public List<DownloadInfoEntity> getLoadingQueue() {
        return loadingQueue;
    }

    public List<DownloadInfoEntity> getWaitingQueue() {
        return waitingQueue;
    }

    public List<DownloadInfoEntity> getPausedQueue() {
        return pausedQueue;
    }

    @Override
    public void onCreate() {
        service = Executors.newFixedThreadPool(3);   //暂时默认为3
        mBinder = new DownloadBinder();
        //从数据库中拿到所有没有下载完的数据
//        waitingQueue = DownloadDBManager.getInstance().getLoadingSongList();
        for (int i = 0; i < waitingQueue.size(); i++) {
            DownloadInfoEntity entity = waitingQueue.get(i);
            entity.setStatus(DownloadStatusConstants.PAUSED);
//            DownloadDBManager.getInstance().updateInfo(entity);   //将之前未下载完成的任务更新状态
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        if (bundle == null) return super.onStartCommand(intent, flags, startId);
        switch (action) {
            case ADD_TASK:     //添加新的任务
                DownloadInfo downloadInfo = bundle.getParcelable("downloadInfo");
                addNewTask(downloadInfo);
                break;
            case RESUME_TASK:   //继续下载
                String downloadId = bundle.getString("downloadId");  //根据url的hashCode得来
                resumeTask(downloadId);
                break;
            case PAUSE_TASK:
                break;
            case REMOVE_TASK:
                break;

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void resumeTask(String downloadId) {
        /**
         * 下载池已满，则加入缓存池
         */
        if (TextUtils.isEmpty(downloadId)) {
            String msg = downloadId + "不存在";
            ToastUtil.getInstance().toast(msg);
            return;
        }
//        DownloadInfoEntity entity = DownloadDBManager.getInstance().getDownloadEntity(downloadId);
//        loadingQueue.add(entity);
//        service.submit(new DownloadSongTask(entity));
        String msg = downloadId + "---" + "开始下载";
        ToastUtil.getInstance().toast(msg);
    }

    private void addNewTask(DownloadInfo downloadInfo) {
        //开启一个下载任务，提交到下载池
        /**
         *1.判断任务是否已经在执行
         *2.下载池已满，加入到等待池中
         */
        if (TextUtils.isEmpty(downloadInfo.getUrl())) {
            L.i("url is null");
            return;
        }
        String downloadId = String.valueOf(downloadInfo.getUrl().hashCode());
        for (DownloadInfoEntity entity : loadingQueue) {
            if (entity.getDownloadId().equals(downloadId)) {
                String msg = downloadId + "---" + downloadInfo.getSongName() + "  任务正在下载中";
                ToastUtil.getInstance().toast(msg);
                return;
            }
        }

        for (DownloadInfoEntity entity : waitingQueue) {
            if (entity.getDownloadId().equals(downloadId)) {
                String msg = downloadId + "---" + downloadInfo.getSongName() + "  任务已经在等待队列中";
                ToastUtil.getInstance().toast(msg);
                return;
            }
        }
        //创建一个数据库实体
//        DownloadInfoEntity entity = new DownloadInfoEntity(downloadId, downloadInfo.getSongId(), DownloadStatusConstants.INIT, downloadInfo.getUrl(), downloadInfo.getSongName(), downloadInfo.getAuthorName(), DownloadUtil.getDownloadPath(), downloadInfo.getLrc(), downloadInfo.getPic_small(), downloadInfo.getPic_big(), 0l, 0l);
//        DownloadDBManager.getInstance().insertInfo(entity);
//        loadingQueue.add(entity);
//        service.submit(new DownloadSongTask(entity));
        String msg = downloadId + "---" + downloadInfo.getSongName() + "  开始下载";
        ToastUtil.getInstance().toast(msg);
    }
}
