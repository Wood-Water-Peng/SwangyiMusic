package com.example.jackypeng.swangyimusic.rx.bean.discover;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class RecommendMVResult {
    private int code;
    private List<RecommendMVItem> result;

    public static class RecommendMVItem {
        private long id;
        private String name;
        private String copywiter;
        private String picUrl;
        private long duration;
        private long playCount;
        private String artistName;
        private long artistId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCopywiter() {
            return copywiter;
        }

        public void setCopywiter(String copywiter) {
            this.copywiter = copywiter;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public long getPlayCount() {
            return playCount;
        }

        public void setPlayCount(long playCount) {
            this.playCount = playCount;
        }

        public String getArtistName() {
            return artistName;
        }

        public void setArtistName(String artistName) {
            this.artistName = artistName;
        }

        public long getArtistId() {
            return artistId;
        }

        public void setArtistId(long artistId) {
            this.artistId = artistId;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RecommendMVItem> getResult() {
        return result;
    }

    public void setResult(List<RecommendMVItem> result) {
        this.result = result;
    }
}
