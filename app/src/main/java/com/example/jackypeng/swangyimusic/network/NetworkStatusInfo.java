package com.example.jackypeng.swangyimusic.network;


/**
 * Created by jackypeng on 2018/4/25.
 * 网络状态信息
 */

public class NetworkStatusInfo {
    private static NetworkStatusInfo sInstance;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private NetworkStatusInfo() {
    }

    public static NetworkStatusInfo getInstance() {
        synchronized (NetworkStatusInfo.class) {
            if (sInstance == null) {
                sInstance = new NetworkStatusInfo();
            }
        }
        return sInstance;
    }


}
