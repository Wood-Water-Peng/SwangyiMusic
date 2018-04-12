package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.AlbumDetailBean;
import com.example.jackypeng.swangyimusic.rx.model.AlbumDetailModel;
import com.example.jackypeng.swangyimusic.rx.view.rxView.AlbumDetailView;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class AlbumDetailPresenter extends BasePresenter<AlbumDetailView, AlbumDetailModel> {
    private static final String TAG = "AlbumDetailPresenter";

    public void getAlbumDetail(String albumId) {
        mModel.getAlbumDetail(albumId).subscribe(new Subscriber<AlbumDetailBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onNext(AlbumDetailBean freshMusicBean) {
                mView.doFetchAlbumDetail(freshMusicBean);
            }
        });
    }

    
}
