package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.contract.DiscoverContract;
import com.example.jackypeng.swangyimusic.rx.contract.RadioProgramContract;
import com.example.jackypeng.swangyimusic.util.L;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class RadioProgramPresenter extends RadioProgramContract.Presenter {

    @Override
    public void getRadioProgramList(String rid) {
        mModel.getRadioProgramList(rid).subscribe(new Subscriber<RadioProgramResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RadioProgramResult radioProgramResult) {
                mView.getRadioProgramListView(radioProgramResult);
            }
        });
    }
}
