package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayingLrcBean;
import com.example.jackypeng.swangyimusic.rx.contract.MusicUrlContract;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/4/17.
 * 拿到播放音乐和电台的信息   如url和lrc
 */

public class PlayingMusicInfoPresenter extends MusicUrlContract.Presenter {

    @Override
    public void getPlayingMusicUrl(String id) {
        mModel.getPlayingMusicUrl(id).subscribe(new Subscriber<PlayUrlBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getPlayingMusicUrlError(e.getMessage());
            }

            @Override
            public void onNext(PlayUrlBean playUrlBean) {
                mView.getPlayingMusicUrlView(playUrlBean);
            }
        });

    }

    @Override
    public void getPlayingMusicLrc(String id) {
        mModel.getPlayingMusicLrc(id).subscribe(new Subscriber<PlayingLrcBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getPlayingMusicLrcError(e.getMessage());
            }

            @Override
            public void onNext(PlayingLrcBean playingLrcBean) {
                mView.getPlayingMusicLrcView(playingLrcBean);
            }
        });
    }
}
