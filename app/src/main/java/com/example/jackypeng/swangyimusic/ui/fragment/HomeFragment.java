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
import com.example.jackypeng.swangyimusic.constants.UserInfoConstants;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.RecentPlayBean;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserPlayListBean;
import com.example.jackypeng.swangyimusic.rx.contract.UserPlayListContract;
import com.example.jackypeng.swangyimusic.rx.db.RecentPlayManager;
import com.example.jackypeng.swangyimusic.rx.model.UserPlayListModel;
import com.example.jackypeng.swangyimusic.rx.presenter.UserPlayListPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.util.MusicUtil;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/16.
 */

public class HomeFragment extends BaseFragment<UserPlayListContract.Model, UserPlayListContract.Presenter> implements UserPlayListContract.View {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.fragment_home_recycler_view)
    RecyclerView recyclerView;
    private List<LocalMusicDetailInfo> localMusicDetailInfoList;
    private FragmentHomeAdapter homeAdapter;
    private List<RecentPlayBean> recentPlayList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
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

        initUserPlayList();
    }

    private void initUserPlayList() {
        boolean isUserLogin = SharePreferenceUtil.readBoolean(UserInfoConstants.USER_INFO, UserInfoConstants.IS_LOGINED);
        String userId = SharePreferenceUtil.readString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_ID);

        if (isUserLogin) {
            mPresenter.getUserPlayList(userId);
        }
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
    protected UserPlayListModel getModel() {
        return new UserPlayListModel();
    }

    @Override
    protected UserPlayListPresenter getPresenter() {
        return new UserPlayListPresenter();
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getUserPlayListView(UserPlayListBean userPlayListBean) {
        if (userPlayListBean.getCode() == 200) {
            homeAdapter.setUserPlaylist(userPlayListBean.getPlaylist());
        }
    }
}
