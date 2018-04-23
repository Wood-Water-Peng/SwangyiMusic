package com.example.jackypeng.swangyimusic.rx.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.model.FreshMusicModel;
import com.example.jackypeng.swangyimusic.rx.view.rxView.FreshMusicView;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class FreshMusicPresenter extends BasePresenter<FreshMusicView, FreshMusicModel> {
    private static final String TAG = "FreshMusicPresenter";

    public void getFreshMusic() {
        mModel.getFreshMusic().subscribe(new Subscriber<FreshMusicBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onNext(FreshMusicBean freshMusicBean) {
                mView.doFetchFreshMusic(freshMusicBean);
            }
        });
    }

    public void getCarouselDetail(int num) {

        mModel.getCarouselDetail(String.valueOf(num)).subscribe(new Subscriber<JSONObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onNext(JSONObject carouselDetail) {
                mView.doFetchCarouselDetail(carouselDetail);
            }
        });
    }
}
