package com.example.jackypeng.swangyimusic.rx.model;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.contract.PlayingListDetailContract;
import com.example.jackypeng.swangyimusic.rx.contract.RadioProgramContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class PlayingListDetailModel implements PlayingListDetailContract.Model {
    @Override
    public Observable<PlayingListDetailResult> getPlayingListDetail(String rid) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getPlayingListDetail(rid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
