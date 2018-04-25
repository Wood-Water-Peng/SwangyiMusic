package com.example.jackypeng.swangyimusic.rx.model;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.LoginResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserPlayListBean;
import com.example.jackypeng.swangyimusic.rx.contract.LoginContract;
import com.example.jackypeng.swangyimusic.rx.contract.UserPlayListContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/4/25.
 */

public class UserPlayListModel implements UserPlayListContract.Model {

    @Override
    public Observable<UserPlayListBean> getUserPlayList(String uid) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .getUserPlayList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
