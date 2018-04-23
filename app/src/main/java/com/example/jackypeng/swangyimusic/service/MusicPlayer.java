package com.example.jackypeng.swangyimusic.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.MediaAidlInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jackypeng on 2018/3/8.
 */

public class MusicPlayer {
    private static final String TAG = "MusicPlayer";
    private static MusicPlayer sMusicPlayer;
    private MediaAidlInterface playerService;
    private Context mContext;
    private CountDownLatch mConnectionBinderPoolCountDownLatch;

    public static MusicPlayer getInstance() {
        if (sMusicPlayer == null) {
            synchronized (MusicPlayer.class) {
                if (sMusicPlayer == null) {
                    sMusicPlayer = new MusicPlayer(MainApplication.getAppContext());
                }
            }
        }
        return sMusicPlayer;
    }

    private MusicPlayer(Context context) {
        this.mContext = context;
        mConnectionBinderPoolCountDownLatch = new CountDownLatch(1);
        bindToService();
    }


    private void bindToService() {
        Intent intent = new Intent(mContext, MediaService.class);
        mContext.startService(intent);
        mContext.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                playerService = MediaAidlInterface.Stub.asInterface(service);
                mConnectionBinderPoolCountDownLatch.countDown();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                playerService = null;
            }
        }, Context.BIND_AUTO_CREATE);
        try {
            mConnectionBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void resumeSong() throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        playerService.resumeSong();

    }

    public void playInQueue(int index) throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        playerService.playSongInQueue(index);
    }

    public void playAll(String[] ids, HashMap<String, AlbumListItemTrack> map, int index) throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        String[] queueIdsOfService = playerService.getQueueIds();
        if (Arrays.equals(ids, queueIdsOfService)) {
            playerService.playSongInQueue(index);
        } else {
            playerService.playAllSong(ids, map, index);
        }
    }


    public int getSongStatus(String songId) throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        return playerService.getSongStatus(songId);
    }

    public AlbumListItemTrack getPlayingSongTrack() throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        return playerService.getPlayingSongInfo();
    }

    public List<AlbumListItemTrack> getPlayingListTrack() throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        return playerService.getPlayingSongList();
    }

    public void pauseSong() throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        playerService.pauseSong();
    }

    public void getPlayingSongLrc() throws RemoteException {
        if (playerService == null) {
            bindToService();
        }
        playerService.getLatestSongLrc();
    }
}
