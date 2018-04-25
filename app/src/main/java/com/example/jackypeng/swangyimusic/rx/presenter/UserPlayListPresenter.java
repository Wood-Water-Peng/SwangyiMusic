package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.user.UserPlayListBean;
import com.example.jackypeng.swangyimusic.rx.contract.UserPlayListContract;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/4/25.
 */

public class UserPlayListPresenter extends UserPlayListContract.Presenter {

    @Override
    public void getUserPlayList(String uid) {
        mModel.getUserPlayList(uid).subscribe(new Subscriber<UserPlayListBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onNext(UserPlayListBean userPlayListBean) {
                mView.getUserPlayListView(userPlayListBean);
            }
        });
    }
}
