package com.example.jackypeng.swangyimusic.rx.model;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.SongDetailResultBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/3/11.
 * g根据歌曲id，获得歌曲详情
 */

public class GetSongDetailModel {
    public Observable<SongDetailResultBean> getSongDetail(String songId, String ts, String e) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getSongDetail(songId, ts, e)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
