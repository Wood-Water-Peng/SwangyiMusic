package com.example.jackypeng.swangyimusic.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.lrc.DefaultLrcParser;
import com.example.jackypeng.swangyimusic.lrc.LrcRow;
import com.example.jackypeng.swangyimusic.lrc.LrcView;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.widget.PlayerSeekBar;
import com.example.jackypeng.swangyimusic.rx.view.widget.PlayingDetailAlbumPager;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;
import com.example.jackypeng.swangyimusic.ui.fragment.PlayingQueueFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.RoundFragment;
import com.example.jackypeng.swangyimusic.util.MusicUtil;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.example.jackypeng.swangyimusic.util.UIUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class PlayDetailActivity extends BaseActivity {

    /**
     * 绑定MediaService
     * <p>
     * 播放的数据从播放音乐的服务中拿
     * 包括封面，歌词，该专辑的音乐列表
     * <p>
     * 用户操作界面，进而调用服务的方法
     * <p>
     * 当用户左右滑动时，调用服务的方法，当服务加载出对应歌曲信息时，发送广播，在该对象内接受该广播
     * 数据包括歌词链接，封面图链接，歌曲播放进度等信息
     * <p>
     * <p>
     * 播放界面的改变应该根据MediaService的反馈完成
     * 比如，用户点击了播放，界面不应该立即变成暂停，而应该等待MediaService确定音乐开始播放后才改变
     */

    private static final String TAG = "PlayDetailActivity";
    @BindView(R.id.activity_play_detail_playing_vp)
    PlayingDetailAlbumPager viewPager;
    @BindView(R.id.activity_play_detail_playing_container)
    ViewGroup playing_container;   //播放界面组
    @BindView(R.id.activity_play_detail_lrc_container)
    ViewGroup lrc_container;    //歌词界面组
    @BindView(R.id.activity_play_detail_lrc_view)
    LrcView lrcView;
    @BindView(R.id.activity_play_detail_needle)
    ImageView needle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //播放组件
    @BindView(R.id.playing_play)
    ImageView iv_play_pause;   //播放-暂停按钮
    @BindView(R.id.playing_pre)
    ImageView iv_play_pre;   //播放-上一首
    @BindView(R.id.playing_next)
    ImageView iv_play_next;   //播放-下一首

    @BindView(R.id.music_duration_played)
    TextView tv_music_duration_played;   //已经播放的时长
    @BindView(R.id.music_duration_total)
    TextView tv_music_duration_total;   //音乐总时长
    @BindView(R.id.play_seek_bar)
    PlayerSeekBar playerSeekBar;   //播放进度
    @BindView(R.id.playing_playlist)
    ImageView iv_show_playlist;    //显示播放列表

    private AlbumListItemTrack cur_playing_song;
    private List<AlbumListItemTrack> cur_playing_list = new ArrayList<>();
    private PlayingQueueFragment queueFragment;

    @OnClick(R.id.playing_play)    //播放或者暂停
    public void play_pause() {
        if (cur_playing_song == null) { //没有任何歌曲在播放，初始进入app的状态
            ToastUtil.getInstance().toast("您还没有播放过任何歌曲");
            return;
        }
        int cur_status = cur_playing_song.getStatus();
        Log.i(TAG, "cur_status:" + cur_status);
        if (cur_status == PlayingMusicStatusConstants.INIT) {
            playMusic();
        } else if (cur_status == PlayingMusicStatusConstants.PLAYING) {
            pauseMusic();
        } else if (cur_status == PlayingMusicStatusConstants.PREPARING) {
            ToastUtil.getInstance().toast("正在准备方法...");
        } else if (cur_status == PlayingMusicStatusConstants.PAUSING) {
            resumeMusic();
        }
    }

    @OnClick(R.id.playing_pre)
    public void playPreSong() {      //播放上一首
        if (cur_playing_song.getIndex() == 0) {
            ToastUtil.getInstance().toast("这是第一首歌曲");
        } else {
            pauseNeedleAnim();
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        }
    }

    @OnClick(R.id.playing_next)
    public void playNextSong() {     //播放下一首
        try {
            List<AlbumListItemTrack> listTrack = MusicPlayer.getInstance().getPlayingListTrack();
            if (cur_playing_song.getIndex() == listTrack.size() - 1) {
                ToastUtil.getInstance().toast("这是最后一首歌曲");
            } else {
                pauseNeedleAnim();
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.playing_playlist)
    public void showPlayingQueueFragment() {   //显示播放列表
        if (queueFragment != null && queueFragment.isVisible()) {
            queueFragment.dismiss();
        } else {
            queueFragment = new PlayingQueueFragment();
            queueFragment.show(getSupportFragmentManager(), "playing_queue_fragment");
        }
    }

    class PlayingSongStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }
            cur_playing_song = extras.getParcelable("cur_song_track");
            if (action.equals(BroadcastConstants.UPDATE_PLAYING_PROGRESS)) {  //更新播放进度
                updateProgressTrack();
            } else if (action.equals(BroadcastConstants.START_PLAY_SONG)) {   //歌曲开始播放
                updateStartTrack();
            } else if (action.equals(BroadcastConstants.RESUME_PLAY_SONG)) {   //歌曲重新开始播放
                updateResumeTrack();
            } else if (action.equals(BroadcastConstants.PAUSE_PLAY_SONG)) {   //歌曲暂停播放
                updatePauseTrack();
            }
        }
    }

    private void updateResumeTrack() {
        Log.i(TAG, "updateResumeTrack");
        iv_play_pause.setImageResource(R.mipmap.play_rdi_btn_pause);
        startRotateAnim();
        startNeedleAnim();
    }

    private void updateProgressTrack() {
        long cur_duration = cur_playing_song.getCur_duration();
        long total_duration = cur_playing_song.getTotal_duration();
        if (total_duration > 0) {
            playerSeekBar.setProgress((int) cur_duration);
        }
        tv_music_duration_played.setText(MusicUtil.makeTimeString((int) cur_duration));
    }

    private void updateStartTrack() {
        setTitle(cur_playing_song.getSongName(), cur_playing_song.getAuthor());
        Log.i(TAG, "updateStartTrack");
        playerSeekBar.setMax((cur_playing_song.getTotal_duration()));
        tv_music_duration_played.setText(MusicUtil.makeTimeString(cur_playing_song.getCur_duration()));
        tv_music_duration_total.setText(MusicUtil.makeTimeString(cur_playing_song.getTotal_duration()));
        iv_play_pause.setImageResource(R.mipmap.play_rdi_btn_pause);
        startRotateAnim();
        startNeedleAnim();
    }

    private void initPausedTrack() {
        int cur_duration = cur_playing_song.getCur_duration();
        int total_duration = cur_playing_song.getTotal_duration();
        playerSeekBar.setMax(total_duration);
        tv_music_duration_played.setText(MusicUtil.makeTimeString(cur_duration));
        tv_music_duration_total.setText(MusicUtil.makeTimeString(total_duration));
        iv_play_pause.setImageResource(R.mipmap.play_rdi_btn_play);

        if (total_duration > 0) {
            playerSeekBar.setProgress(cur_duration);
        }
    }

    private void updatePauseTrack() {
        Log.i(TAG, "updatePauseTrack");
        initPausedTrack();
        pauseRotateAnim();
        pauseNeedleAnim();
    }

    private static final int VIEWPAGER_SCROLL_TIME = 500;

    private static class MyViewPagerScroller extends Scroller {
        private int animTime = VIEWPAGER_SCROLL_TIME;


        MyViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, animTime);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, animTime);
        }
    }

    private void resumeMusic() {
        try {
            MusicPlayer.getInstance().resumeSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        try {
            MusicPlayer.getInstance().pauseSong();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void startRotateAnim() {
        RoundFragment cur_fragment = getCur_fragment();
        if (cur_fragment != null) {
            cur_fragment.startRotate();
//            iv_play_pause.setImageResource(R.mipmap.play_rdi_btn_pause);
        }
    }

    private void resumeRotateAnim() {
        RoundFragment cur_fragment = getCur_fragment();
        if (cur_fragment != null) {
            cur_fragment.resumeRotate();
//            iv_play_pause.setImageResource(R.mipmap.play_rdi_btn_pause);
        }
    }

    private void pauseRotateAnim() {
        RoundFragment cur_fragment = getCur_fragment();
        if (cur_fragment != null) {
            getCur_fragment().pauseRotate();
//            iv_play_pause.setImageResource(R.mipmap.play_rdi_btn_play);
        }
    }

    private boolean mNeedleStatus = false;   //未播放状态

    private void startNeedleAnim() {
        if (!mNeedleStatus) {
            mNeedleStatus = true;
            mNeedleAnim.start();
        }
    }

    private void pauseNeedleAnim() {
        if (mNeedleStatus) {
            mNeedleStatus = false;
            mNeedleAnim.reverse();
        }
    }

    private void playMusic() {
        try {
            MusicPlayer.getInstance().playInQueue(cur_playing_song.getIndex());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ObjectAnimator mNeedleAnim;
    private PlayingSongStatusReceiver receiver;
    private ActionBar ab;
    private FragmentStatePagerAdapter pagerAdapter;

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        initReceiver();
        initNeedleAnim();
        initTooBar();
        //初始化,必须通过Media获取，因为他的数据是由MediaService播放的音乐决定的
        try {
            cur_playing_song = MusicPlayer.getInstance().getPlayingSongTrack();
            cur_playing_list = MusicPlayer.getInstance().getPlayingListTrack();
            setTitle(cur_playing_song.getSongName(), cur_playing_song.getAuthor());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        initView();
        initTrack();
    }

    private void initTrack() {  //根据MediaService播放音乐的状态更新界面
        try {
            cur_playing_song = MusicPlayer.getInstance().getPlayingSongTrack();
        } catch (RemoteException e) {
            Log.i(TAG, e.getMessage());
        }
        Log.i(TAG, "initTrack---status:" + cur_playing_song.getStatus());
        if (cur_playing_song.getStatus() == PlayingMusicStatusConstants.PLAYING) {
            updateStartTrack();
        } else if (cur_playing_song.getStatus() == PlayingMusicStatusConstants.INIT) {
            initPausedTrack();
        } else if (cur_playing_song.getStatus() == PlayingMusicStatusConstants.PAUSING) {
            initPausedTrack();
        }
    }

    private void initReceiver() {
        receiver = new PlayingSongStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastConstants.START_PLAY_SONG);
        filter.addAction(BroadcastConstants.PAUSE_PLAY_SONG);
        filter.addAction(BroadcastConstants.RESUME_PLAY_SONG);
        filter.addAction(BroadcastConstants.UPDATE_PLAYING_PROGRESS);
        registerReceiver(receiver, filter);
    }

    private void initTooBar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.mipmap.actionbar_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void initNeedleAnim() {
        if (mNeedleAnim == null) {
            mNeedleAnim = ObjectAnimator.ofFloat(needle, "rotation", -30, 0);
            mNeedleAnim.setDuration(500);
            mNeedleAnim.setInterpolator(new LinearInterpolator());
            mNeedleAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
//                    mNeedleStatus = !mNeedleStatus;
//                    Log.i(TAG, "mNeedleStatus:" + mNeedleStatus);
                }
            });
        }
    }

//    private List<RoundFragment> fragment_list = new ArrayList<>();
//    private RoundFragment cur_fragment;

    private void initViewPager() {
        pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
//                Log.i(TAG, "getItem-position:" + position);
                RoundFragment roundFragment = RoundFragment.newInstance(cur_playing_list.get(position).getPic_big_url());
//                fragment_list.add(position, roundFragment);
                return roundFragment;
            }

            @Override
            public int getCount() {
                return cur_playing_list.size();
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i(TAG, "---onPageScrolled---:" + position);
//                pauseRotateAnim();
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "---onPageSelected---:" + position);
                Log.i(TAG, "---curIndex---:" + cur_playing_song.getIndex());
                if (position == cur_playing_song.getIndex() && cur_playing_song.getStatus() == PlayingMusicStatusConstants.PLAYING) {
                    getCur_fragment().setRotatePending(true);
                }
                try {
                    if (position != cur_playing_song.getIndex()) {
                        MusicPlayer.getInstance().playInQueue(position);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG, "---onPageScrollStateChanged---:" + state);
                if (state == SCROLL_STATE_DRAGGING) {
                    pauseNeedleAnim();
                    pauseRotateAnim();
                } else if (state == SCROLL_STATE_IDLE) {
                    Log.i(TAG, "---SCROLL_STATE_IDLE---:" + viewPager.getCurrentItem());
                    if ((cur_playing_song.getIndex() == viewPager.getCurrentItem()) && cur_playing_song.getStatus() == PlayingMusicStatusConstants.PLAYING) {
                        startNeedleAnim();
                        startRotateAnim();
                    }
                }
            }
        });
        viewPager.setOnSingleTouchListener(new PlayingDetailAlbumPager.OnSingleTouchListener() {
            @Override
            public void onSingleTouch(View v) {
                if (playing_container.getVisibility() == View.VISIBLE) {
                    showLrcContainer();
                }
            }
        });

        // 改变viewpager动画时间
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            MyViewPagerScroller mScroller = new MyViewPagerScroller(viewPager.getContext().getApplicationContext(), new LinearInterpolator());
            mField.set(viewPager, mScroller);
        } catch (Exception e) {

        }
        viewPager.setAdapter(pagerAdapter);
        int index = cur_playing_song.getIndex();
        Log.i(TAG, "cur_index:" + index);
        viewPager.setCurrentItem(index);
