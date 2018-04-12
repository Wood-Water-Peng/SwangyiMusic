package com.example.jackypeng.swangyimusic.rx.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jackypeng on 2018/3/19.
 */

@Entity
public class RecentPlayBean {
    @Unique
    private String id;
    private long time_played;


    @Generated(hash = 679313208)
    public RecentPlayBean(String id, long time_played) {
        this.id = id;
        this.time_played = time_played;
    }

    @Generated(hash = 162356357)
    public RecentPlayBean() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime_played() {
        return time_played;
    }

    public void setTime_played(long time_played) {
        this.time_played = time_played;
    }
}
