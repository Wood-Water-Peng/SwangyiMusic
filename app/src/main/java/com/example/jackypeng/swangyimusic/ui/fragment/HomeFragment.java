package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentHomeAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.RecentPlayBean;
import com.example.jackypeng.swangyimusic.rx.db.RecentPlayManager;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.util.MusicUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/16.
 */

public class HomeFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.fragment_home_recycler_view)
    RecyclerView recyclerView;
    private List<LocalMusicDetailInfo> localMusicDetailInfoList;
    private FragmentHomeAdapter homeAdapter;
    private List<RecentPlayBean> recentPlayList;


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
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        homeAdapter = new FragmentHomeAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.gray_line_divider));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(homeAdapter);
        //异步的从本地数据库中拿到相关数据
        initData();
    }

    private void initData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                localMusicDetailInfoList = MusicUtil.getAllMusics();
                recentPlayList = RecentPlayManager.getInstance().getRecentPlayList();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                homeAdapter.setData(localMusicDetailInfoList, recentPlayList);
            }
        }.execute();
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
