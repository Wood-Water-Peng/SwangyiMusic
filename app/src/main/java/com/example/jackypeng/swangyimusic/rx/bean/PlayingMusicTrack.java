package com.example.jackypeng.swangyimusic.rx.bean;

import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/12.
 * 该对象被保存到sp中，用户记录播放歌曲的状态
 * 包括封面url，title，author，album_id,播放总时长，当前播放时长，该专辑的音乐列表等等
 */

public class PlayingMusicTrack {
    private String album_cover;
    private String album_name;
    private String album_author;
    private String album_id;
    private String playing_song_id;
    private long song_total_duration;
    private long song_current_duration;
    private int cur_index;  //当前歌曲在列表中是第几首
    private List<AlbumListItemTrack> songList;  //专辑列表

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_author() {
        return album_author;
    }

    public void setAlbum_author(String album_author) {
        this.album_author = album_author;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getPlaying_song_id() {
        return playing_song_id;
    }

    public void setPlaying_song_id(String playing_song_id) {
        this.playing_song_id = playing_song_id;
    }

    public long getSong_total_duration() {
        return song_total_duration;
    }

    public void setSong_total_duration(long song_total_duration) {
        this.song_total_duration = song_total_duration;
    }

    public long getSong_current_duration() {
        return song_current_duration;
    }

    public void setSong_current_duration(long song_current_duration) {
        this.song_current_duration = song_current_duration;
    }

    public int getCur_index() {
        return cur_index;
    }

    public void setCur_index(int cur_index) {
        this.cur_index = cur_index;
    }

    public List<AlbumListItemTrack> getSongList() {
        return songList;
    }

    public void setSongList(List<AlbumListItemTrack> songList) {
        this.songList = songList;
    }
}
