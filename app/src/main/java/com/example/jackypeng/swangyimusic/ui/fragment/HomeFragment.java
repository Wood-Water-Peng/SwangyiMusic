package com.example.jackypeng.swangyimusic.ui.fragment;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentHomeAdapter;
import com.example.jackypeng.swangyimusic.broadcast.NetworkStateReceiver;
import com.example.jackypeng.swangyimusic.constants.NetworkMsgConstants;
import com.example.jackypeng.swangyimusic.constants.UserInfoConstants;
import com.example.jackypeng.swangyimusic.eventBus.NetworkChangedEvent;
import com.example.jackypeng.swangyimusic.network.NetworkStatusInfo;
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
import com.example.jackypeng.swangyimusic.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private List<UserPlayListBean.UserPlayListItem> userPlayListItems;


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
        initBroadcast();
        initUserPlayList();
        EventBus.getDefault().register(this);
    }

    private void initBroadcast() {
        //监听网络变化
        IntentFilter filter2 = new IntentFilter();
        filter2.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter2.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        filter2.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        getContext().registerReceiver(networkStateReceiver, filter2);
    }

    private void initUserPlayList() {
        boolean isUserLogin = SharePreferenceUtil.readBoolean(UserInfoConstants.USER_INFO, UserInfoConstants.IS_LOGINED);
        String userId = SharePreferenceUtil.readString(UserInfoConstants.USER_INFO, UserInfoConstants.USER_ID);

        if (isUserLogin) {
            //判断网络状态
            int network_status = NetworkStatusInfo.getInstance().getStatus();
            if (network_status == NetworkMsgConstants.NO_WIFI_NO_MOBILE) {
                ToastUtil.getInstance().toast("网络未连接");
            } else {
                mPresenter.getUserPlayList(userId);
            }
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
            this.userPlayListItems = userPlayListBean.getPlaylist();
            homeAdapter.setUserPlaylist(userPlayListItems);
        }
    }

    private NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangedEvent(NetworkChangedEvent event) {
        //根据网络状态，刷新界面
        refreshView(event.getMsg());
    }

    //刷新界面
    private void refreshView(int msg) {
        switch (msg) {
            case NetworkMsgConstants.NO_WIFI_NO_MOBILE:
                //将用户创建歌单的颜色变灰
                if (userPlayListItems != null && userPlayListItems.size() > 0) {
                    homeAdapter.updateUserPlayList(NetworkMsgConstants.NETWORK_DISCONNECT);
                }
                break;
            case NetworkMsgConstants.WIFI_MOBILE:
            case NetworkMsgConstants.NO_WIFI_MOBILE:
            case NetworkMsgConstants.WIFI_NO_MOBILE:
                //将用户创建歌单的颜色变白
                if (userPlayListItems != null && userPlayListItems.size() > 0) {
                    homeAdapter.updateUserPlayList(NetworkMsgConstants.NETWORK_AVAILABLE);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(networkStateReceiver);
        EventBus.getDefault().unregister(this);
    }
}
