package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.AlbumDetailBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResponseBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioDetailResult;
import com.example.jackypeng.swangyimusic.rx.contract.RadioDetailContract;
import com.example.jackypeng.swangyimusic.rx.model.AlbumDetailModel;
import com.example.jackypeng.swangyimusic.rx.model.RadioDetailModel;
import com.example.jackypeng.swangyimusic.rx.view.rxView.AlbumDetailView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.RadioDetailView;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class RadioDetailPresenter extends RadioDetailContract.Presenter {
    private static final String TAG = "RadioDetailPresenter";

    public void getRadioDetail(String rid) {
        mModel.getRadioDetail(rid).subscribe(new Subscriber<RadioDetailResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showErrorWithStatus(e.getMessage());
            }

            @Override
            public void onNext(RadioDetailResult radioDetailResultBean) {
                mView.getRadioDetailView(radioDetailResultBean);
            }
        });
    }

    
}
