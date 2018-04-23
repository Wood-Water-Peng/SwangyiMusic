package com.example.jackypeng.swangyimusic.rx.contract;

import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import rx.Observable;

/**
 * Created by jackypeng on 2018/4/17.
 * 歌单详情
 */

public interface PlayingListDetailContract {
    public interface View extends BaseView {
        //        轮播图
        void getPlayingListDetailView(PlayingListDetailResult resultBean);
    }

    public interface Model extends BaseModel {
        Observable<PlayingListDetailResult> getPlayingListDetail(String rid);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        //        轮播
        public abstract void getPlayingListDetail(String rid);

    }
}
