package com.example.jackypeng.swangyimusic.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by jackypeng on 2017/12/28.
 */

public class NotiUtil {

    private static final String TAG = "NotiUtil";
    private static NotiUtil instance;
    private NotificationCompat.Builder builder;
    public static final int STATUS_PLAYING = 1;   //播放中
    public static final int STATUS_PAUSED = 2;   //暂停中
    private RemoteViews remoteViews;


    public static NotiUtil getInstance() {
        synchronized (NotiUtil.class) {
            if (instance == null) {
                instance = new NotiUtil();
            }
        }
        return instance;
    }

    public void showNotification(final Context context, AlbumListItemTrack musicTrack) {
        builder = new NotificationCompat.Builder(context);
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notif);
        if (isDarkNotificationTheme()) {
            remoteViews.setImageViewResource(R.id.custom_notification_play_next, R.mipmap.play_btn_next);
            remoteViews.setImageViewResource(R.id.custom_notification_play_pre, R.mipmap.play_btn_prev);
            remoteViews.setImageViewResource(R.id.custom_notification_play, R.mipmap.play_rdi_btn_play);
        } else {
            remoteViews.setImageViewResource(R.id.custom_notification_play_next, R.mipmap.playbar_btn_next);
//            remoteViews.setImageViewResource(R.id.custom_notification_play_pre, R.mipmap.playbar_btn_prev);
            remoteViews.setImageViewResource(R.id.custom_notification_play, R.mipmap.playbar_btn_play);
        }

        PendingIntent broadcast_start_activity = PendingIntent.getBroadcast(context, 0, new Intent(BroadcastConstants.START_PLAY_DETAIL_ACTIVITY), 0);
        remoteViews.setOnClickPendingIntent(R.id.custom_notif_content, broadcast_start_activity);
        PendingIntent broadcast_play_pre = PendingIntent.getBroadcast(context, 0, new Intent(BroadcastConstants.PLAY_NEXT_MUSIC), 0);
        remoteViews.setOnClickPendingIntent(R.id.custom_notification_play_pre, broadcast_play_pre);
        PendingIntent broadcast_play_next = PendingIntent.getBroadcast(context, 0, new Intent(BroadcastConstants.PLAY_PRE_MUSIC), 0);
        remoteViews.setOnClickPendingIntent(R.id.custom_notification_play_next, broadcast_play_next);
        remoteViews.setTextViewText(R.id.custom_notification_name, musicTrack.getSongName());
        remoteViews.setTextViewText(R.id.custom_notification_artist, musicTrack.getAuthor());
        builder.setCustomBigContentView(remoteViews);
        builder.setAutoCancel(false);
        builder.setOnlyAlertOnce(true);
        builder.setSmallIcon(R.mipmap.notif_small_icon);
        //设置点击事件
        //得到PendingIntent
        //设置监听
        new AsyncTask<String, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6000);//设置超时
                    conn.setDoInput(true);
                    conn.setUseCaches(false);//不缓存
                    conn.connect();
                    int code = conn.getResponseCode();
                    Bitmap bitmap = null;
                    if (code == 200) {
                        InputStream is = conn.getInputStream();//获得图片的数据流
                        bitmap = BitmapFactory.decodeStream(is);

                    }
                    return bitmap;
                } catch (Exception e) {
                    remoteViews.setImageViewResource(R.id.custom_notification_icon, R.mipmap.ic_launcher);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    remoteViews.setImageViewBitmap(R.id.custom_notification_icon, bitmap);
                } else {
                    remoteViews.setImageViewResource(R.id.custom_notification_icon, R.mipmap.ic_launcher);
                }
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1000, builder.build());
            }
        }.execute(musicTrack.getPic_big_url());

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1000, builder.build());

    }

    public void updateNotification(Context context, AlbumListItemTrack musicTrack, int status) {
        Log.i(TAG, "status:" + status);
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (status == STATUS_PLAYING) {
            if (isDarkNotificationTheme()) {
                Log.i(TAG, "Dark Theme");
                remoteViews.setImageViewResource(R.id.custom_notification_play, R.mipmap.play_rdi_btn_pause);
            } else {
                Log.i(TAG, "Light Theme");
                remoteViews.setImageViewResource(R.id.custom_notification_play, R.mipmap.playbar_btn_pause);
            }
            PendingIntent broadcast_pause = PendingIntent.getBroadcast(context, 0, new Intent(BroadcastConstants.PAUSE_CUR_MUSIC), 0);
            remoteViews.setOnClickPendingIntent(R.id.custom_notification_play, broadcast_pause);
        } else if (status == STATUS_PAUSED) {
            if (isDarkNotificationTheme()) {
                remoteViews.setImageViewResource(R.id.custom_notification_play, R.mipmap.play_rdi_btn_play);
            } else {
                remoteViews.setImageViewResource(R.id.custom_notification_play, R.mipmap.playbar_btn_play);
            }
            PendingIntent broadcast_play = PendingIntent.getBroadcast(context, 0, new Intent(BroadcastConstants.PLAY_CUR_MUSIC), 0);
            remoteViews.setOnClickPendingIntent(R.id.custom_notification_play, broadcast_play);
        }
        builder.setCustomBigContentView(remoteViews);
        nm.notify(1000, builder.build());
    }

    private static boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor = baseColor | 0xff000000;
        int simpleColor = color | 0xff000000;
        int baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor);
        int baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor);
        int baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor);
        double value = Math.sqrt(baseRed * baseRed + baseGreen * baseGreen + baseBlue * baseBlue);
        if (value < 180.0) {
            return true;
        }
        return false;
    }

    public static boolean isDarkNotificationTheme() {
        return !isSimilarColor(Color.BLACK, getNotificationColor(MainApplication.getAppContext()));
    }

    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        int layoutId = notification.contentView.getLayoutId();
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title) != null) {
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static int findColor(ViewGroup viewGroupSource) {
        int color = Color.TRANSPARENT;
        LinkedList<ViewGroup> viewGroups = new LinkedList<>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size() > 0) {
            ViewGroup viewGroup1 = viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                } else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor() != -1) {
                        color = ((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }

}
