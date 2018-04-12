package com.example.jackypeng.swangyimusic.rx.api;

import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumDetailBean;
import com.example.jackypeng.swangyimusic.rx.bean.ArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResponseBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.SongDetailResultBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_ALBUM_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_FRESH_MUSIC;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_RADIO_DETAIL;
import static com.example.jackypeng.swangyimusic.rx.api.NetApi.GET_SONG_DETAIL;
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
    @GET(GET_RADIO_DETAIL)
    Observable<RadioDetailResponseBean> getRadioDetail(@Query("album_id") String album_id, @Query("num") int num);

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

}
