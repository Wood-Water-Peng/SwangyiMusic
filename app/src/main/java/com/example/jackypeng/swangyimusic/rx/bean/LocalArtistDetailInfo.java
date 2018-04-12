package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class LocalArtistDetailInfo implements Parcelable {

    public static final String KEY_ARTIST_NAME = "artist_name";
    public static final String KEY_NUMBER_OF_TRACKS = "number_of_tracks";
    public static final String KEY_ARTIST_ID = "artist_id";

    public String artist_name;
    public int number_of_tracks;
    public String artist_id;
    public String artist_sort;


    public LocalArtistDetailInfo() {
    }

    public LocalArtistDetailInfo(Parcel in) {
        artist_name = in.readString();
        number_of_tracks = in.readInt();
        artist_id = in.readString();
        artist_sort = in.readString();
    }

    public static final Creator<LocalArtistDetailInfo> CREATOR = new Creator<LocalArtistDetailInfo>() {
        @Override
        public LocalArtistDetailInfo createFromParcel(Parcel in) {
            return new LocalArtistDetailInfo(in);
        }

        @Override
        public LocalArtistDetailInfo[] newArray(int size) {
            return new LocalArtistDetailInfo[size];
        }
    };

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public int getNumber_of_tracks() {
        return number_of_tracks;
    }

    public void setNumber_of_tracks(int number_of_tracks) {
        this.number_of_tracks = number_of_tracks;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist_sort() {
        return artist_sort;
    }

    public void setArtist_sort(String artist_sort) {
        this.artist_sort = artist_sort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artist_name);
        dest.writeInt(number_of_tracks);
        dest.writeString(artist_id);
        dest.writeString(artist_sort);
    }
}
