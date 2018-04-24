package com.example.jackypeng.swangyimusic;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NetApi;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.ui.activity.MainActivity;
import com.example.jackypeng.swangyimusic.util.QueryUtil;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by jackypeng on 2018/3/6.
 * <p>
 * 仿网易云音乐
 */

public class MainApplication extends Application {

    public static Context mContext;
    private static Handler sMainThreadHandler = new Handler();

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Fresco.initialize(this);
        initOkHttpUtils();
    }



    private void initOkHttpUtils() {
        /**
         *  网络配置
         */
        RetrofitUtil.getInstance(this).setConfiguration(new NetWorkConfiguration(this)
                .baseUrl(NetApi.NEW_BASE_URL));
        QueryUtil.getInstance().setConfiguration(new NetWorkConfiguration(this)
                .baseUrl(NetApi.QUERY_BASE_URL));
    }

    public static Handler getMainThreadHandler() {
        return sMainThreadHandler;
    }


}
