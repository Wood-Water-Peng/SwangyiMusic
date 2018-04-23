package com.example.jackypeng.swangyimusic.rx.bean.discover;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/17.
 * 独家放送
 */

public class ExclusivePartResult {
    private int code;
    private List<ExclusivePartItem> result;

    public static class ExclusivePartItem {
        private long id;
        private String url;
        private String picUrl;
        private String sPicUrl;
        private String name;
        private String copywriter;
        private String videoId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getsPicUrl() {
            return sPicUrl;
        }

        public void setsPicUrl(String sPicUrl) {
            this.sPicUrl = sPicUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCopywriter() {
            return copywriter;
        }

        public void setCopywriter(String copywriter) {
            this.copywriter = copywriter;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ExclusivePartItem> getResult() {
        return result;
    }

    public void setResult(List<ExclusivePartItem> result) {
        this.result = result;
    }
}
