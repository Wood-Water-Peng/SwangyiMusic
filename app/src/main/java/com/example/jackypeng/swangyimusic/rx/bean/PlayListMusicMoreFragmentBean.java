package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/4/26.
 * 歌单中的歌曲详情，用来对歌曲进一步操作
 */

public class PlayListMusicMoreFragmentBean implements Parcelable{
    private String id;
    private String musicName;
    private String artistName;
    private String albumName;

    public PlayListMusicMoreFragmentBean() {
    }

    protected PlayListMusicMoreFragmentBean(Parcel in) {
        id = in.readString();
        musicName = in.readString();
        artistName = in.readString();
        albumName = in.readString();
    }

    public static final Creator<PlayListMusicMoreFragmentBean> CREATOR = new Creator<PlayListMusicMoreFragmentBean>() {
        @Override
        public PlayListMusicMoreFragmentBean createFromParcel(Parcel in) {
            return new PlayListMusicMoreFragmentBean(in);
        }

        @Override
        public PlayListMusicMoreFragmentBean[] newArray(int size) {
            return new PlayListMusicMoreFragmentBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(musicName);
        dest.writeString(artistName);
        dest.writeString(albumName);
    }
}
