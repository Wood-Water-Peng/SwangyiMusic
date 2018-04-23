package com.example.jackypeng.swangyimusic.rx.bean.discover;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/17.
 */

public class BannerResultBean {
    private List<JSONObject> banners;
    private int code;

    public List<JSONObject> getBanners() {
        return banners;
    }

    public void setBanners(List<JSONObject> banners) {
        this.banners = banners;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
