package com.example.jackypeng.swangyimusic.rx.model;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class SongMenuModel implements BaseModel {
    public Observable<FreshMusicBean> getFreshMusic() {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getFreshMusic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
