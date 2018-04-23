package com.example.jackypeng.swangyimusic.rx.api;

import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumDetailBean;
import com.example.jackypeng.swangyimusic.rx.bean.ArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayUrlBean;
import com.example.jackypeng.swangyimusic.rx.bean.PlayingLrcBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResponseBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.SongDetailResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioDetailResult;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.example.jackypeng.swangyimusic.rx.api.NetApi.ACTIVITY_RADIO_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.FRAGMENT_DISCOVER_BANNER;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.FRAGMENT_DISCOVER_EXCLUSIVE_PART;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.FRAGMENT_DISCOVER_RECOMMEND_LIST;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.FRAGMENT_DISCOVER_RECOMMEND_MV;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.FRAGMENT_DISCOVER_RECOMMEND_RADIO;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.FRAGMENT_RADIO_PROGRAM;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_ALBUM_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_CAROUSEL_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_FRESH_MUSIC;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_RADIO_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_SONG_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.PLAYING_LIST_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.PLAYING_MUSIC_LRC;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.PLAYING_MUSIC_URL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.QUERY_WITH_ALBUM;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.QUERY_WITH_ARTIST;

/**
 * Created by jackypeng on 2018/3/6.
 * 新曲界面
 */

public interface NewMusicApi {
    /**
     * @return 获取最新音乐
     */
    @GET(GET_FRESH_MUSIC)
    Observable<FreshMusicBean> getFreshMusic();

    /**
     * @return 获取专辑详情
     */
    @GET(GET_ALBUM_DETAIL)
    Observable<AlbumDetailBean> getAlbumDetail(@Query("album_id") String album_id);

    /**
     * @return 获取电台详情
     */
    @GET(ACTIVITY_RADIO_DETAIL)
    Observable<RadioDetailResult> getRadioDetail(@Query("rid") String rid);

    /**
     * @return 获取歌曲详情
     */
    @GET(GET_SONG_DETAIL)
    Observable<SongDetailResultBean> getSongDetail(@Query("songid") String album_id, @Query("ts") String ts, @Query("e") String e);

    /**
     * @return 获取作者相关信息
     */
    @GET(QUERY_WITH_ARTIST)
    Observable<JSONObject> queryArtist(@Query("artist") String artist);

    /**
     * @return 获取专辑相关信息
     */
    @GET(QUERY_WITH_ALBUM)
    Observable<String> queryAlbum(@Query("artist") String artist, @Query("album") String album);

    /**
     * @return 获取首页轮播图信息
     */
    @GET(GET_CAROUSEL_DETAIL)
    Observable<JSONObject> getCarouselDetail(@Query("num") String num);


    //新增音乐接口----------------------------------------------------------------------------

    /**
     * @return 获取发现页面轮播图信息
     */
    @GET(FRAGMENT_DISCOVER_BANNER)
    Observable<BannerResultBean> getDiscoverBanner();

    /**
     * @return 获取发现页面推荐歌单
     */
    @GET(FRAGMENT_DISCOVER_RECOMMEND_LIST)
    Observable<RecommendListResult> getDiscoverRecommendList(@Query("limit") int limit);

    /**
     * @return 获取发现页面推荐MV
     */
    @GET(FRAGMENT_DISCOVER_RECOMMEND_MV)
    Observable<RecommendMVResult> getDiscoverRecommendMV();

    /**
     * @return 获取发现页面独家放送
     */
    @GET(FRAGMENT_DISCOVER_EXCLUSIVE_PART)
    Observable<ExclusivePartResult> getDiscoverExclusivePart();

    /**
     * @return 发现页面推荐电台
     */
    @GET(FRAGMENT_DISCOVER_RECOMMEND_RADIO)
    Observable<RecommendRadioResult> getDiscoverRecommendRadio();

    /**
     * @return 电台节目
     */
    @GET(FRAGMENT_RADIO_PROGRAM)
    Observable<RadioProgramResult> getRadioProgram(@Query("rid") String rid);

    /**
     * @return 播放音乐的url信息
     */
    @GET(PLAYING_MUSIC_URL)
    Observable<PlayUrlBean> getPlayingUrl(@Query("id") String id);

    /**
     * @return 播放音乐的lrc信息
     */
    @GET(PLAYING_MUSIC_LRC)
    Observable<PlayingLrcBean> getPlayingLrc(@Query("id") String id);
    /*
     * @return 歌单详情
     */
    @GET(PLAYING_LIST_DETAIL)
    Observable<PlayingListDetailResult> getPlayingListDetail(@Query("id") String id);
}
