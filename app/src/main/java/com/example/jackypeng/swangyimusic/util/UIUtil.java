package com.example.jackypeng.swangyimusic.util;

import android.os.Handler;

import com.example.jackypeng.swangyimusic.MainApplication;

/**
 * Created by pj on 2015/11/10.
 */
public class UIUtil {

    public static void runInMainThread(Runnable runnable) {
        post(runnable);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return MainApplication.getMainThreadHandler();
    }
}
