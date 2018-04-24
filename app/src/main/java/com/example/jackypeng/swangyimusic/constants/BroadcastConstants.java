package com.example.jackypeng.swangyimusic.constants;

/**
 * Created by jackypeng on 2018/3/11.
 */

public class BroadcastConstants {
    //更新进度
    public static final String UPDATE_PLAYING_PROGRESS = "update_playing_progress";
    //暂停播放
    public static final String PAUSE_PLAY_SONG = "pause_play_song";
    //开始播放
    public static final String START_PLAY_SONG = "start_play_song";
    //重新开始播放
    public static final String RESUME_PLAY_SONG = "resume_play_song";
    //正在缓冲歌曲
    public static final String BUFFERING_PLAYSONG = "buffering_song";
    //播放歌曲出错
    public static final String ERROR_PLAYSONG = "error_play_song";
    //歌词信息
    public static final String PLAYSONG_LRC = "playing_song_lrc";

    //加载歌曲url出错(网络错误)
    public static final String ERROR_LOADING_MUSIC_URL = "error_loading_music_url";

    //加载歌曲lrc出错(网络错误)
    public static final String ERROR_LOADING_MUSIC_LRC = "error_loading_music_lrc";


    //结束上一首音乐的播放(切换音乐播放时调用)
    public static final String FINISH_LAST_MUSIC = "finish_last_music";


    //加载轮播图数据
    public static final String LOAD_BANNER = "load_banner";

    //加载推荐歌单
    public static final String LOAD_RECOMMEND_LIST = "load_recommend_list";

    //加载独家放送
    public static final String LOAD_EXCLUSIVE_PART = "load_exclusive_part";

    //加载推荐电台
    public static final String LOAD_RECOMMEND_RADIO = "load_recommend_radio";

    //    ---------------------------------------------------   通知栏
    //跳转到播放详情界面
    public static final String START_PLAY_DETAIL_ACTIVITY = "start_play_detail_activity";


    public static final String PLAY_CUR_MUSIC = "play_cur_music";
    public static final String PAUSE_CUR_MUSIC = "pause_cur_music";
    public static final String PLAY_PRE_MUSIC = "play_pre_music";
    public static final String PLAY_NEXT_MUSIC = "play_next_music";

}
