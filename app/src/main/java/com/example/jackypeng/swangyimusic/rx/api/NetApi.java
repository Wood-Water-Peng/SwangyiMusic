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

    //首页轮播图信息
    public static final String GET_CAROUSEL_DETAIL = MIDDLE_COMMON_URL + "&method=baidu.ting.plaza.getFocusPic";

    //歌单信息   关键词：listid
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.diy.gedanInfo&listid=365408623

    //歌单里面具体某一首歌的信息    关键词：song_id
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.song.baseInfos&song_id=541615960
    //获得专辑信息
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.album.getAlbumInfo&album_id=578951463

    //根据歌词id获得歌曲的信息
    //http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.song.getInfos&songid=578951469&ts=1520823217763&e=8AgKMs0EKmKjAE7z6uKBd5OLAvw6Cf%252FHgb64KQHF50ZZ61EPfn%252BJJaWur7O93tU7


    //新增音乐接口----------------------------------------------------------------------------
    public static final String NEW_BASE_URL = "http://192.168.1.112:3000";
    //发现中的banner
    public static final String FRAGMENT_DISCOVER_BANNER = "/banner";

    //推荐歌单
    public static final String FRAGMENT_DISCOVER_RECOMMEND_LIST = "/personalized";

    //推荐MV
    public static final String FRAGMENT_DISCOVER_RECOMMEND_MV = "/personalized/MV";

    //推荐节目
    public static final String FRAGMENT_DISCOVER_RECOMMEND_RADIO = "/program/recommend";

    //独家放送
    public static final String FRAGMENT_DISCOVER_EXCLUSIVE_PART = "/personalized/privatecontent";

    //电台详情
    public static final String ACTIVITY_RADIO_DETAIL = "/dj/detail";

    //电台 节目
    public static final String FRAGMENT_RADIO_PROGRAM = "/dj/program";

    //电台和音乐的url
    public static final String PLAYING_MUSIC_URL = "/music/url";

    //电台和音乐的lrc
    public static final String PLAYING_MUSIC_LRC = "/lyric";

    //歌单详情
    public static final String PLAYING_LIST_DETAIL = "/playlist/detail";

    //登录---手机
    public static final String LOGIN = "/login/cellphone";

    //获取用户歌单(需要登录)
    public static final String GET_USER_PLAYLIST = "/user/playlist";


}
