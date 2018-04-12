package com.example.jackypeng.swangyimusic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.jackypeng.swangyimusic.MainApplication;

/**
 * Created by pj on 2015/11/25.
 */
public class SharePreferenceUtil {
    private static SharedPreferences mSharePre;

    /**
     * 保存4个sp文件
     * 1.记录最后一次登录的账号
     * 3.记录每一个账号所对应的用户信息，包括密码，手机密码，姓名，身份证号等
     *
     * @param fn  name of sharePreference file
     * @param key
     * @return
     */
    public static String readString(String fn, String key) {
        if (TextUtils.isEmpty(fn))
            return "";
        mSharePre = MainApplication.getAppContext().getSharedPreferences(fn, Context.MODE_PRIVATE);
        return mSharePre.getString(key, "");
    }


    public static void writeString(String fn, String key, String data) {
        if (TextUtils.isEmpty(fn))
            return;
        mSharePre = MainApplication.getAppContext().getSharedPreferences(fn, Context.MODE_PRIVATE);
        mSharePre.edit().putString(key, data).commit();
    }

    public static void writeBoolean(String fn, String key, boolean value) {
        if (TextUtils.isEmpty(fn))
            return;
        mSharePre = MainApplication.getAppContext().getSharedPreferences(fn, Context.MODE_PRIVATE);
        mSharePre.edit().putBoolean(key, value).commit();
    }

    public static boolean readBoolean(String fn, String key) {
        if (TextUtils.isEmpty(fn))
            return false;
        mSharePre = MainApplication.getAppContext().getSharedPreferences(fn, Context.MODE_PRIVATE);
        return mSharePre.getBoolean(key, false);  //如果键值不存在,则返回false
    }

}
