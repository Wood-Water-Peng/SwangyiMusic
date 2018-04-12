package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/11.
 * <p>
 * 根据歌曲id获取到的歌曲详情
 */

public class SongDetailUrlItemBean{
    private String show_link;

    public String getShow_link() {
        return show_link;
    }

    public void setShow_link(String show_link) {
        this.show_link = show_link;
    }

}
