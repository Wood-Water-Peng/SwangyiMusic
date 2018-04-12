package com.example.jackypeng.swangyimusic.rx.view.rxView;

import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;

/**
 * Created by jackypeng on 2018/3/7.
 */

public interface SongMenuView extends BaseView {
    void doFetchFreshMusic(FreshMusicBean result);
}
