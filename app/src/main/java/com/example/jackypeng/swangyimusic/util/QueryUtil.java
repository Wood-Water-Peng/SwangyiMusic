package com.example.jackypeng.swangyimusic.util;

import android.content.Context;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.NetWorkConfiguration;
import com.example.jackypeng.swangyimusic.rx.QueryRetrofitClient;
import com.example.jackypeng.swangyimusic.rx.interceptor.HeaderInterceptor;
import com.example.jackypeng.swangyimusic.rx.interceptor.LogInterceptor;
import com.example.jackypeng.swangyimusic.ui.RetrofitClient;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jackypeng on 2017/11/28.
 * 根据albumId,artist查询详细信息的工具类
 */

public class QueryUtil {
    //    RetrofitUtil
    private static QueryUtil mInstance;
    private NetWorkConfiguration configuration;
    //    OkHttpClient对象
    private OkHttpClient mOkHttpClient;

    public QueryUtil(Context context) {
        /**
         * 进行默认配置
         * 未配置configuration
         */
        if (configuration == null) {
            configuration = new NetWorkConfiguration(context);
        }
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .connectionPool(configuration.getConnectionPool())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LogInterceptor())
                .retryOnConnectionFailure(true)
                .build();
    }

    public void setConfiguration(NetWorkConfiguration configuration) {
        this.configuration = configuration;
    }

    public QueryRetrofitClient getRetrofitClient() {
        return new QueryRetrofitClient(configuration.getBaseUrl(), mOkHttpClient);
    }

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static QueryUtil getInstance() {
        if (mInstance == null) {
            synchronized (QueryUtil.class) {
                if (mInstance == null) {
                    mInstance = new QueryUtil(MainApplication.getAppContext());
                }
            }
        }
        return mInstance;
    }

}


