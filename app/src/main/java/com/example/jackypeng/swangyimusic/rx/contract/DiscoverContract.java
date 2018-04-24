package com.example.jackypeng.swangyimusic.rx.contract;

import android.content.Context;

import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import rx.Observable;

/**
 * Created by jackypeng on 2018/4/17.
 */

public interface DiscoverContract {
    public interface View extends BaseView {
        //        轮播图
        void getBanner(BannerResultBean resultBean);

        //        推荐歌单
        void getRecommendList(RecommendListResult resultBean);

        //        推荐电台
        void getRecommendRadio(RecommendRadioResult resultBean);

        //        推荐MV
        void getRecommendMV(RecommendMVResult resultBean);

        //        独家放送
        void getExclusivePart(ExclusivePartResult resultBean);



        //        轮播图
        void getBannerError(String msg);

        //        推荐歌单
        void getRecommendListError(String msg);

        //        推荐电台
        void getRecommendRadioError(String msg);

        //        推荐MV
        void getRecommendMVError(String msg);

        //        独家放送
        void getExclusivePartError(String msg);

    }

    public interface Model extends BaseModel {
        Observable<BannerResultBean> getBanner(Context context);

        Observable<RecommendListResult> getRecommendList(Context context, int limit);

        Observable<RecommendRadioResult> getRecommendRadio(Context context);

        Observable<RecommendMVResult> getRecommendMV(Context context);

        Observable<ExclusivePartResult> getExclusivePart(Context context);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        //        轮播
        public abstract void getBanner();

        //        推荐歌单
        public abstract void getRecommendList(int limit);

        //        推荐电台
        public abstract void getRecommendRadio();

        //        推荐MV
        public abstract void getRecommendMV();

        //独家放送
        public abstract void getExclusivePart();
    }
}
