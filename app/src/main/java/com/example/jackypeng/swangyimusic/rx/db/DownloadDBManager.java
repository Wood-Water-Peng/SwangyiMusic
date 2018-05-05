package com.example.jackypeng.swangyimusic.rx.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants;
import com.example.jackypeng.swangyimusic.download_music.DaoMaster;
import com.example.jackypeng.swangyimusic.download_music.DaoSession;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrackDao;
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

    public void insertInfo(MusicDownloadTrack musicDownloadTrack) {
        daoSession.getMusicDownloadTrackDao().insertOrReplace(musicDownloadTrack);
    }

    public void insertListTrack(List<MusicDownloadTrack> tracks) {
        daoSession.getMusicDownloadTrackDao().insertInTx(tracks);
    }

    public void updateInfo(MusicDownloadTrack musicDownloadTrack) {
        daoSession.getMusicDownloadTrackDao().update(musicDownloadTrack);
    }

    public void deleteInfo(MusicDownloadTrack musicDownloadTrack) {
        daoSession.getMusicDownloadTrackDao().delete(musicDownloadTrack);
    }

    //根据歌曲id拿到下载实体
    public MusicDownloadTrack getDownloadEntity(String musicId) {
        List<MusicDownloadTrack> list = daoSession.getMusicDownloadTrackDao().queryBuilder().where(MusicDownloadTrackDao.Properties.MusicId.eq(musicId)).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public void deleteInfo(String musicId) {
        daoSession.getMusicDownloadTrackDao().delete(getDownloadEntity(musicId));
    }

    //拿到下载完的了
    public List<MusicDownloadTrack> getFinishedSongList() {
        return daoSession.getMusicDownloadTrackDao().queryBuilder().where(MusicDownloadTrackDao.Properties.Status.eq(DownloadStatusConstants.FINISHED)).list();
    }

    //拿到所有的下载任务
    public List<MusicDownloadTrack> getLoadingSongList() {
        QueryBuilder<MusicDownloadTrack> queryBuilder = daoSession.getMusicDownloadTrackDao().queryBuilder();
        queryBuilder.whereOr(MusicDownloadTrackDao.Properties.Status.eq(DownloadStatusConstants.DOWNLOADING)
                , MusicDownloadTrackDao.Properties.Status.eq(DownloadStatusConstants.PAUSED), MusicDownloadTrackDao.Properties.Status.eq(DownloadStatusConstants.WAITING));
        return queryBuilder.list();

    }

    public void deleteDownloadindList() {
        daoSession.getMusicDownloadTrackDao().deleteInTx(getLoadingSongList());
    }
}
