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
import com.example.jackypeng.swangyimusic.constants.SpConstants;
import com.example.jackypeng.swangyimusic.rx.bean.PlayListMusicMoreFragmentBean;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.contract.PlayingListDetailContract;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.PlayingListDetailModel;
import com.example.jackypeng.swangyimusic.rx.presenter.PlayingListDetailPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.fragment.DownloadMusicPlayListFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.PlayListMusicMoreFragment;
import com.example.jackypeng.swangyimusic.util.DBUtil;
import com.example.jackypeng.swangyimusic.util.FileUtil;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;

import java.io.File;

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
    private PlayingSongStatusReceiver playing_song_status_receiver = new PlayingSongStatusReceiver();
    private DownloadMusicStatusReceiver download_music_receiver = new DownloadMusicStatusReceiver();
    private DialogFragmentControllerReceiver dialog_fragment_controll_receiver = new DialogFragmentControllerReceiver();
    private PlayListMusicMoreFragment playListMusicMoreFragment;
    private DownloadMusicPlayListFragment downloadMusicPlayListFragment;

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
        initBroadcast();

        /**
         *开启子线程去查询下载音乐的文件夹有没有被改变
         * 1.获取下载音乐的文件的hashCode
         *
         */
        File download_music_dir = new File(FileUtil.getDownloadMusicDir().getAbsolutePath());
        if (FileUtil.hasFileDeleted(download_music_dir)) {  //说明文件夹中有音乐被删除
            SharePreferenceUtil.writeString(SpConstants.APP_INFO, SpConstants.DOWNLOAD_MUSIC_DIR_LAST_MODIFIED_TIME, String.valueOf(download_music_dir.lastModified()));
            DBUtil.updateDBRecords(download_music_dir);
        }

    }

    private void initBroadcast() {
        IntentFilter playing_song_status_filter = new IntentFilter();
        playing_song_status_filter.addAction(BroadcastConstants.START_PLAY_SONG);
        registerReceiver(playing_song_status_receiver, playing_song_status_filter);

        IntentFilter filter_download_music = new IntentFilter();
        filter_download_music.addAction(BroadcastConstants.DOWNLOAD_MUSIC);
        registerReceiver(download_music_receiver, filter_download_music);

        IntentFilter filter_dialog_fragment_controll = new IntentFilter();
        filter_dialog_fragment_controll.addAction(BroadcastConstants.SHOW_MUSIC_MORE_DOWNLOAD_TO_PLAYLIST_FRAGMENT);
        filter_dialog_fragment_controll.addAction(BroadcastConstants.SHOW_MUSIC_MORE_FRAGMENT);
        filter_dialog_fragment_controll.addAction(BroadcastConstants.HIDE_MUSIC_MORE_DOWNLOAD_TO_PLAYLIST_FRAGMENT);
        filter_dialog_fragment_controll.addAction(BroadcastConstants.HIDE_MUSIC_MORE_FRAGMENT);
        registerReceiver(dialog_fragment_controll_receiver, filter_dialog_fragment_controll);


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

    //控制DialogFragment
    class DialogFragmentControllerReceiver extends BroadcastReceiver {
        PlayListMusicMoreFragmentBean songDetail;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BroadcastConstants.SHOW_MUSIC_MORE_DOWNLOAD_TO_PLAYLIST_FRAGMENT:
                    songDetail = intent.getExtras().getParcelable("songDetail");
                    downloadMusicPlayListFragment = DownloadMusicPlayListFragment.newInstance(songDetail);
                    downloadMusicPlayListFragment.show(PlayingListDetailActivity.this.getSupportFragmentManager(), "DownloadMusicPlayListFragment");
                    break;
                case BroadcastConstants.SHOW_MUSIC_MORE_FRAGMENT:
                    songDetail = intent.getExtras().getParcelable("songDetail");
                    playListMusicMoreFragment = PlayListMusicMoreFragment.newInstance(songDetail);
                    playListMusicMoreFragment.show(PlayingListDetailActivity.this.getSupportFragmentManager(), "playListMusicMoreFragment");
                    break;
                case BroadcastConstants.HIDE_MUSIC_MORE_DOWNLOAD_TO_PLAYLIST_FRAGMENT:
                    if (downloadMusicPlayListFragment != null) {
                        downloadMusicPlayListFragment.dismiss();
                    }
                    break;
                case BroadcastConstants.HIDE_MUSIC_MORE_FRAGMENT:
                    if (playListMusicMoreFragment != null) {
                        playListMusicMoreFragment.dismiss();
                    }
                    break;
            }
        }
    }

    //播放音乐的状态
    class PlayingSongStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BroadcastConstants.START_PLAY_SONG)) {   //歌曲开始播放
                adapter.notifyDataSetChanged();
            }
        }
    }

    //下载音乐的状态
    class DownloadMusicStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (action.equals(BroadcastConstants.DOWNLOAD_MUSIC)) {   //歌曲开始播放
                if (extras != null) {
                    String musicId = extras.getString("musicId");
                    Log.i(TAG, "onReceive---musicId:" + musicId);
                    int status = extras.getInt("status");
                    adapter.updateDownloadStatus(musicId, status);
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(playing_song_status_receiver);
        unregisterReceiver(download_music_receiver);
    }
}
