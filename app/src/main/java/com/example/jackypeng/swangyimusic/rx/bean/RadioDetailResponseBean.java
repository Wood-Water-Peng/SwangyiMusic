package com.example.jackypeng.swangyimusic.rx.bean;

/**
 * Created by jackypeng on 2018/3/14.
 */

public class RadioDetailResponseBean {
    private String error_code;
    private RadioDetailResultBean result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public RadioDetailResultBean getResult() {
        return result;
    }

    public void setResult(RadioDetailResultBean result) {
        this.result = result;
    }
}
