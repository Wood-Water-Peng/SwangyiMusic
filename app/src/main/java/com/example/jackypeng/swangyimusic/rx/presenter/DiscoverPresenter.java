package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.contract.DiscoverContract;
import com.example.jackypeng.swangyimusic.util.L;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class DiscoverPresenter extends DiscoverContract.Presenter {
    @Override
    public void getBanner() {
        mModel.getBanner(null).subscribe(new Subscriber<BannerResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               mView.getBannerError(e.getMessage());
            }

            @Override
            public void onNext(BannerResultBean resultBean) {
                    mView.getBanner(resultBean);
            }
        });
    }

    @Override
    public void getRecommendList(int limit) {
        mModel.getRecommendList(null,limit).subscribe(new Subscriber<RecommendListResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getRecommendListError(e.getMessage());
            }

            @Override
            public void onNext(RecommendListResult resultBean) {
                mView.getRecommendList(resultBean);
            }
        });
    }

    @Override
    public void getRecommendRadio() {
        mModel.getRecommendRadio(null).subscribe(new Subscriber<RecommendRadioResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getRecommendRadioError(e.getMessage());
            }

            @Override
            public void onNext(RecommendRadioResult resultBean) {
                mView.getRecommendRadio(resultBean);
            }
        });
    }

    @Override
    public void getRecommendMV() {
        mModel.getRecommendMV(null).subscribe(new Subscriber<RecommendMVResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getRecommendMVError(e.getMessage());
            }

            @Override
            public void onNext(RecommendMVResult resultBean) {
                mView.getRecommendMV(resultBean);
            }
        });
    }

    @Override
    public void getExclusivePart() {
        mModel.getExclusivePart(null).subscribe(new Subscriber<ExclusivePartResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getExclusivePartError(e.getMessage());
            }

            @Override
            public void onNext(ExclusivePartResult resultBean) {
                mView.getExclusivePart(resultBean);
            }
        });
    }
}
