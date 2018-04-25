package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.LoginResultBean;
import com.example.jackypeng.swangyimusic.rx.contract.LoginContract;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/4/25.
 */

public class LoginPresenter extends LoginContract.Presenter {
    @Override
    public void login(String account, String psw) {
        mModel.login(account, psw).subscribe(new Subscriber<LoginResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onNext(LoginResultBean loginResultBean) {
                mView.getLoginResultView(loginResultBean);
            }
        });
    }
}
