package com.example.jackypeng.swangyimusic;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.NetworkMsgConstants;
import com.example.jackypeng.swangyimusic.constants.SpConstants;
import com.example.jackypeng.swangyimusic.constants.UserInfoConstants;
import com.example.jackypeng.swangyimusic.eventBus.NetworkChangedEvent;
import com.example.jackypeng.swangyimusic.network.NetworkStatusInfo;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NetApi;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserLoginInfoBean;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.ui.activity.MainActivity;
import com.example.jackypeng.swangyimusic.util.QueryUtil;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;

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
        initNetworkStatus();
        initUserInfo();
    }

    private void initUserInfo() {
        UserLoginInfoBean.getInstance().setUserId(SharePreferenceUtil.readString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_ID));
    }

    private void initNetworkStatus() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取ConnectivityManager对象对应的NetworkInfo对象
        //获取WIFI连接的信息
        NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //获取移动数据连接的信息
        NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.WIFI_MOBILE);
        } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.WIFI_NO_MOBILE);
        } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.NO_WIFI_MOBILE);
        } else {
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.NO_WIFI_NO_MOBILE);
        }

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
