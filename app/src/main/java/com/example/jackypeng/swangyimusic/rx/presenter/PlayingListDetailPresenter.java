package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.contract.PlayingListDetailContract;
import com.example.jackypeng.swangyimusic.rx.contract.RadioProgramContract;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class PlayingListDetailPresenter extends PlayingListDetailContract.Presenter {

    @Override
    public void getPlayingListDetail(String rid) {
        mModel.getPlayingListDetail(rid).subscribe(new Subscriber<PlayingListDetailResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PlayingListDetailResult playingListDetailResult) {
                mView.getPlayingListDetailView(playingListDetailResult);
            }
        });
    }
}
