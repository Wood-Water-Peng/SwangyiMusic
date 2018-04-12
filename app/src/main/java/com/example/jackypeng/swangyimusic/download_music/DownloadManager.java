package com.example.jackypeng.swangyimusic.download_music;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.api.NetApi;
import com.example.jackypeng.swangyimusic.util.AESTools;
import com.example.jackypeng.swangyimusic.util.NetUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by jackypeng on 2018/3/20.
 */

public class DownloadManager {
    public static final String TAG = "DownloadDBManager";
    private static DownloadManager instance;

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

    /**
     * @param downloadInfo 1.根据songId,获得歌曲的详细信息
     *                     2.拿到下载link
     */
    public void startDownload(final DownloadInfo downloadInfo) {
        long timeMillis = System.currentTimeMillis();
        String str = "songid=" + downloadInfo.getSongId() + "&ts=" + timeMillis;
        String e = AESTools.encrpty(str);
        StringBuilder builder = new StringBuilder(NetApi.BASE_URL + NetApi.GET_SONG_DETAIL);
        builder.append("&")
                .append(str)
                .append("&e=")
                .append(e);
        Log.i(TAG, "request_url:" + builder.toString());
        NetUtil.getInstance().fetchData(builder.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "response:" + result);
                //如果成功，将信息提交到DownloadService
                String url = JSONObject.parseObject(result).getJSONObject("songurl").getJSONArray("url").getJSONObject(0).getString("show_link");
                Intent intent = new Intent(MainApplication.getAppContext(), DownloadService.class);
                intent.setAction(DownloadService.ADD_TASK);
                Bundle bundle = new Bundle();
                downloadInfo.setUrl(url);
                bundle.putParcelable("downloadInfo", downloadInfo);
                intent.putExtras(bundle);
                //该服务的生命周期和App相同
                MainApplication.getAppContext().startService(intent);
            }
        });
    }
}
