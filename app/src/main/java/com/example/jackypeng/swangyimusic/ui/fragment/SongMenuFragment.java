package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.model.SongMenuModel;
import com.example.jackypeng.swangyimusic.rx.presenter.SongMenuPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.SongMenuView;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class SongMenuFragment extends BaseFragment<SongMenuModel, SongMenuPresenter> implements SongMenuView {
    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void doFetchFreshMusic(FreshMusicBean result) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_songmenu;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

    }

    @Override
    protected SongMenuModel getModel() {
        return null;
    }

    @Override
    protected SongMenuPresenter getPresenter() {
        return new SongMenuPresenter();
    }
}
