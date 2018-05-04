package com.example.jackypeng.swangyimusic.download_music;

import android.util.Log;

import com.example.jackypeng.swangyimusic.network.DownloadMusicTask;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.squareup.okhttp.Call;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jackypeng on 2018/4/27.
 * 用户分发进来的网络请求
 */

public class Dispatcher {
    private static final int MAX_THREADS = 1;
    private static final String TAG = "Dispatcher";
    private ExecutorService executor;
    private List<DownloadMusicTask> runningQueues = new ArrayList<>();
    private List<DownloadMusicTask> waitingQueues = new ArrayList<>();

    public Dispatcher() {
        executor = Executors.newFixedThreadPool(MAX_THREADS);
    }

    synchronized void enqueue(DownloadMusicTask task) {
        Log.i(TAG, "size:" + runningQueues.size());
        if (runningQueues.size() < MAX_THREADS) {
            runningQueues.add(task);
            executor.execute(task);
            Log.i(TAG, "running:" + task.getTrack().getId());
        } else {
            DownloadTaskListener listener = task.getListener();
            if (listener != null) {
                listener.onPrepare(task.getTrack());
            }
            Log.i(TAG, "waiting:" + task.getTrack().getId());
            waitingQueues.add(task);
        }
    }


    public synchronized void finished(DownloadMusicTask task) {
        Log.i(TAG, "finished:" + task.getTrack().getId());
        Log.i(TAG, "cur_task:" + runningQueues.get(0).getTrack().getId());
        boolean remove = runningQueues.remove(task);
        Log.i(TAG, "remove_from_running:" + remove);
        //删除数据库中的下载记录
        promoteTasks();
    }

    //执行等待队列中的下一个任务
    synchronized private void promoteTasks() {
        if (waitingQueues.size() > 0) {
            DownloadMusicTask removedTask = waitingQueues.remove(0);
            Log.i(TAG, "removedTask:" + removedTask.getTrack().getId());
            enqueue(removedTask);
        }
    }

    public List<DownloadMusicTask> getRunningQueues() {
        return runningQueues;
    }

    public List<DownloadMusicTask> getWaitingQueues() {
        return waitingQueues;
    }
}
