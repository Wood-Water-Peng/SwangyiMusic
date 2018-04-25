package com.example.jackypeng.swangyimusic.rx.contract;

import com.example.jackypeng.swangyimusic.rx.bean.user.UserPlayListBean;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import rx.Observable;

/**
 * Created by jackypeng on 2018/4/19.
 * 用户歌单
 */

public interface UserPlayListContract {
    public interface View extends BaseView {
        void getUserPlayListView(UserPlayListBean userPlayListBean);
    }

    public interface Model extends BaseModel {
        Observable<UserPlayListBean> getUserPlayList(String uid);
    }

    public abstract class Presenter extends BasePresenter<UserPlayListContract.View, UserPlayListContract.Model> {
        //        拿到music的url
        public abstract void getUserPlayList(String uid);
    }
}
