package com.example.jackypeng.swangyimusic.rx.view.rxView;

import com.example.jackypeng.swangyimusic.rx.bean.SongDetailResultBean;

/**
 * Created by jackypeng on 2018/3/11.
 * g根据歌曲id，获得歌曲详情
 */

public interface GetSongDetailView extends BaseView{
    void doFetchSongDetail(SongDetailResultBean result);
}
