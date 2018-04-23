package com.example.jackypeng.swangyimusic.rx.contract;

import android.content.Context;

import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

import rx.Observable;

/**
 * Created by jackypeng on 2018/4/17.
 * 电台节目
 */

public interface RadioProgramContract {
    public interface View extends BaseView {
        //        轮播图
        void getRadioProgramListView(RadioProgramResult resultBean);
    }

    public interface Model extends BaseModel {
        Observable<RadioProgramResult> getRadioProgramList(String rid);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        //        轮播
        public abstract void getRadioProgramList(String rid);

    }
}
