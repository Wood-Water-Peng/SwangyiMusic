package com.example.jackypeng.swangyimusic.rx.bean;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class RadioResultBean {
    private List<RadioItemBean> result;
    private int error_code;

    public List<RadioItemBean> getResult() {
        return result;
    }

    public void setResult(List<RadioItemBean> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
