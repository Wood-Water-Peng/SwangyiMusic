package com.example.jackypeng.swangyimusic.network;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.download_music.DownloadInfo;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.download_music.DownloadTaskListener;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.rx.api.NetApi;
import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.util.FileUtil;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.example.jackypeng.swangyimusic.util.UIUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.jackypeng.swangyimusic.download_music.DownloadTaskListener.DOWNLOAD_ERROR_FILE_NOT_FOUND;

/**
 * Created by jackypeng on 2018/4/26.
 */

public class DownloadMusicTask implements Runnable {
    private DownloadTaskListener listener;
    private DownloadManager manager;
    private static final int UPDATE_INTERVAL = 5000;
    private Object tag;
    private MusicDownloadTrack track;

    public MusicDownloadTrack getTrack() {
        return track;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    private static final String TAG = "DownloadMusicTask";
    private boolean cancelTask = false;

    public DownloadMusicTask(MusicDownloadTrack track) {
        this(track, null);
    }

    public DownloadMusicTask(MusicDownloadTrack track, DownloadTaskListener listener) {
        this.listener = listener;
        this.manager = DownloadManager.getInstance();
        this.track = track;

    }

    public void setListener(DownloadTaskListener listener) {
        this.listener = listener;
    }

    public DownloadTaskListener getListener() {
        return listener;
    }

    @Override
    public void run() {
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        try {
            /**
             * 1.根据歌曲id去获取歌曲的url
             */
            Log.i(TAG, "准备获取歌曲的url...");
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            String get_info_url = NetApi.NEW_BASE_URL + NetApi.PLAYING_MUSIC_URL + "?id=" + track.getMusicId();
            Log.i(TAG, "请求url---:" + get_info_url);
            builder.url(get_info_url);
            Call call = client.newCall(builder.build());
            Response response = call.execute();
            /**
             * 2.根据得到的url结果去下载歌曲
             */
            Log.i(TAG, "获取url---code:" + response.code());
            if (response.code() > 300) {   //获取url失败
                Log.i(TAG, "获取url失败...");
                if (listener != null) {
                    UIUtil.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(track, DOWNLOAD_ERROR_FILE_NOT_FOUND);
                        }
                    });
                }
                return;
            }
            PlayUrlBean playUrlBean = JSONObject.parseObject(response.body().string(), PlayUrlBean.class);
            track.setUrl(playUrlBean.getData().get(0).getUrl());
            File file = new File(FileUtil.getDownloadMusicDir(), track.getMusicName() + "." + playUrlBean.getData().get(0).getType());
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            track.setAbsolutePath(file.getAbsolutePath());
            URL url = new URL(track.getUrl());
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setConnectTimeout(5000);

