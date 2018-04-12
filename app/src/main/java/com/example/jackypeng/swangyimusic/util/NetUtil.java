package com.example.jackypeng.swangyimusic.util;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by jackypeng on 2018/3/12.
 */

public class NetUtil {
    private static NetUtil sInstance;
    private OkHttpClient client;

    public static NetUtil getInstance() {
        if (sInstance == null) {
            synchronized (NetUtil.class) {
                if (sInstance == null) {
                    sInstance = new NetUtil();
                }
            }
        }
        return sInstance;
    }

    private NetUtil() {
        client = new OkHttpClient();
    }

    public void fetchData(String url, Callback callback) {
        Request request = new Request.Builder().url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
