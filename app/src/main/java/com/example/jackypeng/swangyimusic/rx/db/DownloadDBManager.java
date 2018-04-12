package com.example.jackypeng.swangyimusic.rx.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.rx.bean.DaoMaster;
import com.example.jackypeng.swangyimusic.rx.bean.DaoSession;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntityDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class DownloadDBManager {
    private static final String DB_NAME = MainApplication.getAppContext().getExternalCacheDir() + "/db/download_info.db";
    private static DownloadDBManager manager;
    private final DaoMaster.DevOpenHelper helper;
    private final SQLiteDatabase db;
    private final DaoMaster daoMaster;
    private final DaoSession daoSession;

    public static DownloadDBManager getInstance() {
        synchronized (DownloadDBManager.class) {
            if (manager == null) {
                synchronized (DownloadDBManager.class) {
                    manager = new DownloadDBManager();
                }
            }
        }
        return manager;
    }

    private DownloadDBManager() {
        helper = new DaoMaster.DevOpenHelper(MainApplication.getAppContext(), DB_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void insertInfo(DownloadInfoEntity downloadInfoEntity) {
        daoSession.getDownloadInfoEntityDao().insertOrReplace(downloadInfoEntity);
    }

    public void updateInfo(DownloadInfoEntity downloadInfoEntity) {
        daoSession.getDownloadInfoEntityDao().insertOrReplace(downloadInfoEntity);
    }

    public void deleteInfo(DownloadInfoEntity downloadInfoEntity) {
        daoSession.getDownloadInfoEntityDao().delete(downloadInfoEntity);
    }

    //拿到下载实体
    public DownloadInfoEntity getDownloadEntity(String downloadId) {
        return daoSession.getDownloadInfoEntityDao().queryBuilder().where(DownloadInfoEntityDao.Properties.DownloadId.eq(downloadId)).list().get(0);
    }

    //拿到下载完的了
    public List<DownloadInfoEntity> getFinishedSongList() {
        return daoSession.getDownloadInfoEntityDao().queryBuilder().where(DownloadInfoEntityDao.Properties.Status.eq(DownloadStatusConstants.FINISHED)).list();
    }

    //拿到下载中的
    public List<DownloadInfoEntity> getLoadingSongList() {
        QueryBuilder<DownloadInfoEntity> queryBuilder = daoSession.getDownloadInfoEntityDao().queryBuilder();
        queryBuilder.whereOr(DownloadInfoEntityDao.Properties.Status.eq(DownloadStatusConstants.DOWNLOADING)
                , DownloadInfoEntityDao.Properties.Status.eq(DownloadStatusConstants.PAUSED));
        return queryBuilder.list();

    }
}
