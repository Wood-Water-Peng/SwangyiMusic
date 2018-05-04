package com.example.jackypeng.swangyimusic.util;

import android.util.Log;

import com.example.jackypeng.swangyimusic.constants.SpConstants;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/4/28.
 */

public class DBUtil {
    private static final String TAG = "DBUtil";

    /**
     * @param dir 根据文件夹中的数据更新下载数据库记录
     */
    public static void updateDBRecords(File dir) {
        if (dir == null) return;
        Log.i(TAG, "文件夹中有音乐被删除");
        List<String> filePathList = new ArrayList<>();
        //迭代下载音乐的文件夹
        for (File temp : dir.listFiles()) {
            if (!temp.isDirectory()) {
                String absolutePath = temp.getAbsolutePath();
                filePathList.add(absolutePath);
            }
        }

        List<String> extraIds = new ArrayList<>();
        //跟新下载数据库记录
        List<MusicDownloadTrack> finishedSongList = DownloadDBManager.getInstance().getFinishedSongList();
        for (MusicDownloadTrack track : finishedSongList) {
            if (!filePathList.contains(track.getAbsolutePath())) {
                extraIds.add(track.getMusicId());
                Log.i(TAG, track.getMusicName() + "被移除");
            }
        }
        for (String id : extraIds) {
            DownloadDBManager.getInstance().deleteInfo(id);
        }
    }
}
