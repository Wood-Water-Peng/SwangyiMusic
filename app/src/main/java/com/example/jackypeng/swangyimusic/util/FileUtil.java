package com.example.jackypeng.swangyimusic.util;

import android.os.Environment;
import android.util.Log;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.constants.SpConstants;
import com.example.jackypeng.swangyimusic.lrc.DefaultLrcParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jackypeng on 2018/3/12.
 */

public class FileUtil {
    public static final String ROOT_DIR_NAME = "Swangyi";   //根目录名称
    public static final String MUSIC_DIR_NAME = "Cloudmusic";   //音乐目录名称
    public static final String DOWNLOAD_DIR_NAME = "Download";   //下载目录名称
    public static final String DOWNLOAD_MUSIC_DIR_NAME = "Music";   //下载的音乐目录名称
    public static final String DOWNLOAD_IMAGE_DIR_NAME = "Image";   //下载的图片目录名称
    public static final String DOWNLOAD_LYRIC_DIR_NAME = "Lyric";   //下载的歌词目录名称
    public static final String DOWNLOAD_ALBUM_IMAGE_DIR_NAME = "Album";   //下载的专辑图片名称
    public static final String DOWNLOAD_ARTIST_IMAGE_DIR_NAME = "Artist";   //下载的艺术家图片名称
    private static final String TAG = "FileUtil";


    //保存某个文件夹的状态
    public static void saveDirStatus(File dir) {
        if (dir != null) {
            SharePreferenceUtil.writeString(SpConstants.APP_INFO, SpConstants.DOWNLOAD_MUSIC_DIR_FREE_SPACE, String.valueOf(dir.getFreeSpace()));
            SharePreferenceUtil.writeString(SpConstants.APP_INFO, SpConstants.DOWNLOAD_MUSIC_DIR_LAST_MODIFIED_TIME, String.valueOf(System.currentTimeMillis()));
        }
    }

    //判断文件夹下是否有文件被删除
    public static boolean hasFileDeleted(File dir) {
        if (dir != null) {
            String str_lastest_free_space = SharePreferenceUtil.readString(SpConstants.APP_INFO, SpConstants.DOWNLOAD_MUSIC_DIR_FREE_SPACE);
            String str_latest_modified_time = SharePreferenceUtil.readString(SpConstants.APP_INFO, SpConstants.DOWNLOAD_MUSIC_DIR_LAST_MODIFIED_TIME);
            String str_cur_freeSpace = String.valueOf(dir.getFreeSpace());
            String str_cur_modified_time = String.valueOf(dir.lastModified());
            Log.i(TAG,"str_latest_modified_time:"+str_latest_modified_time);
            Log.i(TAG,"str_cur_modified_time:"+str_cur_modified_time);
            if ((!str_cur_freeSpace.equals(str_lastest_free_space)) && (str_cur_modified_time != str_latest_modified_time)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    //创建下载歌曲的目录
    public static File getDownloadMusicDir() {
        File download_music_dir = new File(Environment.getExternalStorageDirectory(),
                File.separator + ROOT_DIR_NAME + File.separator + MUSIC_DIR_NAME + File.separator + DOWNLOAD_DIR_NAME + File.separator + DOWNLOAD_MUSIC_DIR_NAME);
        if (!download_music_dir.exists()) {
            download_music_dir.mkdirs();
        }
        return download_music_dir;
    }

    public static void writeToFile(String content, String path) {
        File lrc_file = new File(path);
        if (!lrc_file.exists()) {
            try {
                lrc_file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(lrc_file);
                outputStream.write(content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileString(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder sb = new StringBuilder();
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
