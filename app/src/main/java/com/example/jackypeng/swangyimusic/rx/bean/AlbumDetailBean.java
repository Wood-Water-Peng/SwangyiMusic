package com.example.jackypeng.swangyimusic.rx.bean;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class AlbumDetailBean {
    private AlbumInfoBean albumInfo;
    private List<AlbumSongItemBean> songlist;

    public AlbumInfoBean getAlbuminfo() {
        return albumInfo;
    }

    public void setAlbuminfo(AlbumInfoBean albuminfo) {
        this.albumInfo = albuminfo;
    }

    public List<AlbumSongItemBean> getSonglist() {
        return songlist;
    }

    public void setSonglist(List<AlbumSongItemBean> songlist) {
        this.songlist = songlist;
    }
}
