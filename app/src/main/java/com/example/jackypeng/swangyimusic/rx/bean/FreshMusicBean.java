package com.example.jackypeng.swangyimusic.rx.bean;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class FreshMusicBean {
    private FreshMusicResultBean result;
    private List<Object> module;

    public FreshMusicResultBean getResult() {
        return result;
    }

    public void setResult(FreshMusicResultBean result) {
        this.result = result;
    }

    public List<Object> getModule() {
        return module;
    }

    public void setModule(List<Object> module) {
        this.module = module;
    }
}
