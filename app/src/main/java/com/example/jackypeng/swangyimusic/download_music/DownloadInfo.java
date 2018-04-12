package com.example.jackypeng.swangyimusic.download_music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/20.
 */

public class DownloadInfo  implements Parcelable{
    private String songId;
    private String url;
    private String authorName;
    private String songName;
    private String albumId;
    private String lrc;
    private String pic_small;
    private String pic_big;

    public DownloadInfo(String songId, String authorName, String songName, String albumId, String lrc, String pic_small, String pic_big) {
        this.songId = songId;
        this.authorName = authorName;
        this.songName = songName;
        this.albumId = albumId;
        this.lrc = lrc;
        this.pic_small = pic_small;
        this.pic_big = pic_big;
    }

    protected DownloadInfo(Parcel in) {
        songId = in.readString();
        url = in.readString();
        authorName = in.readString();
        songName = in.readString();
        albumId = in.readString();
        lrc = in.readString();
        pic_small = in.readString();
        pic_big = in.readString();
    }

    public static final Creator<DownloadInfo> CREATOR = new Creator<DownloadInfo>() {
        @Override
        public DownloadInfo createFromParcel(Parcel in) {
            return new DownloadInfo(in);
        }

        @Override
        public DownloadInfo[] newArray(int size) {
            return new DownloadInfo[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getPic_small() {
        return pic_small;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(songId);
        dest.writeString(url);
        dest.writeString(authorName);
        dest.writeString(songName);
        dest.writeString(albumId);
        dest.writeString(lrc);
        dest.writeString(pic_small);
        dest.writeString(pic_big);
    }
}
