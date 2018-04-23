package com.example.jackypeng.swangyimusic.rx.bean;

/**
 * Created by jackypeng on 2018/4/19.
 */

public class PlayingLrcBean {
    private int code;
    private PlayingLrcBeanData lrc;
    public static class PlayingLrcBeanData {
        private String lyric;

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PlayingLrcBeanData getLrc() {
        return lrc;
    }

    public void setLrc(PlayingLrcBeanData lrc) {
        this.lrc = lrc;
    }
}
