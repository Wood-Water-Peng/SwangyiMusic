package com.example.jackypeng.swangyimusic.rx.bean.radioDetail;

/**
 * Created by jackypeng on 2018/4/18.
 * Activity---电台详情
 */

public class RadioDetailResult {
    private int code;
    private RadioDetailItem djRadio;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RadioDetailItem getDjRadio() {
        return djRadio;
    }

    public void setDjRadio(RadioDetailItem djRadio) {
        this.djRadio = djRadio;
    }

    public static class RadioDetailItem {
        private String id;
        private String name;
        private String picUrl;
        private String desc;
        private String subCount;
        private String programCount;
        private String category;
        private String serialNum;

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSubCount() {
            return subCount;
        }

        public void setSubCount(String subCount) {
            this.subCount = subCount;
        }

        public String getProgramCount() {
            return programCount;
        }

        public void setProgramCount(String programCount) {
            this.programCount = programCount;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

}
