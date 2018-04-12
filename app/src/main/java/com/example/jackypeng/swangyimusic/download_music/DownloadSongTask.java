package com.example.jackypeng.swangyimusic.download_music;

import android.util.Log;

import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.eventBus.DownloadSongFinishedEvent;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.example.jackypeng.swangyimusic.util.UIUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jackypeng on 2017/12/20.
 */

public class DownloadSongTask implements Runnable {
    private static final String TAG = "DownloadSongTask";
    private DownloadInfoEntity downloadInfo;
    private DownloadTaskListener taskListener;

    public DownloadSongTask(DownloadInfoEntity downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    @Override
    public void run() {
        long lastLoadedSize = downloadInfo.getLoadedSize();
        long totalSize = downloadInfo.getTotalSize();
        String savePath = downloadInfo.getPath();
        HttpURLConnection httpConnection = null;
        taskListener = downloadInfo.getListener();
        try {
            URL url = new URL(downloadInfo.getUrl());
            Log.i(TAG, "url:" + url);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(5000);
            httpConnection.setRequestProperty("Range",
                    "bytes=" + lastLoadedSize + "-");
            int responseCode = httpConnection.getResponseCode();
            Log.i(TAG, "responseCode:" + responseCode);
            InputStream is;
            if (responseCode > 300) {
                ToastUtil.getInstance().toast("responseCode:" + responseCode + "---下载错误");
                if (taskListener != null) {
                    UIUtil.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            taskListener.onCancel(downloadInfo);  //取消下载

                        }
                    });
                }
                return;
            }
            if (responseCode == HttpURLConnection.HTTP_PARTIAL
                    || responseCode == HttpURLConnection.HTTP_OK) {
                downloadInfo.setStatus(DownloadStatusConstants.DOWNLOADING);
                if (totalSize == 0) {
                    downloadInfo.setTotalSize(httpConnection.getContentLength());
                }
                DownloadDBManager.getInstance().updateInfo(downloadInfo);
                if (taskListener != null) {
                    UIUtil.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            taskListener.onStart(downloadInfo);   //开始下载
                        }
                    });
                }
                is = httpConnection.getInputStream();
                File file = new File(savePath + downloadInfo.getSongName());
                if (!file.exists()) {
                    Log.i(TAG, "savePath:" + file.getAbsolutePath());
                    file.createNewFile();
                }
                //开始下载
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                Log.i(TAG, "download_url:" + downloadInfo.getUrl());
                raf.seek(lastLoadedSize);
                final byte[] buf = new byte[1024 * 1024 * 10];
                int len;
                long offset = lastLoadedSize;
                long lastUpdateSize = 0L;
                while ((len = (is.read(buf, 0, buf.length))) != -1 && (downloadInfo.getStatus() != DownloadStatusConstants.PAUSED)) {
                    raf.write(buf, 0, len);
                    offset += len;
                    //更新下载进度
                    downloadInfo.setLoadedSize(offset);
                    DownloadDBManager.getInstance().updateInfo(downloadInfo);
                    if (offset - lastUpdateSize > 1000 * 100) {
                        lastUpdateSize = offset;
                        if (taskListener != null) {
                            UIUtil.runInMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    taskListener.onDownloading(downloadInfo);   //跟新进度
                                }
                            });
                        }
                    }
//                    Thread.sleep(500);
                }
                //通知监听者下载状态
                //更新下载记录到数据库中
                if (downloadInfo.getStatus() == DownloadStatusConstants.PAUSED) {
                    //暂停下载
                    if (taskListener != null) {
                        UIUtil.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                taskListener.onPause(downloadInfo);   //暂停下载
                            }
                        });
                    }
                    downloadInfo.setStatus(DownloadStatusConstants.PAUSED);
                    DownloadDBManager.getInstance().updateInfo(downloadInfo);
                } else {
                    if (taskListener != null) {
                        UIUtil.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                taskListener.onCompleted(downloadInfo);   //完成下载
                            }
                        });
                    }
                    downloadInfo.setStatus(DownloadStatusConstants.FINISHED);
                    DownloadDBManager.getInstance().updateInfo(downloadInfo);
                    EventBus.getDefault().post(new DownloadSongFinishedEvent());  //通知
                    Log.i(TAG, "---下载完成---");
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "下载文件异常:" + e.getMessage());
            //下载错误
            if (taskListener != null) {
                UIUtil.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        taskListener.onError(downloadInfo, 0);   //下载异常
                    }
                });
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }
}
