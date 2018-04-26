package com.example.jackypeng.swangyimusic.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.jackypeng.swangyimusic.constants.NetworkMsgConstants;
import com.example.jackypeng.swangyimusic.eventBus.NetworkChangedEvent;
import com.example.jackypeng.swangyimusic.network.NetworkStatusInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jackypeng on 2018/4/25.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        //获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取ConnectivityManager对象对应的NetworkInfo对象
        //获取WIFI连接的信息
        NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //获取移动数据连接的信息
        NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//            Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "WIFI已连接,移动数据已连接");
            EventBus.getDefault().post(new NetworkChangedEvent(NetworkMsgConstants.WIFI_MOBILE));
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.WIFI_MOBILE);
        } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//            Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "WIFI已连接,移动数据已断开");
            EventBus.getDefault().post(new NetworkChangedEvent(NetworkMsgConstants.WIFI_NO_MOBILE));
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.WIFI_NO_MOBILE);
        } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//            Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "WIFI已断开,移动数据已连接");
            EventBus.getDefault().post(new NetworkChangedEvent(NetworkMsgConstants.NO_WIFI_MOBILE));
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.NO_WIFI_MOBILE);
        } else {
//            Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "WIFI已断开,移动数据已断开");
            EventBus.getDefault().post(new NetworkChangedEvent(NetworkMsgConstants.NO_WIFI_NO_MOBILE));
            NetworkStatusInfo.getInstance().setStatus(NetworkMsgConstants.NO_WIFI_NO_MOBILE);
        }
//API大于23时使用下面的方式进行网络监听
    }
}

