package com.example.jackypeng.swangyimusic.download_music;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jackypeng on 2018/4/27.
 * 保存在数据库中的
 */
@Entity
public class MusicDownloadTrack {

    @Id(autoincrement = true)
    private Long id;  //数据库的序号
    @Unique
    private String musicId; //歌曲的唯一表示
    private String absolutePath; //文件路径
    private String url; //歌曲的下载链接
    private long total_size; //文件总大小
    private long download_size; //文件已下载大小
    private String musicName; //歌曲名称
    private String artistName; //作者名称
    private String albumName; //专辑名称
    private int status;  //下载状态
    private String albumId;  //所属歌单的id
    @Generated(hash = 1042546437)
    public MusicDownloadTrack(Long id, String musicId, String absolutePath,
            String url, long total_size, long download_size, String musicName,
            String artistName, String albumName, int status, String albumId) {
        this.id = id;
        this.musicId = musicId;
        this.absolutePath = absolutePath;
        this.url = url;
        this.total_size = total_size;
        this.download_size = download_size;
        this.musicName = musicName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.status = status;
        this.albumId = albumId;
    }
    @Generated(hash = 670330881)
    public MusicDownloadTrack() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMusicId() {
        return this.musicId;
    }
    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
    public String getAbsolutePath() {
        return this.absolutePath;
    }
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public long getTotal_size() {
        return this.total_size;
    }
    public void setTotal_size(long total_size) {
        this.total_size = total_size;
    }
    public long getDownload_size() {
        return this.download_size;
    }
    public void setDownload_size(long download_size) {
        this.download_size = download_size;
    }
    public String getMusicName() {
        return this.musicName;
    }
    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
    public String getArtistName() {
        return this.artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getAlbumName() {
        return this.albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getAlbumId() {
        return this.albumId;
    }
    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }


}
