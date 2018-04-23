package com.example.jackypeng.swangyimusic.rx.contract;

import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayingLrcBean;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import rx.Observable;

/**
 * Created by jackypeng on 2018/4/19.
 */

public interface MusicUrlContract {
    public interface View extends BaseView {
        void getPlayingMusicUrlView(PlayUrlBean resultBean);
        void getPlayingMusicLrcView(PlayingLrcBean resultBean);

        void getPlayingMusicUrlError(String msg);
        void getPlayingMusicLrcError(String msg);
    }

    public interface Model extends BaseModel {
        Observable<PlayUrlBean> getPlayingMusicUrl(String id);
        Observable<PlayingLrcBean> getPlayingMusicLrc(String id);
    }

    public abstract class Presenter extends BasePresenter<MusicUrlContract.View, MusicUrlContract.Model> {
        //        拿到music的url
        public abstract void getPlayingMusicUrl(String id);
        public abstract void getPlayingMusicLrc(String id);

    }
}
