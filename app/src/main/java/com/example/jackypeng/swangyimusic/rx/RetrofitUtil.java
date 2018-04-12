package com.example.jackypeng.swangyimusic.rx;

import android.content.Context;

import com.example.jackypeng.swangyimusic.NetWorkConfiguration;
import com.example.jackypeng.swangyimusic.rx.interceptor.HeaderInterceptor;
import com.example.jackypeng.swangyimusic.rx.interceptor.LogInterceptor;
import com.example.jackypeng.swangyimusic.ui.RetrofitClient;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jackypeng on 2017/11/28.
 */

public class RetrofitUtil {
    //    RetrofitUtil
    private static RetrofitUtil mInstance;
    private NetWorkConfiguration configuration;
    //    OkHttpClient对象
    private OkHttpClient mOkHttpClient;

    public RetrofitUtil(Context context) {
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

    public RetrofitClient getRetrofitClient() {
        return new RetrofitClient(configuration.getBaseUrl(), mOkHttpClient);
    }

    /**
     * 获取请求网络实例
     *
     * @return
     */
    public static RetrofitUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil(context);
                }
            }
        }
        return mInstance;
    }

}


