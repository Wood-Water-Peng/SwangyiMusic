package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/18.
 * 音乐的详细信息，保存在本地
 */

public class LocalFolderDetailInfo implements Parcelable{
    public String folder_name;
    public String folder_path;
    public String folder_sort;
    public int folder_count;

    public LocalFolderDetailInfo() {
    }

    protected LocalFolderDetailInfo(Parcel in) {
        folder_name = in.readString();
        folder_path = in.readString();
        folder_sort = in.readString();
        folder_count = in.readInt();
    }

    public static final Creator<LocalFolderDetailInfo> CREATOR = new Creator<LocalFolderDetailInfo>() {
        @Override
        public LocalFolderDetailInfo createFromParcel(Parcel in) {
            return new LocalFolderDetailInfo(in);
        }

        @Override
        public LocalFolderDetailInfo[] newArray(int size) {
            return new LocalFolderDetailInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(folder_name);
        dest.writeString(folder_path);
        dest.writeString(folder_sort);
        dest.writeInt(folder_count);
    }
}
