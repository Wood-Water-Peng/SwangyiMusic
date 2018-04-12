package com.example.jackypeng.swangyimusic.download_music;

import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;

/**
 * Created by dzc on 15/11/21.
 */
public interface DownloadTaskListener {

    void onPrepare(DownloadInfoEntity downloadTask);

    void onStart(DownloadInfoEntity downloadTask);

    void onDownloading(DownloadInfoEntity downloadTask);

    void onPause(DownloadInfoEntity downloadTask);

    void onCancel(DownloadInfoEntity downloadTask);

    void onCompleted(DownloadInfoEntity downloadTask);

    void onError(DownloadInfoEntity downloadTask, int errorCode);

    int DOWNLOAD_ERROR_FILE_NOT_FOUND = -1;
    int DOWNLOAD_ERROR_IO_ERROR = -2;

}
