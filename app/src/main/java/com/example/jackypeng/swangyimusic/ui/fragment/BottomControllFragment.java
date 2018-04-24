package com.example.jackypeng.swangyimusic.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.MediaAidlInterface;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MediaService;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;
import com.example.jackypeng.swangyimusic.ui.activity.PlayDetailActivity;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class BottomControllFragment extends Fragment {

    private static final String TAG = "BottomControllFragment";
    private Unbinder unbinder;
    @BindView(R.id.fragment_bottom_controller_content)
    View contentView;
    @BindView(R.id.fragment_bottom_controller_icon)
    SimpleDraweeView icon;
    @BindView(R.id.fragment_bottom_controller_song_name)
    TextView tv_song_name;
    @BindView(R.id.fragment_bottom_controller_author_name)
    TextView tv_author_name;
    @BindView(R.id.fragment_bottom_controller_play_pause)
    ImageView iv_play_pause;  //播放、暂停
    @BindView(R.id.fragment_bottom_controller_song_list)
    ImageView iv_song_list;  //歌曲列表
    @BindView(R.id.fragment_bottom_controller_next)
    ImageView iv_play_next;  //下一首
    @BindView(R.id.fragment_bottom_controller_progress_bar)
    ProgressBar progressBar;  //进度条
    private MediaAidlInterface service;

    @OnClick(R.id.fragment_bottom_controller_play_pause)
    public void play_pause_music() {
        Log.i(TAG, "status:" + curStatus);
        if (curStatus == PlayingMusicStatusConstants.PLAYING) {
            pauseCurMusic();
        } else if (curStatus == PlayingMusicStatusConstants.INIT) {
            startCurMusic();
        } else if (curStatus == PlayingMusicStatusConstants.PAUSING) {
            resumeCurMusic();
        }
    }

    @OnClick(R.id.fragment_bottom_controller_content)
    public void showPlayDetailActivity() {
        if (playing_song_track == null) {
            ToastUtil.getInstance().toast("您还没有播放任何歌曲");
            return;
        }
        Intent intent = new Intent(getContext(), PlayDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.fragment_bottom_controller_song_list)
    public void showPlayingQueue() {
        try {
            playing_song_track = MusicPlayer.getInstance().getPlayingSongTrack();
            if (playing_song_track == null) {
                ToastUtil.getInstance().toast("您还没有播放任何歌曲!");
            } else {
                PlayingQueueFragment queueFragment = new PlayingQueueFragment();
                queueFragment.setCancelable(true);
                //设置fragment高度 、宽度
                queueFragment.show(getChildFragmentManager(), "playing_queue_fragment");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void resumeCurMusic() {
        try {
            MusicPlayer.getInstance().resumeSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //MediaService没有加载播放资源
    private void startCurMusic() {
        try {
            MusicPlayer.getInstance().playInQueue(playing_song_track.getIndex());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void pauseCurMusic() {
        try {
            MusicPlayer.getInstance().pauseSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private AlbumListItemTrack playing_song_track;
    private FloatingViewReceiver floatingViewReceiver;
    private int curStatus = PlayingMusicStatusConstants.INIT;  //音乐的播放状态

    class FloatingViewReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle bundle = intent.getExtras();
            if (action.equals(BroadcastConstants.START_PLAY_SONG)) {
                if (bundle != null) {
                    playing_song_track = bundle.getParcelable("cur_song_track");
                    updateStartTrack();
                }
            } else if (action.equals(BroadcastConstants.UPDATE_PLAYING_PROGRESS)) {
                if (bundle != null) {
                    playing_song_track = bundle.getParcelable("cur_song_track");
                    updateProgressTrack();
                }
            } else if (action.equals(BroadcastConstants.PAUSE_PLAY_SONG)) {
                if (bundle != null) {
                    playing_song_track = bundle.getParcelable("cur_song_track");
                    updatePausedTrack();
                }
            } else if (action.equals(BroadcastConstants.RESUME_PLAY_SONG)) {
                if (bundle != null) {
                    playing_song_track = bundle.getParcelable("cur_song_track");
                    updateResumeTrack();
                }
            } else if (action.equals(BroadcastConstants.ERROR_PLAYSONG)) {
                ToastUtil.getInstance().toast("播放出错");
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReceiver();
    }

    private void initReceiver() {
        floatingViewReceiver = new FloatingViewReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConstants.START_PLAY_SONG);
        filter.addAction(BroadcastConstants.UPDATE_PLAYING_PROGRESS);
        filter.addAction(BroadcastConstants.PAUSE_PLAY_SONG);
        filter.addAction(BroadcastConstants.RESUME_PLAY_SONG);
        filter.addAction(BroadcastConstants.ERROR_PLAYSONG);
        getContext().registerReceiver(floatingViewReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_controller, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        //通过服务获取到最新的播放信息
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                service = MusicPlayer.getInstance().getMediaService();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    playing_song_track = service.getPlayingSongInfo();
                    initTrack();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        return rootView;
    }

    private void initTrack() {
        if (playing_song_track == null) return;
        curStatus = playing_song_track.getStatus();
        Log.i(TAG, "initTrack---status:" + curStatus);
        if (curStatus == PlayingMusicStatusConstants.INIT) {
            iv_play_pause.setBackgroundResource(R.mipmap.playbar_btn_play);
        } else if (curStatus == PlayingMusicStatusConstants.PLAYING) {
            iv_play_pause.setBackgroundResource(R.mipmap.playbar_btn_pause);
        } else if (curStatus == PlayingMusicStatusConstants.PAUSING) {
            iv_play_pause.setBackgroundResource(R.mipmap.playbar_btn_play);
        }
        tv_author_name.setText(playing_song_track.getAuthor());
        tv_song_name.setText(playing_song_track.getSongName());
        icon.setImageURI(playing_song_track.getPic_small_url());

        int cur_duration = playing_song_track.getCur_duration();
        int total_duration = playing_song_track.getTotal_duration();
        if (total_duration == 0) return;
        int progress = cur_duration * 100 / total_duration;
        progressBar.setProgress(progress);
    }

    private void updatePausedTrack() {
        Log.i(TAG, "updatePausedTrack---status:" + curStatus);
        curStatus = PlayingMusicStatusConstants.PAUSING;
        tv_author_name.setText(playing_song_track.getAuthor());
        tv_song_name.setText(playing_song_track.getSongName());
        icon.setImageURI(playing_song_track.getPic_small_url());
        iv_play_pause.setBackgroundResource(R.mipmap.playbar_btn_play);
    }


    private void updateStartTrack() {
        Log.i(TAG, "updateStartTrack---status:" + curStatus);
        curStatus = PlayingMusicStatusConstants.PLAYING;
        tv_author_name.setText(playing_song_track.getAuthor());
        tv_song_name.setText(playing_song_track.getSongName());
        icon.setImageURI(playing_song_track.getPic_small_url());
        iv_play_pause.setBackgroundResource(R.mipmap.playbar_btn_pause);
    }

    private void updateResumeTrack() {
        Log.i(TAG, "updateResumeTrack---status:" + curStatus);
        curStatus = PlayingMusicStatusConstants.PLAYING;
        iv_play_pause.setBackgroundResource(R.mipmap.playbar_btn_pause);
    }

    private void updateProgressTrack() {
        int cur_duration = playing_song_track.getCur_duration();
        int total_duration = playing_song_track.getTotal_duration();
        if (total_duration == 0) return;
        int progress = cur_duration * 100 / total_duration;
        progressBar.setProgress(progress);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(floatingViewReceiver);
    }
}
