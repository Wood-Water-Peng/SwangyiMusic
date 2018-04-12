package com.example.jackypeng.swangyimusic.rx.view.rxView;

import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResponseBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResultBean;

/**
 * Created by jackypeng on 2018/3/7.
 */

public interface RadioDetailView extends BaseView {
    void doFetchRadioDetail(RadioDetailResponseBean radioDetailBean);
}
