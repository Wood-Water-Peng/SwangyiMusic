package com.example.jackypeng.swangyimusic.rx.bean;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/14.
 */

public class RadioDetailResultBean {
    private String album_id;
    private String fm_id;
    private String album_name;
    private String album_info;
    private List<RadioDetailItemBean> latest_song;
    private List<RadioDetailPicBean> album_pic;

    public String getAlbum_id() {
        return album_id;
    }

    public List<RadioDetailPicBean> getAlbum_pic() {
        return album_pic;
    }

    public void setAlbum_pic(List<RadioDetailPicBean> album_pic) {
        this.album_pic = album_pic;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getFm_id() {
        return fm_id;
    }

    public void setFm_id(String fm_id) {
        this.fm_id = fm_id;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_info() {
        return album_info;
    }

    public void setAlbum_info(String album_info) {
        this.album_info = album_info;
    }

    public List<RadioDetailItemBean> getLatest_song() {
        return latest_song;
    }

    public void setLatest_song(List<RadioDetailItemBean> latest_song) {
        this.latest_song = latest_song;
    }
}
