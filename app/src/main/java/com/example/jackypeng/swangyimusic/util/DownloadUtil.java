package com.example.jackypeng.swangyimusic.util;

import android.os.Environment;

import com.example.jackypeng.swangyimusic.MainApplication;

import java.io.File;

/**
 * Created by jackypeng on 2018/3/20.
 */

public class DownloadUtil {
    public static String getDownloadPath() {
        return MainApplication.getAppContext().getExternalCacheDir() + "/music";
    }
}
