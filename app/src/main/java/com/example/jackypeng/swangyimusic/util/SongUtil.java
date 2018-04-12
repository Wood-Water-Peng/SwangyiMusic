package com.example.jackypeng.swangyimusic.util;

import com.example.jackypeng.swangyimusic.rx.api.NetApi;

/**
 * Created by jackypeng on 2018/3/11.
 * <p>
 * 根据歌曲的id得到歌曲的详细信息
 */

public class SongUtil {
    public static String songInfo(String songid) {
        StringBuffer sb = new StringBuffer(NetApi.BASE_URL);
        String str = "songid=" + songid + "&ts=" + System.currentTimeMillis();
        String e = AESTools.encrpty(str);
        sb.append("&method=").append("baidu.ting.song.getInfos")
                .append("&").append(str)
                .append("&e=").append(e);
        return sb.toString();
    }
}
