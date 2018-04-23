package com.example.jackypeng.swangyimusic.rx.bean.discover;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class RecommendRadioResult {
    private int code;
    private List<RecommendListRadioItem> programs;
    private boolean more;

    public static class RadioItem {
        private String id;
        private String picUrl;
        private String desc;
        private String category;
        private String programCount;
        private String name;
        private String subCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getProgramCount() {
            return programCount;
        }

        public void setProgramCount(String programCount) {
            this.programCount = programCount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubCount() {
            return subCount;
        }

        public void setSubCount(String subCount) {
            this.subCount = subCount;
        }
    }

    public static class RecommendListRadioItem {
        private String name;
        private String listenerCount;
        private RadioItem radio;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getListenerCount() {
            return listenerCount;
        }

        public void setListenerCount(String listenerCount) {
            this.listenerCount = listenerCount;
        }

        public RadioItem getRadio() {
            return radio;
        }

        public void setRadio(RadioItem radio) {
            this.radio = radio;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RecommendListRadioItem> getPrograms() {
        return programs;
    }

    public void setPrograms(List<RecommendListRadioItem> programs) {
        this.programs = programs;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }
}
