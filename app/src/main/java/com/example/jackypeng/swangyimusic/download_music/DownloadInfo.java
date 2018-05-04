package com.example.jackypeng.swangyimusic.download_music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/20.
 */

public class DownloadInfo {
    private String songId;
    private String url;
    private String authorName;
    private String songName;
    private String albumId;
    private String fileType;  //文件类型(mp3,mp4）
    private long total_size;
    private long download_size;  //文件类型(mp3,mp4）


    public DownloadInfo() {
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getTotal_size() {
        return total_size;
    }

    public void setTotal_size(long total_size) {
        this.total_size = total_size;
    }

    public long getDownload_size() {
        return download_size;
    }

    public void setDownload_size(long download_size) {
        this.download_size = download_size;
    }
}
