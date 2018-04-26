package com.example.jackypeng.swangyimusic.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.jackypeng.swangyimusic.MainApplication;

/**
 * Created by jackypeng on 2018/4/25.
 */

public class NetworkCheckUtil {
    /**
     * 判断当前网络是否是wifi网络
     *
     * @return boolean
     */
    public static boolean isWifiAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MainApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有网络连接(wifi和4g)
     */
    public static boolean isNetworkConnected() {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) MainApplication.getAppContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空
        if (networkInfo != null)
            return networkInfo.isAvailable();
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     */
    public static boolean isMobileConnected() {
        //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager manager = (ConnectivityManager) MainApplication.getAppContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
            return networkInfo.isAvailable();
        return false;
    }

}
