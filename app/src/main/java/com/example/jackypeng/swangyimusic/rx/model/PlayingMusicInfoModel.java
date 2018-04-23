package com.example.jackypeng.swangyimusic.rx.model;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayingLrcBean;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.contract.MusicUrlContract;
import com.example.jackypeng.swangyimusic.rx.contract.RadioProgramContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class PlayingMusicInfoModel implements MusicUrlContract.Model {
    @Override
    public Observable<PlayUrlBean> getPlayingMusicUrl(String id) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getPlayingUrl(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PlayingLrcBean> getPlayingMusicLrc(String id) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getPlayingLrc(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
