package com.example.jackypeng.swangyimusic.rx.bean;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/19.
 */

public class PlayUrlBean {
    private int code;
    private List<PlayUrlBeanData> data;

    public static class PlayUrlBeanData {
        private String id;
        private String url;
        private long size;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<PlayUrlBeanData> getData() {
        return data;
    }

    public void setData(List<PlayUrlBeanData> data) {
        this.data = data;
    }
}
