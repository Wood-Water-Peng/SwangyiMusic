package com.example.jackypeng.swangyimusic.rx.bean;

import com.example.jackypeng.swangyimusic.download_music.DownloadTaskListener;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jackypeng on 2018/3/19.
 */
@Entity
public class DownloadInfoEntity {


    @Transient
    private DownloadTaskListener listener;

    @Unique
    private String downloadId;
    private String songId;
    private int status;   //状态
    private String url;
    private String songName;
    private String authorName;
    private String path;
    private String lrc;
    private String pic_small;
    private String pic_big;
    private long loadedSize;
    private long totalSize;


    @Generated(hash = 1933133395)
    public DownloadInfoEntity(String downloadId, String songId, int status,
            String url, String songName, String authorName, String path, String lrc,
            String pic_small, String pic_big, long loadedSize, long totalSize) {
        this.downloadId = downloadId;
        this.songId = songId;
        this.status = status;
        this.url = url;
        this.songName = songName;
        this.authorName = authorName;
        this.path = path;
        this.lrc = lrc;
        this.pic_small = pic_small;
        this.pic_big = pic_big;
        this.loadedSize = loadedSize;
        this.totalSize = totalSize;
    }

    @Generated(hash = 1357263013)
    public DownloadInfoEntity() {
    }


    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DownloadTaskListener getListener() {
        return listener;
    }

    public void setListener(DownloadTaskListener listener) {
        this.listener = listener;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLoadedSize() {
        return loadedSize;
    }

    public void setLoadedSize(long loadedSize) {
        this.loadedSize = loadedSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getDownloadId() {
        return this.downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }
}