            //根据id去数据库中找记录
            long lastLoadedSize = track.getDownload_size();
            httpConnection.setRequestProperty("Range",
                    "bytes=" + lastLoadedSize + "-");
            int responseCode = httpConnection.getResponseCode();
            Log.i(TAG, "responseCode:" + responseCode);
            if (responseCode > 300) {
                Log.i(TAG, "responseCode:" + responseCode + "---下载错误");
                if (listener != null) {
                    UIUtil.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onError(track, DOWNLOAD_ERROR_FILE_NOT_FOUND);
                        }
                    });
                }
                sendDownloadPausedBroadcast();
                return;
            }
            if (responseCode == HttpURLConnection.HTTP_PARTIAL
                    || responseCode == HttpURLConnection.HTTP_OK) {
                is = httpConnection.getInputStream();
                Log.i(TAG, "download_song_file:" + track.getAbsolutePath());
                //开始下载
                Log.i(TAG, "开始下载...");
                if (listener != null) {
                    UIUtil.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onStart(track);
                        }
                    });
                }
                sendStartDownloadBroadcast();
                track.setTotal_size(httpConnection.getContentLength());
                track.setStatus(DownloadStatusConstants.DOWNLOADING);
                DownloadDBManager.getInstance().updateInfo(track);    //更新记录
                RandomAccessFile raf = new RandomAccessFile(new File(track.getAbsolutePath()), "rwd");
                raf.seek(track.getDownload_size());
                final byte[] buf = new byte[1024 * 1024 * 10];
                int len;
                long offset = track.getDownload_size();
                long lastStamp = 0L;
                while (((len = (is.read(buf, 0, buf.length))) != -1) && (!cancelTask)) {
                    raf.write(buf, 0, len);
                    offset += len;
                    //更新下载进度
                    track.setDownload_size(offset);
                    if (listener != null) {
                        UIUtil.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDownloading(track);
                            }
                        });
                    }
                    long timeMillis = System.currentTimeMillis();
                    if (timeMillis - lastStamp > UPDATE_INTERVAL) {
                        lastStamp = timeMillis;
                        //跟新数据库操作
                        track.setDownload_size(offset);
                        DownloadDBManager.getInstance().updateInfo(track);
                    }
                }
                if (cancelTask) {
                    //任务暂停
                    sendDownloadPausedBroadcast();
                    track.setStatus(DownloadStatusConstants.PAUSED);
                    track.setDownload_size(offset);
                    DownloadDBManager.getInstance().updateInfo(track);
                    manager.getDispatcher().finished(DownloadMusicTask.this);
                    if (listener != null) {
                        UIUtil.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onPause(track);
                            }
                        });
                    }
                } else {
                    //任务完成
//                    manager.getDispatcher().finished(this);
                    if (listener != null) {
                        UIUtil.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(track);
                            }
                        });
                    }
                    sendDownloadFinishedBroadcast();
                    track.setDownload_size(offset);
                    track.setStatus(DownloadStatusConstants.FINISHED);
                    DownloadDBManager.getInstance().updateInfo(track);
                    //更新app配置文件中下载音乐文件夹的改动时间和剩余大小
                    FileUtil.saveDirStatus(new File(FileUtil.getDownloadMusicDir().getAbsolutePath()));
                    Log.i(TAG, "下载任务完成");
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Exception:" + e.getMessage());
            //任务暂停
            sendDownloadPausedBroadcast();
            if (listener != null) {
                UIUtil.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onPause(track);
                    }
                });
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //告知管理器，下载完成
            Log.i(TAG, "---finally---");
            manager.getDispatcher().finished(DownloadMusicTask.this);
        }
    }

    public void cancelTask() {
        cancelTask = true;
    }

    private void sendStartDownloadBroadcast() {
        Log.i(TAG, "sendStartDownloadBroadcast");
        Intent intent = new Intent(BroadcastConstants.DOWNLOAD_MUSIC);
        Bundle bundle = new Bundle();
        bundle.putString("musicId", track.getMusicId());
        bundle.putInt("status", DownloadStatusConstants.DOWNLOADING);
        intent.putExtras(bundle);
        MainApplication.getAppContext().sendBroadcast(intent);
    }

    private void sendPauseDownloadBroadcast() {
        Log.i(TAG, "sendPauseDownloadBroadcast");
        Intent intent = new Intent(BroadcastConstants.DOWNLOAD_MUSIC);
        Bundle bundle = new Bundle();
        bundle.putString("musicId", track.getMusicId());
        bundle.putInt("status", DownloadStatusConstants.PAUSED);
        intent.putExtras(bundle);
        MainApplication.getAppContext().sendBroadcast(intent);
    }

    private void sendDownloadFinishedBroadcast() {
        Log.i(TAG, "sendDownloadFinishedBroadcast");
        Intent intent = new Intent(BroadcastConstants.DOWNLOAD_MUSIC);
        Bundle bundle = new Bundle();
        bundle.putString("musicId", track.getMusicId());
        bundle.putInt("status", DownloadStatusConstants.FINISHED);
        intent.putExtras(bundle);
        MainApplication.getAppContext().sendBroadcast(intent);
    }

    private void sendDownloadPausedBroadcast() {
        Log.i(TAG, "sendDownloadPausedBroadcast");
        Intent intent = new Intent(BroadcastConstants.DOWNLOAD_MUSIC);
        Bundle bundle = new Bundle();
        bundle.putString("musicId", track.getMusicId());
        bundle.putInt("status", DownloadStatusConstants.PAUSED);
        intent.putExtras(bundle);
        MainApplication.getAppContext().sendBroadcast(intent);
    }
}
