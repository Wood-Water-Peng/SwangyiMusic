package com.example.jackypeng.swangyimusic.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.PlayingListDetailAdapter;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.contract.PlayingListDetailContract;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.PlayingListDetailModel;
import com.example.jackypeng.swangyimusic.rx.presenter.PlayingListDetailPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.util.L;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jackypeng on 2018/4/20.
 * 歌单详情
 */

public class PlayingListDetailActivity extends BaseActivity<PlayingListDetailContract.Model, PlayingListDetailContract.Presenter> implements PlayingListDetailContract.View {

    private static final String TAG = "PlayingListDetailActivity";

    @BindView(R.id.activity_playing_list_detail_song_list)
    RecyclerView recyclerView;
    @BindView(R.id.activity_playing_list_detail_title)
    TextView tv_title;
    @BindView(R.id.activity_playing_list_detail_back)
    ImageView iv_back;
    private PlayingListDetailAdapter adapter;
    private PlayingSongStatusReceiver receiver=new PlayingSongStatusReceiver();

    @OnClick(R.id.activity_playing_list_detail_back)
    public void onBackClicked() {
        finish();
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            mPresenter.getPlayingListDetail(id);
        }
        adapter = new PlayingListDetailAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConstants.START_PLAY_SONG);
        registerReceiver(receiver, filter);
    }

    @Override
    protected boolean hasBottomController() {
        return true;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected BaseModel getModel() {
        return new PlayingListDetailModel();
    }

    @Override
    protected PlayingListDetailContract.Presenter getPresenter() {
        return new PlayingListDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_playing_list_detail;
    }

    @Override
    protected int getStatusBarStatus() {
        return TRANS_STATUS_BAR;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getPlayingListDetailView(PlayingListDetailResult resultBean) {
        tv_title.setText(resultBean.getResult().getName());
        adapter.setData(resultBean);
    }

    class PlayingSongStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "intent:" + intent);
            String action = intent.getAction();
            if (action.equals(BroadcastConstants.START_PLAY_SONG)) {   //歌曲开始播放
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
