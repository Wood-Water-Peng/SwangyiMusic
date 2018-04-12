package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/11.
 * <p>
 * 根据歌曲id获取到的歌曲详情
 */

public class SongDetailUrlBean{
    List<SongDetailUrlItemBean> url;
    int error_code;

    public List<SongDetailUrlItemBean> getUrl() {
        return url;
    }

    public void setUrl(List<SongDetailUrlItemBean> url) {
        this.url = url;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

}
