package com.example.jackypeng.swangyimusic.rx.bean.user;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/25.
 * 用户歌单
 */

public class UserPlayListBean {
    private int code;
    private List<UserPlayListItem> playlist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<UserPlayListItem> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<UserPlayListItem> playlist) {
        this.playlist = playlist;
    }

    public static class UserPlayListItem {
        private String name;
        private String id;
        private String coverImgUrl;
        private int trackCount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public int getTrackCount() {
            return trackCount;
        }

        public void setTrackCount(int trackCount) {
            this.trackCount = trackCount;
        }
    }
}
