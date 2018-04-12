package com.example.jackypeng.swangyimusic.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.MediaAidlInterface;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.constants.SpConstants;
import com.example.jackypeng.swangyimusic.rx.api.NetApi;
import com.example.jackypeng.swangyimusic.rx.bean.SongDetailResultBean;
import com.example.jackypeng.swangyimusic.ui.activity.PlayDetailActivity;
import com.example.jackypeng.swangyimusic.util.AESTools;
import com.example.jackypeng.swangyimusic.util.NetUtil;
import com.example.jackypeng.swangyimusic.util.SharePreferenceUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jackypeng on 2018/3/8.
 * 播放音乐的服务
 * <p>
 * 动态注册一个广播，控制音乐的播放
 * <p>
 * 服务得到要播放歌曲的id,然后根据id去获得歌曲的详细信息
 * <p>
 * 服务应该拥有正在播放歌曲的完成信息
 * <p>
 * 新的接口
 * 1.返回该专辑的播放列表
 */

public class MediaService extends Service {
    private static final String TAG = "MediaService";
    public static final int PLAY_CURRENT_SONG = 1;   //播放当前音乐
    public static final int PAUSE_CURRENT_SONG = 2;  //暂停当前音乐
    public static final int SHOW_PLAYING_DETAIL_ACTIVITY = 3;  //显示播放音乐界面
    private AudioManager mAudioManager;
    private ComponentName mMediaButtonReceiverComponent;
    private MultiPlayer mPlayer;
    //服务里面必须保存有播放列表，用户可以自动点击播放下一首,该数据跨进程传递
    private AlbumListItemTrack curSongTrack;  //最后播放的音乐的信息
    //    private HashMap<String, List<AlbumListItemTrack>> playlistInfo = new HashMap<>();
    private List<AlbumListItemTrack> songListTrack = new ArrayList<>(); //当前播放列表
    private int curIndex;  //上一首歌的索引

    /**
     * 播放歌曲的思路
     * 1.一个String[]数组保存歌曲的id
     * 2.一个HashMap<String,AlbumListItemTrack> 保存着id和歌曲信息的映射
     * <p>
     * 问题：如何判断播放列表是否在服务中已经存在？
     * 答：存过保存歌曲id的String[]判断
     */
    private String[] musicIds = new String[0];    //播放队列中所有歌曲的id
    private HashMap<String, AlbumListItemTrack> musicDetailMap = new HashMap<>();  //id和歌曲详情的映射