//        Log.i(TAG, "cur_item:" + viewPager.getCurrentItem());
    }

    //拿到当前正在显示的Fragment
    private RoundFragment getCur_fragment() {
        RoundFragment item = (RoundFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
        Log.i(TAG, "getCur_fragment:" + item.toString());
        return item;
    }

    private void setTitle(String title, String subTitle) {
        if (ab != null) {
            ab.setTitle(title);
            ab.setSubtitle(subTitle);
        }
    }

    private void getSongLrc(final String playingSongId, final String lrc) {
        //首先尝试从sp中获取歌词
        String str_lrc = SharePreferenceUtil.readString(playingSongId, "lrc");
        if (!TextUtils.isEmpty(str_lrc)) {
            final List<LrcRow> rows = DefaultLrcParser.getIstance().getLrcRows(str_lrc);
            lrcView.setLrcRows(rows);
            return;
        }
        if (TextUtils.isEmpty(lrc)) {
            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(lrc).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i(TAG, "request_lrc_onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str_lrc = response.body().string();
//                Log.i(TAG, "str_lrc:" + str_lrc);
                final List<LrcRow> rows = DefaultLrcParser.getIstance().getLrcRows(str_lrc);
                if (rows != null) {
                    UIUtil.runInMainThread(new Runnable() {
                        @Override
                        public void run() {
                            lrcView.setLrcRows(rows);
                        }
                    });
                }
                //将其保存到本地，下次进入该界面时，优先从本地文件中获取歌词
//                FileUtil.writeToFile(str_lrc, getExternalCacheDir().getAbsolutePath() + LRC_PATH + playingSongId);
                SharePreferenceUtil.writeString(playingSongId, "lrc", str_lrc);
            }
        });
    }

    private void initView() {
        initViewPager();
//        initAnim(cur_playing_song.getStatus());   //展示动画
        //隐藏歌词控件
        hideLrcContainer();
        lrc_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lrc_container.getVisibility() == View.VISIBLE) {
                    hideLrcContainer();
                }
            }
        });
    }

    private void hideLrcContainer() {
        lrc_container.setVisibility(View.GONE);
        playing_container.setVisibility(View.VISIBLE);
    }

    private void showLrcContainer() {
        lrc_container.setVisibility(View.VISIBLE);
        playing_container.setVisibility(View.GONE);
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_play_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "PlayDetailActivity---onDestroy");
        /**
         * 发送一条广播，显示出底部的悬浮窗
         */
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}
