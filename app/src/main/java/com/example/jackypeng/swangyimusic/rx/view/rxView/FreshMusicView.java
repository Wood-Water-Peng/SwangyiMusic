package com.example.jackypeng.swangyimusic.rx.view.rxView;

import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;

/**
 * Created by jackypeng on 2018/3/7.
 */

public interface FreshMusicView extends BaseView {
    void doFetchFreshMusic(FreshMusicBean result);
    void doFetchCarouselDetail(JSONObject result);
}
