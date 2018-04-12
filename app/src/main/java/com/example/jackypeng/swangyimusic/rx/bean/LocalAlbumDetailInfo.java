package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class LocalAlbumDetailInfo implements Parcelable{

    //专辑名称
    public String album_name;
    //专辑在数据库中的id
    public String album_id;
    //专辑的歌曲数目
    public int number_of_songs = 0;
    //专辑封面图片路径
    public String album_art;
    public String album_artist;
    public String album_sort;

    public LocalAlbumDetailInfo() {
    }

    protected LocalAlbumDetailInfo(Parcel in) {
        album_name = in.readString();
        album_id = in.readString();
        number_of_songs = in.readInt();
        album_art = in.readString();
        album_artist = in.readString();
        album_sort = in.readString();
    }

    public static final Creator<LocalAlbumDetailInfo> CREATOR = new Creator<LocalAlbumDetailInfo>() {
        @Override
        public LocalAlbumDetailInfo createFromParcel(Parcel in) {
            return new LocalAlbumDetailInfo(in);
        }

        @Override
        public LocalAlbumDetailInfo[] newArray(int size) {
            return new LocalAlbumDetailInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(album_name);
        dest.writeString(album_id);
        dest.writeInt(number_of_songs);
        dest.writeString(album_art);
        dest.writeString(album_artist);
        dest.writeString(album_sort);
    }
}
