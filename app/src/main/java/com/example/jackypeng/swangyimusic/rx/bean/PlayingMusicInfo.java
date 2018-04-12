package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/8.
 * <p>
 * 待要播放的音乐列表
 */

public class PlayingMusicInfo implements Parcelable {
    public long id;
    public int position;
    public String title;
    public String album;
    public String author;

    protected PlayingMusicInfo(Parcel in) {
        id = in.readLong();
        position = in.readInt();
        title = in.readString();
        album = in.readString();
        author = in.readString();
    }

    public static final Creator<PlayingMusicInfo> CREATOR = new Creator<PlayingMusicInfo>() {
        @Override
        public PlayingMusicInfo createFromParcel(Parcel in) {
            return new PlayingMusicInfo(in);
        }

        @Override
        public PlayingMusicInfo[] newArray(int size) {
            return new PlayingMusicInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(position);
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(author);
    }
}
