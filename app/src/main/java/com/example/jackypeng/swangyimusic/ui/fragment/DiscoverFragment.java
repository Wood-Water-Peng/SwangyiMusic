package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentDiscoverAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.contract.DiscoverContract;
import com.example.jackypeng.swangyimusic.rx.model.DiscoverModel;
import com.example.jackypeng.swangyimusic.rx.presenter.DiscoverPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.widget.SmartLoadingLayout;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class DiscoverFragment extends BaseFragment<DiscoverContract.Model, DiscoverContract.Presenter> implements DiscoverContract.View {

    @BindView(R.id.fragment_discover_music_recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_discover_music_smart_loading_layout)
    SmartLoadingLayout smartLoadingLayout;
    private FragmentDiscoverAdapter discoverAdapter;

    @Override
    public void showErrorWithStatus(String msg) {
        smartLoadingLayout.onError();
    }

    @Override
    public void getBanner(BannerResultBean resultBean) {
        discoverAdapter.setBanner(resultBean);
        smartLoadingLayout.onSuccess();
    }

    @Override
    public void getRecommendList(RecommendListResult resultBean) {
        discoverAdapter.setRecommendList(resultBean);
        smartLoadingLayout.onSuccess();
    }

    @Override
    public void getRecommendRadio(RecommendRadioResult resultBean) {
        discoverAdapter.setRecommendRadio(resultBean);
    }

    @Override
    public void getRecommendMV(RecommendMVResult resultBean) {
//        discoverAdapter.setRecommendMV(resultBean);
    }

    @Override
    public void getExclusivePart(ExclusivePartResult resultBean) {
        discoverAdapter.setExclusivePart(resultBean);
        smartLoadingLayout.onSuccess();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        discoverAdapter = new FragmentDiscoverAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(discoverAdapter);
        smartLoadingLayout.setOnReloadClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    @Override
    public void lazyFetchData() {

    }

    public void fetchData() {
        smartLoadingLayout.onLoading();
        mPresenter.getBanner();
        mPresenter.getRecommendList(6);
//        mPresenter.getRecommendMV();
        mPresenter.getRecommendRadio();
        mPresenter.getExclusivePart();
    }

    @Override
    protected DiscoverContract.Model getModel() {
        return new DiscoverModel();
    }

    @Override
    protected DiscoverContract.Presenter getPresenter() {
        return new DiscoverPresenter();
    }
}
