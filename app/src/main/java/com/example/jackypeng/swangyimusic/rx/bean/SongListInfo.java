package com.example.jackypeng.swangyimusic.rx.bean;

/**
 * Created by jackypeng on 2018/3/18.
 * 歌单信息---创建的歌单、收藏的歌单
 */

public class SongListInfo {
    private long id;   //用于标识歌单
    private String cover;   //歌单封面的地址
    private String name;   //歌单名词
    private String count;   //歌单中歌曲的数量

    public SongListInfo(long id, String cover, String name, String count) {
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.count = count;
    }
}
