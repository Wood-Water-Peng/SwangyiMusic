package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected V mView;
    protected M mModel;

    public M getModel() {
        return mModel;
    }

    public V getView() {
        return mView;
    }

    public void attachView(V v) {
        this.mView = v;
    }

    public void attachModel(M m) {
        this.mModel = m;
    }
}
