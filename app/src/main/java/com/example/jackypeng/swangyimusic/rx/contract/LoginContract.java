package com.example.jackypeng.swangyimusic.rx.contract;

import com.example.jackypeng.swangyimusic.rx.bean.LoginResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayingLrcBean;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import rx.Observable;

/**
 * Created by jackypeng on 2018/4/19.
 */

public interface LoginContract {
    public interface View extends BaseView {
        void getLoginResultView(LoginResultBean resultBean);

    }

    public interface Model extends BaseModel {
        Observable<LoginResultBean> login(String account, String psw);

    }

    public abstract class Presenter extends BasePresenter<LoginContract.View, LoginContract.Model> {
        //        拿到music的url
        public abstract void login(String account, String psw);


    }
}
