package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.model.RankingListModel;
import com.example.jackypeng.swangyimusic.rx.presenter.RankingListPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.RankingListView;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class RankingListFragment extends BaseFragment<RankingListModel,RankingListPresenter> implements RankingListView {
    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void doFetchFreshMusic(FreshMusicBean result) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {

    }

    @Override
    protected RankingListModel getModel() {
        return null;
    }

    @Override
    protected RankingListPresenter getPresenter() {
        return new RankingListPresenter();
    }
}
