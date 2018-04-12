package com.example.jackypeng.swangyimusic.rx.presenter;

import com.example.jackypeng.swangyimusic.rx.bean.SongDetailResultBean;
import com.example.jackypeng.swangyimusic.rx.model.GetSongDetailModel;
import com.example.jackypeng.swangyimusic.rx.view.rxView.GetSongDetailView;

import rx.Subscriber;

/**
 * Created by jackypeng on 2018/3/11.
 * g根据歌曲id，获得歌曲详情
 */

public class GetSongDetailPresenter {
    private GetSongDetailView view;
    private GetSongDetailModel model;

    public GetSongDetailPresenter(GetSongDetailView view) {
        this.view = view;
        model = new GetSongDetailModel();
    }

    public void getSongDetail(String songId, String ts, String e) {
        model.getSongDetail(songId, ts, e).subscribe(new Subscriber<SongDetailResultBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SongDetailResultBean songDetailInfoBean) {
                if (view != null) {
                    view.doFetchSongDetail(songDetailInfoBean);
                }
            }
        });
    }


}
