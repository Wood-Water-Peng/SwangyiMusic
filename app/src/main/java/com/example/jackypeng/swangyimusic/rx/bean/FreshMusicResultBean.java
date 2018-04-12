package com.example.jackypeng.swangyimusic.rx.bean;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class FreshMusicResultBean {
    private DiyResultBean diy;
    private Mix_1_ResultBean mix_1;
    private RadioResultBean radio;
    private int error_code;

    public DiyResultBean getDiy() {
        return diy;
    }

    public Mix_1_ResultBean getMix_1_resultBean() {
        return mix_1;
    }

    public void setMix_1_resultBean(Mix_1_ResultBean mix_1_resultBean) {
        this.mix_1 = mix_1_resultBean;
    }

    public RadioResultBean getRadioResultBean() {
        return radio;
    }

    public void setRadioResultBean(RadioResultBean radioResultBean) {
        this.radio = radioResultBean;
    }

    public void setDiy(DiyResultBean diy) {
        this.diy = diy;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
