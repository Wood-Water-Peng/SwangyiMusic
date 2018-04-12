package com.example.jackypeng.swangyimusic.rx.api;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class NetApi {
    public static final String BASE_URL = "http://tingapi.ting.baidu.com/";
    public static final String QUERY_BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    public static final String MIDDLE_COMMON_URL = "v1/restserver/ting?from=android&version=5.6.5.6&format=json";
    //新歌
    public static final String GET_FRESH_MUSIC = MIDDLE_COMMON_URL + "&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14";
    //专辑
    public static final String GET_ALBUM_DETAIL = MIDDLE_COMMON_URL + "&method=baidu.ting.album.getAlbumInfo";
    //歌曲详情
    public static final String GET_SONG_DETAIL = MIDDLE_COMMON_URL + "&method=baidu.ting.song.getInfos";
    //主播电台
    public static final String GET_RADIO_DETAIL = MIDDLE_COMMON_URL + "&method=baidu.ting.lebo.albumInfo";

    //根据专辑名查询
    public static final String QUERY_WITH_ALBUM = "?method=album.getinfo&api_key=fdb3a51437d4281d4d64964d333531d4&format=json";

    //根据作者名查询
    public static final String QUERY_WITH_ARTIST = "?method=artist.getinfo&api_key=fdb3a51437d4281d4d64964d333531d4&format=json";


    //歌单信息   关键词：listid
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.diy.gedanInfo&listid=365408623

    //歌单里面具体某一首歌的信息    关键词：song_id
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.song.baseInfos&song_id=541615960
    //获得专辑信息
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.album.getAlbumInfo&album_id=578951463

    //根据歌词id获得歌曲的信息
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.song.getInfos&songid=578951469&ts=1520823217763&e=8AgKMs0EKmKjAE7z6uKBd5OLAvw6Cf%252FHgb64KQHF50ZZ61EPfn%252BJJaWur7O93tU7


}
