package com.example.jackypeng.swangyimusic.eventBus;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class NetworkChangedEvent {
    private int msg;

    public NetworkChangedEvent(int msg) {
        this.msg = msg;
    }

    public int getMsg() {
        return msg;
    }
}