    //保存单首歌的信息
    public void savePlayingSongInfo() {
        String str_track = JSON.toJSONString(curSongTrack);
        SharePreferenceUtil.writeString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_ID, str_track);
    }

    //保存歌单
    private void savePlayingList() {
        if (songListTrack != null && songListTrack.size() > 0) {
            String str_songList = JSONArray.toJSONString(songListTrack);
            String str_song_id_list = JSONArray.toJSONString(musicIds);
            SharePreferenceUtil.writeString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_LIST, str_songList);
            SharePreferenceUtil.writeString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_ID_LIST, str_song_id_list);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playerService;
    }

    MediaAidlInterface.Stub playerService = new MediaAidlInterface.Stub() {

        @Override
        public String[] getQueueIds() throws RemoteException {
            return musicIds;
        }

        //数据源还没有加载
        @Override
        public void playSongInQueue(int index) throws RemoteException {
            curIndex = index;
            curSongTrack = musicDetailMap.get(musicIds[curIndex]);
            curSongTrack.setIndex(curIndex);
            //判断歌曲是否在本地
            if (curSongTrack.isLocal()) {
                MediaService.this.playLocalSong(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + curSongTrack.getSongId());
            } else {
                MediaService.this.playNetSong(curSongTrack.getSongId());
            }
        }

        @Override
        public void playAllSong(String[] ids, Map map, int index) throws RemoteException {
            musicIds = ids;
            musicDetailMap = (HashMap<String, AlbumListItemTrack>) map;
            curIndex = index;
            curSongTrack = musicDetailMap.get(musicIds[curIndex]);
            curSongTrack.setIndex(curIndex);
            songListTrack.clear();
            for (int i = 0; i < ids.length; i++) {
                songListTrack.add(musicDetailMap.get(musicIds[i]));
            }
            //判断歌曲是否在本地
            if (curSongTrack.isLocal()) {
                MediaService.this.playLocalSong(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + curSongTrack.getSongId());
            } else {
                MediaService.this.playNetSong(curSongTrack.getSongId());
            }
            savePlayingList();
        }

        @Override
        public void resumeSong() throws RemoteException {
            MediaService.this.resumeSong();
        }

        @Override
        public void pauseSong() throws RemoteException {
            MediaService.this.pauseSong();
        }


        @Override
        public String getSongLrc() throws RemoteException {
//            Log.i(TAG, "---getSongLrc---:" + curPlayingSong.toString());
            if (curSongTrack == null) {
                return null;
            } else {
                return curSongTrack.getLrc();
            }
        }

        @Override
        public String getAlbumCover() throws RemoteException {
            if (curSongTrack == null) {
                return null;
            } else {
                return curSongTrack.getAlbum_id();
            }
        }

        @Override
        public String getSongAuthor() throws RemoteException {
            if (curSongTrack == null) {
                return null;
            } else {
                return curSongTrack.getAuthor();
            }
        }

        @Override
        public String getSongTitle() throws RemoteException {
            if (curSongTrack == null) {
                return null;
            } else {
                return curSongTrack.getSongName();
            }
        }

        @Override
        public String getSongId() throws RemoteException {
            if (curSongTrack == null) {
                return null;
            } else {
                return curSongTrack.getSongId();
            }
        }

        @Override
        public boolean isPlayListExist(String albumId) throws RemoteException {
            //判断播放列表是否已经存在
            return false;
        }

        @Override
        public int getSongStatus(String songId) throws RemoteException {
            //拿到歌曲在服务中的状态
            if (curSongTrack != null && curSongTrack.getSongId().equals(songId)) {
                return curSongTrack.getStatus();
            }
            return PlayingMusicStatusConstants.INIT;  //当前歌曲并未在服务中
        }


        @Override
        public int getPlayingStatus() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getStatus();
            }
            return PlayingMusicStatusConstants.INIT;  //当前歌曲并未在服务中
        }

        @Override
        public int getCurPlayingIndex() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getIndex();
            }
            return 0;
        }

        @Override
        public String getLatestSongAuthor() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getAuthor();
            }
            return null;
        }

        @Override
        public String getLatestSongName() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getSongName();
            }
            return null;
        }

        @Override
        public String getLatestSongSmallPic() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getPic_small_url();
            }
            return null;
        }

        @Override
        public String getLatestSongBigPic() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getPic_big_url();
            }
            return null;
        }

        @Override
        public String getLatestSongLrc() throws RemoteException {
            return SharePreferenceUtil.readString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_LRC);
        }

        @Override
        public String getLatestPlayingSongId() throws RemoteException {
            if (curSongTrack != null) {
                return curSongTrack.getSongId();
            }
            return null;
        }

        @Override
        public List<AlbumListItemTrack> getPlayingSongList() throws RemoteException {
            return songListTrack;
        }

        @Override
        public AlbumListItemTrack getPlayingSongInfo() throws RemoteException {
            return curSongTrack;
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mPlayer = new MultiPlayer();
        Log.i(TAG, "MediaService-----onCreate");
        //初始化数据，拿到最后一次播放的歌曲，并获得歌单
        String str_song_track = SharePreferenceUtil.readString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_ID);
        if (!TextUtils.isEmpty(str_song_track)) {   //之前有播放过音乐
            curSongTrack = JSON.parseObject(str_song_track, AlbumListItemTrack.class);
            curSongTrack.setStatus(PlayingMusicStatusConstants.INIT);
            songListTrack = JSON.parseArray(SharePreferenceUtil.readString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_LIST), AlbumListItemTrack.class);
            musicIds = JSON.parseObject(SharePreferenceUtil.readString(SpConstants.LATEST_PLAYING_SONG_INFO, SpConstants.LATEST_PLAYING_SONG_ID_LIST), String[].class);
            if (musicIds.length == songListTrack.size()) {
                for (int i = 0; i < musicIds.length; i++) {
                    musicDetailMap.put(musicIds[i], songListTrack.get(i));
                }
            }
        }

    }

    private void stopSong() {
        mPlayer.stop();
    }

    private void pauseSong() {
        mPlayer.pause();
    }

    private void resumeSong() {
        mPlayer.resumePlay();
    }

    /**
     * 播放本地音乐
     *
     * @param path 本地路径
     */
    private void playLocalSong(String path) {
        //path    content://media/external/audio/media/48315
        mPlayer.playLocalSong(path);
    }

    /**
     * @param songId 要播放音乐的信息
     *               <p>
     *               音乐的url可以根据id拼接
     */
    private void playNetSong(String songId) {
        long timeMillis = System.currentTimeMillis();
        String str = "songid=" + songId + "&ts=" + timeMillis;
        String e = AESTools.encrpty(str);
        StringBuilder builder = new StringBuilder(NetApi.BASE_URL + NetApi.GET_SONG_DETAIL);
        builder.append("&")
                .append(str)
                .append("&e=")
                .append(e);
        Log.i(TAG, "request_url:" + builder.toString());
        NetUtil.getInstance().fetchData(builder.toString(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorSongBroadcast();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Log.i(TAG, "response:" + result);
                final SongDetailResultBean resultBean = JSONObject.parseObject(result, SongDetailResultBean.class);
                if (resultBean == null) {
                    sendErrorSongBroadcast();
                    mPlayer.mCurrentMediaPlayer.reset();
                    return;
                }
                curSongTrack.setLrc(resultBean.getSonginfo().getLrclink());
                //将歌曲信息保存起来
                mPlayer.playNetSong(resultBean.getSongurl().getUrl().get(0).getShow_link());
            }
        });
    }


    //播放歌曲出错
    private void sendErrorSongBroadcast() {
        Intent intent = new Intent(BroadcastConstants.ERROR_PLAYSONG);
        Bundle bundle = new Bundle();
        bundle.putParcelable("cur_song_track", curSongTrack);
        sendBroadcast(intent.putExtras(bundle));
    }

    //歌曲正在你缓冲
    private void sendSongBufferingBroadcast() {
        Intent intent = new Intent(BroadcastConstants.BUFFERING_PLAYSONG);
        Bundle bundle = new Bundle();
        bundle.putParcelable("cur_song_track", curSongTrack);
        sendBroadcast(intent.putExtras(bundle));
    }

    //暂停歌曲
    private void sendPauseSongBroadcast() {
        curSongTrack.setStatus(PlayingMusicStatusConstants.PAUSING);
        Intent intent = new Intent(BroadcastConstants.PAUSE_PLAY_SONG);
        Bundle bundle = new Bundle();
        bundle.putParcelable("cur_song_track", curSongTrack);
        sendBroadcast(intent.putExtras(bundle));
    }

    //歌曲开始播放
    private void sendPlayingStartBroadcast() {
        Log.i(TAG, "---sendPlayingStatusBroadcast---:" + curSongTrack.getCur_duration());
        curSongTrack.setStatus(PlayingMusicStatusConstants.PLAYING);
        Intent intent = new Intent(BroadcastConstants.START_PLAY_SONG);
        Bundle bundle = new Bundle();
        bundle.putParcelable("cur_song_track", curSongTrack);
        sendBroadcast(intent.putExtras(bundle));
//        RecentPlayManager.getInstance().insertInfo(new RecentPlayBean(curSongTrack.getSongId(), System.currentTimeMillis()));
    }

    //歌曲重新开始播放
    private void sendPlayingResumeBroadcast() {
        Log.i(TAG, "---sendPlayingStatusBroadcast---:" + curSongTrack.getCur_duration());
        curSongTrack.setStatus(PlayingMusicStatusConstants.PLAYING);
        Intent intent = new Intent(BroadcastConstants.RESUME_PLAY_SONG);
        Bundle bundle = new Bundle();
        bundle.putParcelable("cur_song_track", curSongTrack);
        sendBroadcast(intent.putExtras(bundle));
//        RecentPlayManager.getInstance().insertInfo(new RecentPlayBean(curSongTrack.getSongId(), System.currentTimeMillis()));
    }

    //发送更新进度的广播
    private void sendUpdateProgressBroadcast() {
        if (curSongTrack.getCur_duration() < curSongTrack.getTotal_duration()) {
//            Log.i(TAG, "---sendUpdateProgressBroadcast---:" + curSongTrack.getCur_duration());
            Intent intent = new Intent(BroadcastConstants.UPDATE_PLAYING_PROGRESS);
            Bundle bundle = new Bundle();
            bundle.putParcelable("cur_song_track", curSongTrack);
            sendBroadcast(intent.putExtras(bundle));
        }
    }


    private final class MultiPlayer implements MediaPlayer.OnErrorListener,
            MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

        private MediaPlayer mCurrentMediaPlayer = new MediaPlayer();

        private boolean mIsSourceInited = false;  //判断该播放器是否加载过资源

        public boolean ismIsSourceInited() {   //外部类只能获得，不能修改
            return mIsSourceInited;
        }

        /**
         * @param path 本地音乐路径
         */
        private void playLocalSong(String path) {
            mHandler.removeCallbacksAndMessages(null);
            mCurrentMediaPlayer.reset();
            mCurrentMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                if (path.startsWith("content://")) {
                    mCurrentMediaPlayer.setDataSource(MainApplication.getAppContext(), Uri.parse(path));
                    mCurrentMediaPlayer.prepare();
                    mCurrentMediaPlayer.setOnPreparedListener(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * @param url 网络音乐路径
         */
        private void playNetSong(String url) {
            mHandler.removeCallbacksAndMessages(null);
            mCurrentMediaPlayer.reset();
            mCurrentMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mCurrentMediaPlayer.setDataSource(url);
                mCurrentMediaPlayer.prepare();
                mCurrentMediaPlayer.setOnPreparedListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onCompletion(MediaPlayer mp) {
            //歌曲播放完成，根据播放模式，从播放队列中取出下一首歌
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            sendErrorSongBroadcast();
            return false;
        }

//        public void start(final SongDetailResultBean resultBean) {
//            tryToPrepareSource(resultBean.getSongurl().getUrl().get(0).getShow_link());
//        }

        private void tryToPrepareSource(String path) {
            mHandler.removeCallbacksAndMessages(null);
            mCurrentMediaPlayer.reset();
            mCurrentMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                if (path.startsWith("content://")) {
                    mCurrentMediaPlayer.setDataSource(MainApplication.getAppContext(), Uri.parse(path));
                    mCurrentMediaPlayer.prepare();
                    mCurrentMediaPlayer.setOnPreparedListener(this);
                } else {
                    mCurrentMediaPlayer.setDataSource(path);
                    mCurrentMediaPlayer.setOnPreparedListener(this);
                    mCurrentMediaPlayer.prepareAsync();
                }
            } catch (Exception e) {
            }
        }

        //继续播放
        public void resumePlay() {
            mCurrentMediaPlayer.start();
            sendPlayingResumeBroadcast();
            mHandler.post(updateProgressTask);
        }

        //暂停歌曲
        public void pause() {
            if (mCurrentMediaPlayer.isPlaying()) {
                mCurrentMediaPlayer.pause();
                mHandler.removeCallbacksAndMessages(null);
                sendPauseSongBroadcast();
                savePlayingSongInfo();
//                savePlayingList();
            }
        }

        //停止正在播放的歌曲
        public void stop() {
            mCurrentMediaPlayer.stop();
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mCurrentMediaPlayer.start();  //此时正式开始播放
            mCurrentMediaPlayer.seekTo(curSongTrack.getCur_duration());
            curSongTrack.setCur_duration(mCurrentMediaPlayer.getCurrentPosition());
            curSongTrack.setTotal_duration(mCurrentMediaPlayer.getDuration());
//            savePlayingList();
            savePlayingSongInfo();
            sendPlayingStartBroadcast();   //更新播放状态
            mHandler.post(updateProgressTask);  //更新进度
        }
    }

    //每隔1s钟，如果音乐正在播放，主动的发送进度广播
    private static Handler mHandler = new Handler();

    private Runnable updateProgressTask = new Runnable() {
        @Override
        public void run() {
            if (curSongTrack.getStatus() == PlayingMusicStatusConstants.PLAYING) {
                curSongTrack.setCur_duration(mPlayer.mCurrentMediaPlayer.getCurrentPosition());
                sendUpdateProgressBroadcast();
                mHandler.postDelayed(updateProgressTask, 1000);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "MediaService-----onDestroy");
    }

}
