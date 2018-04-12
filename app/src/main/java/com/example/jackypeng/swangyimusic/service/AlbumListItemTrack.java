package com.example.jackypeng.swangyimusic.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/12.
 * 专辑中歌曲列表的记录信息
 */

public class AlbumListItemTrack implements Parcelable {
    private String songName;
    private String queueId;   //用于标识歌曲是否在同一个列表中
    private String album_id;
    private String author;
    private String songId;
    private String lrc;
    private int index;
    private String pic_small_url;
    private String pic_big_url;
    public String localPath;   //本地播放路径
    private int total_duration;
    private int cur_duration;
    private int status;    //当前歌曲的状态
    private boolean isLocal;   //歌曲是否在本地


    protected AlbumListItemTrack(Parcel in) {
        songName = in.readString();
        queueId = in.readString();
        album_id = in.readString();
        author = in.readString();
        songId = in.readString();
        lrc = in.readString();
        index = in.readInt();
        pic_small_url = in.readString();
        pic_big_url = in.readString();
        localPath = in.readString();
        total_duration = in.readInt();
        cur_duration = in.readInt();
        status = in.readInt();
        isLocal = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(queueId);
        dest.writeString(album_id);
        dest.writeString(author);
        dest.writeString(songId);
        dest.writeString(lrc);
        dest.writeInt(index);
        dest.writeString(pic_small_url);
        dest.writeString(pic_big_url);
        dest.writeString(localPath);
        dest.writeInt(total_duration);
        dest.writeInt(cur_duration);
        dest.writeInt(status);
        dest.writeByte((byte) (isLocal ? 1 : 0));
    }

    public static final Creator<AlbumListItemTrack> CREATOR = new Creator<AlbumListItemTrack>() {
        @Override
        public AlbumListItemTrack createFromParcel(Parcel in) {
            return new AlbumListItemTrack(in);
        }

        @Override
        public AlbumListItemTrack[] newArray(int size) {
            return new AlbumListItemTrack[size];
        }
    };

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPic_small_url() {
        return pic_small_url;
    }

    public void setPic_small_url(String pic_small_url) {
        this.pic_small_url = pic_small_url;
    }

    public String getPic_big_url() {
        return pic_big_url;
    }

    public void setPic_big_url(String pic_big_url) {
        this.pic_big_url = pic_big_url;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(int total_duration) {
        this.total_duration = total_duration;
    }

    public int getCur_duration() {
        return cur_duration;
    }

    public void setCur_duration(int cur_duration) {
        this.cur_duration = cur_duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static Creator<AlbumListItemTrack> getCREATOR() {
        return CREATOR;
    }

    public AlbumListItemTrack() {
    }


}
