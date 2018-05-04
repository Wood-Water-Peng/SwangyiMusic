package com.example.jackypeng.swangyimusic.download_music;

/**
 * Created by dzc on 15/11/21.
 */
public interface DownloadTaskListener<T> {

    void onPrepare(T downloadTask);

    void onStart(T downloadTask);

    void onDownloading(T downloadTask);

    void onPause(T downloadTask);

    void onCancel(T downloadTask);

    void onCompleted(T downloadTask);

    void onError(T downloadTask, int errorCode);

    int DOWNLOAD_ERROR_FILE_NOT_FOUND = -1;
    int DOWNLOAD_ERROR_IO_ERROR = -2;

}
