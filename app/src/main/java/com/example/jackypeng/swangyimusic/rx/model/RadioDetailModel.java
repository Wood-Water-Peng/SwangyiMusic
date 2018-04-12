package com.example.jackypeng.swangyimusic.rx.model;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumDetailBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResponseBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResultBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class RadioDetailModel implements BaseModel {
    public Observable<RadioDetailResponseBean> getRadioDetail(String albumId, int num) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getRadioDetail(albumId, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
