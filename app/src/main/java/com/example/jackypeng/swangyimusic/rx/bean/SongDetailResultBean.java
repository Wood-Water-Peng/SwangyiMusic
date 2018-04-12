package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/11.
 */

public class SongDetailResultBean {
    private SongDetailUrlBean songurl;
    private SongDetailInfoBean songinfo;


    public SongDetailUrlBean getSongurl() {
        return songurl;
    }

    public void setSongurl(SongDetailUrlBean songurl) {
        this.songurl = songurl;
    }

    public SongDetailInfoBean getSonginfo() {
        return songinfo;
    }

    public void setSonginfo(SongDetailInfoBean songinfo) {
        this.songinfo = songinfo;
    }

}
