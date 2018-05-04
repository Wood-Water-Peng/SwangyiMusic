package com.example.jackypeng.swangyimusic.download_music;

/**
 * Created by jackypeng on 2018/4/27.
 */

public abstract class SimpleDownloadTaskListener implements DownloadTaskListener<MusicDownloadTrack> {
    @Override
    public void onPrepare(MusicDownloadTrack downloadTrack) {

    }

    @Override
    public void onPause(MusicDownloadTrack downloadTrack) {

    }

    @Override
    public void onCancel(MusicDownloadTrack downloadTrack) {

    }
}
