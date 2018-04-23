package com.example.jackypeng.swangyimusic.rx.bean.discover;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/17.
 * 推荐歌单
 */

public class RecommendListResult {
    private int code;
    private List<RecommendListItem> result;

    public static class RecommendListItem {
        private long id;
        private int type;
        private String name;
        private String picUrl;
        private String copywriter;
        private String playCount;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getCopywriter() {
            return copywriter;
        }

        public void setCopywriter(String copywriter) {
            this.copywriter = copywriter;
        }

        public String getPlayCount() {
            return playCount;
        }

        public void setPlayCount(String playCount) {
            this.playCount = playCount;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RecommendListItem> getResult() {
        return result;
    }

    public void setResult(List<RecommendListItem> result) {
        this.result = result;
    }
}
