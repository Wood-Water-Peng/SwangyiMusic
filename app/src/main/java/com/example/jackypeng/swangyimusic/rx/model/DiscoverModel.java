package com.example.jackypeng.swangyimusic.rx.model;

import android.content.Context;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NetApi;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.contract.DiscoverContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class DiscoverModel implements DiscoverContract.Model {
    @Override
    public Observable<BannerResultBean> getBanner(Context context) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .setBaseUrl(NetApi.NEW_BASE_URL)
                .builder(NewMusicApi.class)
                .getDiscoverBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RecommendListResult> getRecommendList(Context context,int limit) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .setBaseUrl(NetApi.NEW_BASE_URL)
                .builder(NewMusicApi.class)
                .getDiscoverRecommendList(limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RecommendRadioResult> getRecommendRadio(Context context) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .setBaseUrl(NetApi.NEW_BASE_URL)
                .builder(NewMusicApi.class)
                .getDiscoverRecommendRadio()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RecommendMVResult> getRecommendMV(Context context) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .setBaseUrl(NetApi.NEW_BASE_URL)
                .builder(NewMusicApi.class)
                .getDiscoverRecommendMV()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ExclusivePartResult> getExclusivePart(Context context) {
        return RetrofitUtil.getInstance(MainApplication.getAppContext())
                .getRetrofitClient()
                .setBaseUrl(NetApi.NEW_BASE_URL)
                .builder(NewMusicApi.class)
                .getDiscoverExclusivePart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
