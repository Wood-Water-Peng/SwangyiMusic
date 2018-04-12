package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/18.
 * 音乐的详细信息，保存在本地
 */

public class LocalMusicDetailInfo implements Parcelable{
    public static final String KEY_SONG_ID = "songid";
    public static final String KEY_ALBUM_ID = "albumid";
    public static final String KEY_ALBUM_NAME = "albumname";
    public static final String KEY_ALBUM_DATA = "albumdata";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_MUSIC_NAME = "musicname";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_ARTIST_ID = "artist_id";
    public static final String KEY_DATA = "data";
    public static final String KEY_FOLDER = "folder";
    public static final String KEY_SIZE = "size";
    public static final String KEY_FAVORITE = "favorite";
    public static final String KEY_LRC = "lrc";
    public static final String KEY_ISLOCAL = "islocal";
    public static final String KEY_SORT = "sort";


    public String songId;
    public String albumId;
    public String albumName;
    public String albumData;
    public int duration;
    public String musicName;
    public String artist;
    public String artistId;
    public String data;
    public String folder;
    public String lrc;
    public boolean islocal;
    public String sort;
    public int size;


    public LocalMusicDetailInfo() {
    }

    protected LocalMusicDetailInfo(Parcel in) {
        songId = in.readString();
        albumId = in.readString();
        albumName = in.readString();
        albumData = in.readString();
        duration = in.readInt();
        musicName = in.readString();
        artist = in.readString();
        artistId = in.readString();
        data = in.readString();
        folder = in.readString();
        lrc = in.readString();
        islocal = in.readByte() != 0;
        sort = in.readString();
        size = in.readInt();
    }

    public static final Creator<LocalMusicDetailInfo> CREATOR = new Creator<LocalMusicDetailInfo>() {
        @Override
        public LocalMusicDetailInfo createFromParcel(Parcel in) {
            return new LocalMusicDetailInfo(in);
        }

        @Override
        public LocalMusicDetailInfo[] newArray(int size) {
            return new LocalMusicDetailInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songId);
        dest.writeString(albumId);
        dest.writeString(albumName);
        dest.writeString(albumData);
        dest.writeInt(duration);
        dest.writeString(musicName);
        dest.writeString(artist);
        dest.writeString(artistId);
        dest.writeString(data);
        dest.writeString(folder);
        dest.writeString(lrc);
        dest.writeByte((byte) (islocal ? 1 : 0));
        dest.writeString(sort);
        dest.writeInt(size);
    }
}
