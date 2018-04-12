package com.example.jackypeng.swangyimusic.util;

import android.view.Gravity;
import android.widget.Toast;

import com.example.jackypeng.swangyimusic.MainApplication;

/**
 * 自定义toast
 *
 * @author Administrator
 */
public class ToastUtil {

    private static ToastUtil instance;

    public static ToastUtil getInstance() {
        synchronized (ToastUtil.class) {
            if (instance == null) {
                instance = new ToastUtil();
            }
        }
        return instance;
    }

    public void toast(String text) {
        Toast.makeText(MainApplication.getAppContext(), text, Toast.LENGTH_SHORT).show();
    }


}
