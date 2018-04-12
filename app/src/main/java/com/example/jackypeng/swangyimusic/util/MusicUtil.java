package com.example.jackypeng.swangyimusic.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.rx.bean.LocalAlbumDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalFolderDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/15.
 */

public class MusicUtil {

    public static final int FILTER_SIZE = 1 * 1024 * 1024;// 1MB
    public static final int FILTER_DURATION = 1 * 60 * 1000;// 1分钟

    //查询歌曲的入口---可根据专辑，作者名还有文件夹
    public static final int START_FROM_ARTIST = 1;
    public static final int START_FROM_ALBUM = 2;
    public static final int START_FROM_LOCAL = 3;
    public static final int START_FROM_FOLDER = 4;
    public static final int START_FROM_FAVORITE = 5;


    //查询歌曲信息的行
    private static String[] proj_music = new String[]{
            MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE};

    //查询歌手信息的行
    private static String[] proj_artist = new String[]{
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
            MediaStore.Audio.Artists._ID};
    //查询专辑的行
    private static String[] proj_album = new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.Albums.ARTIST};

    //查询文件夹
    private static String[] proj_folder = new String[]{MediaStore.Files.FileColumns.DATA};

    public static String makeTimeString(int milliSecs) {
        StringBuffer sb = new StringBuffer();
        long m = milliSecs / (60 * 1000);
        sb.append(m < 10 ? "0" + m : m);
        sb.append(":");
        long s = (milliSecs % (60 * 1000)) / 1000;
        sb.append(s < 10 ? "0" + s : s);
        return sb.toString();
    }

    //获取本地所有的音乐
    public static List<LocalMusicDetailInfo> getAllMusics() {
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = MainApplication.getAppContext().getContentResolver();
        Cursor cursor = cr.query(uri, proj_music, null, null, null);
        return getMusicWithCursor(cursor);
    }

    //查询所有的歌手
    public static List<LocalArtistDetailInfo> queryAllArtists() {
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        ContentResolver cr = MainApplication.getAppContext().getContentResolver();
        Cursor cursor = cr.query(uri, proj_artist, null, null, null);
        return getArtistWithCursor(cursor);
    }

    //查询所有的专辑
    public static List<LocalAlbumDetailInfo> queryAllAlbums() {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        ContentResolver cr = MainApplication.getAppContext().getContentResolver();
        Cursor cursor = cr.query(uri, proj_album, null, null, null);
        return getAlbumsWithCursor(cursor);
    }

    //查询所有的文件夹
    public static List<LocalFolderDetailInfo> queryAllFolders() {
        Uri uri = MediaStore.Files.getContentUri("external");
        ContentResolver cr = MainApplication.getAppContext().getContentResolver();
        StringBuilder mSelection = new StringBuilder(MediaStore.Files.FileColumns.MEDIA_TYPE
                + " = " + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + " and " + "("
                + MediaStore.Files.FileColumns.DATA + " like'%.mp3' or " + MediaStore.Audio.Media.DATA
                + " like'%.wma')");
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        mSelection.append(" and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE);
        mSelection.append(" and " + MediaStore.Audio.Media.DURATION + " > " + FILTER_DURATION);
        mSelection.append(") group by ( " + MediaStore.Files.FileColumns.PARENT);
        Cursor cursor = cr.query(uri, proj_folder, mSelection.toString(), null, null);
        return getFolderWithCursor(cursor);
    }


    private static List<LocalAlbumDetailInfo> getAlbumsWithCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<LocalAlbumDetailInfo> albumDetailInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            LocalAlbumDetailInfo info = new LocalAlbumDetailInfo();
            info.album_name = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            info.album_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
            info.number_of_songs = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
            info.album_art = getAlbumArtUri(info.album_id) + "";
            info.album_artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST));
            albumDetailInfos.add(info);
        }
        cursor.close();
        return albumDetailInfos;
    }

    private static List<LocalFolderDetailInfo> getFolderWithCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<LocalFolderDetailInfo> folderDetailInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            LocalFolderDetailInfo info = new LocalFolderDetailInfo();
            String filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA));
            info.folder_path = filePath.substring(0, filePath.lastIndexOf(File.separator));
            info.folder_name = info.folder_path.substring(info.folder_path
                    .lastIndexOf(File.separator) + 1);
            folderDetailInfos.add(info);
        }
        cursor.close();
        return folderDetailInfos;
    }

    private static List<LocalArtistDetailInfo> getArtistWithCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<LocalArtistDetailInfo> artistList = new ArrayList<>();
        while (cursor.moveToNext()) {
            LocalArtistDetailInfo info = new LocalArtistDetailInfo();
            info.artist_name = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Artists.ARTIST));
            info.number_of_tracks = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
            info.artist_id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
            artistList.add(info);
        }
        cursor.close();
        return artistList;
    }


    private static List<LocalMusicDetailInfo> getMusicWithCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        List<LocalMusicDetailInfo> musicList = new ArrayList<>();
        while (cursor.moveToNext()) {
            LocalMusicDetailInfo music = new LocalMusicDetailInfo();
            music.songId = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media._ID));
            music.albumId = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            music.albumName = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM));
            music.albumData = getAlbumArtUri(music.albumId) + "";
            music.duration = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));
            music.musicName = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.TITLE));
            music.artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));
            music.artistId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
            String filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));
            music.data = filePath;
            music.folder = filePath.substring(0, filePath.lastIndexOf(File.separator));
            music.size = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));
            music.islocal = true;
            musicList.add(music);
        }
        cursor.close();
        return musicList;
    }

    public static List<LocalMusicDetailInfo> queryMusic(Context context, String id, int from) {

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = context.getContentResolver();

        StringBuilder select = new StringBuilder(" 1=1 and title != ''");
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        select.append(" and " + MediaStore.Audio.Media.SIZE + " > " + FILTER_SIZE);
        select.append(" and " + MediaStore.Audio.Media.DURATION + " > " + FILTER_DURATION);

        String selectionStatement = "is_music=1 AND title != ''";

        switch (from) {
            case START_FROM_LOCAL:
                List<LocalMusicDetailInfo> list3 = getMusicWithCursor(cr.query(uri, proj_music,
                        select.toString(), null,
                        null));
                return list3;
            case START_FROM_ARTIST:
                select.append(" and " + MediaStore.Audio.Media.ARTIST_ID + " = " + id);
                return getMusicWithCursor(cr.query(uri, proj_music, select.toString(), null,
                        null));
            case START_FROM_ALBUM:
                select.append(" and " + MediaStore.Audio.Media.ALBUM_ID + " = " + id);
                return getMusicWithCursor(cr.query(uri, proj_music,
                        select.toString(), null,
                        null));
            case START_FROM_FOLDER:
                List<LocalMusicDetailInfo> list1 = new ArrayList<>();
                List<LocalMusicDetailInfo> list = getMusicWithCursor(cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj_music,
                        select.toString(), null,
                        null));
                for (LocalMusicDetailInfo music : list) {
                    if (music.data.substring(0, music.data.lastIndexOf(File.separator)).equals(id)) {
                        list1.add(music);
                    }
                }
                return list1;
            default:
                return null;
        }

    }

    public static Uri getAlbumArtUri(String albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Long.valueOf(albumId));
    }
}
