package com.example.jackypeng.swangyimusic.rx.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.bean.DaoMaster;
import com.example.jackypeng.swangyimusic.rx.bean.DaoSession;
import com.example.jackypeng.swangyimusic.rx.bean.RecentPlayBean;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class RecentPlayManager {
    //最近播放过的歌曲
    private static final String DB_NAME = MainApplication.getAppContext().getExternalCacheDir() + "/db/recent_play.db";
    private static RecentPlayManager manager;
    private final DaoMaster.DevOpenHelper helper;
    private final SQLiteDatabase db;
    private final DaoMaster daoMaster;
    private final DaoSession daoSession;

    public static RecentPlayManager getInstance() {
        synchronized (RecentPlayManager.class) {
            if (manager == null) {
                synchronized (RecentPlayManager.class) {
                    manager = new RecentPlayManager();
                }
            }
        }
        return manager;
    }

    private RecentPlayManager() {
        helper = new DaoMaster.DevOpenHelper(MainApplication.getAppContext(), DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insertInfo(RecentPlayBean recentPlayBean) {
        daoSession.getRecentPlayBeanDao().insertOrReplace(recentPlayBean);
    }

    public void updateInfo(RecentPlayBean recentPlayBean) {
        daoSession.getRecentPlayBeanDao().insertOrReplace(recentPlayBean);
    }

    public void deleteInfo(RecentPlayBean recentPlayBean) {
        daoSession.getRecentPlayBeanDao().delete(recentPlayBean);
    }

    //拿到最近播放的10首歌
    public List<RecentPlayBean> getRecentPlayList() {
        return daoSession.queryBuilder(RecentPlayBean.class).limit(10).list();
    }
}
