package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/11.
 * <p>
 * 根据歌曲id获取到的歌曲详情
 */

public class SongDetailInfoBean{
    private String title;    //歌名
    private String author;
    private String album_title;   //专辑名
    private String lrclink;   //歌词地址
    private String pic_radio; //专辑封面
    private String song_id;


    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public String getLrclink() {
        return lrclink;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    public String getPic_radio() {
        return pic_radio;
    }

    public void setPic_radio(String pic_radio) {
        this.pic_radio = pic_radio;
    }

}
