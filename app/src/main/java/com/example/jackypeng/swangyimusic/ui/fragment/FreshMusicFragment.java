package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FreshMusicAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicResultBean;
import com.example.jackypeng.swangyimusic.rx.model.FreshMusicModel;
import com.example.jackypeng.swangyimusic.rx.presenter.FreshMusicPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.FreshMusicView;
import com.example.jackypeng.swangyimusic.ui.widget.SmartLoadingLayout;
import com.example.jackypeng.swangyimusic.util.L;
import com.example.jackypeng.swangyimusic.util.UIUtil;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class FreshMusicFragment extends BaseFragment<FreshMusicModel, FreshMusicPresenter> implements FreshMusicView {

    @BindView(R.id.fresh_music_smart_loading_layout)
    SmartLoadingLayout smartLoadingLayout;
    @BindView(R.id.fresh_music_recycle_view)
    RecyclerView recyclerView;
    private FreshMusicAdapter freshMusicAdapter;
    private static final String TAG = "FreshMusicFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_freshmuisc;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "---onCreate---");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "---onViewCreated---:" + getUserVisibleHint());
    }

    @Override
    protected void lazyFetchData() {
        Log.i(TAG, "---lazyFetchData---");
        smartLoadingLayout.onLoading();
        mPresenter.getFreshMusic();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "---onCreateView---");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "---setUserVisibleHint:" + isVisibleToUser);
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        smartLoadingLayout.onEmpty();
        freshMusicAdapter = new FreshMusicAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(freshMusicAdapter);
        smartLoadingLayout.setOnReloadClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartLoadingLayout.onLoading();
                mPresenter.getFreshMusic();
            }
        });
    }

    @Override
    protected FreshMusicModel getModel() {
        return new FreshMusicModel();
    }

    @Override
    protected FreshMusicPresenter getPresenter() {
        return new FreshMusicPresenter();
    }

    @Override
    public void showErrorWithStatus(String msg) {
        L.i("showErrorWithStatus:" + msg);
        smartLoadingLayout.onError();
    }

    @Override
    public void doFetchFreshMusic(final FreshMusicBean result) {
        final FreshMusicResultBean freshMusicResult = result.getResult();
        freshMusicAdapter.setData(freshMusicResult);
        smartLoadingLayout.onSuccess();
    }
}
