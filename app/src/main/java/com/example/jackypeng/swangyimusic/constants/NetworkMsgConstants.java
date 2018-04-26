package com.example.jackypeng.swangyimusic.constants;

/**
 * Created by jackypeng on 2018/4/25.
 * 网络信息变化的常量
 */

public class NetworkMsgConstants {
    //高位表示wifi,低位表示移动数据
    public static final int WIFI_MOBILE = 0x11;
    public static final int NO_WIFI_MOBILE = 0x01;
    public static final int WIFI_NO_MOBILE = 0x10;
    public static final int NO_WIFI_NO_MOBILE = 0x00;


    public static final int NETWORK_DISCONNECT = 10;
    public static final int NETWORK_AVAILABLE = 11;


}
