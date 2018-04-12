package com.example.jackypeng.swangyimusic;

import android.content.Context;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;

/**
 * Created by jackypeng on 2017/11/28.
 */

public final class NetWorkConfiguration {
    /**
     * 默认缓存
     */
    private boolean isCache;
    //    是否进行磁盘缓存
    private boolean isDiskCache;
    //        是否进行内存缓存
    private boolean isMemoryCache;
    //    内存缓存时间单位S （默认为60s）
    private int memoryCacheTime;

    //    本地缓存时间单位S (默认为4周)
    private int diskCacheTime;

    //    缓存本地大小 单位字节（默认为30M）
    private int maxDiskCacheSize;
    //      缓存路径
    private Cache diskCache;

    //     设置超时时间
    private int connectTimeout;
    //    设置网络最大连接数
    private ConnectionPool connectionPool;
    //    设置HttpS客户端带证书访问
    private InputStream[] certificates;
    public Context context;

    //    设置网络BaseUrl地址
    private String baseUrl;

    public NetWorkConfiguration(Context context) {
        this.isCache = false;
        this.isDiskCache = false;
        this.isMemoryCache = false;
        this.memoryCacheTime = 60;
        this.diskCacheTime = 60 * 60 * 24 * 28;
        this.maxDiskCacheSize = 30 * 1024 * 1024;
        this.context = context.getApplicationContext();
        this.diskCache = new Cache(new File(this.context.getCacheDir(), "network"), maxDiskCacheSize);
        this.connectTimeout = 10000;
        this.connectionPool = new ConnectionPool(50, 60, TimeUnit.SECONDS);
        certificates = null;
        baseUrl = null;
    }

    public boolean isCache() {
        return isCache;
    }

    public boolean isDiskCache() {
        return isDiskCache;
    }

    public boolean isMemoryCache() {
        return isMemoryCache;
    }

    public int getMemoryCacheTime() {
        return memoryCacheTime;
    }

    public int getDiskCacheTime() {
        return diskCacheTime;
    }

    public int getMaxDiskCacheSize() {
        return maxDiskCacheSize;
    }

    public Cache getDiskCache() {
        return diskCache;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public InputStream[] getCertificates() {
        return certificates;
    }

    public Context getContext() {
        return context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public NetWorkConfiguration baseUrl(String qmtRequestUrlBase) {
        this.baseUrl=qmtRequestUrlBase;
        return this;
    }
}
