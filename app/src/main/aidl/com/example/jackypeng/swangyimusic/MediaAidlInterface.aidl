// MediaAidlInterface.aidl
package com.example.jackypeng.swangyimusic;
// Declare any non-default types here with import statements
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;

interface MediaAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void playSong(String albumId,int index);
    void playSongInQueue(int index);  //播放队列中的某一只音乐
    void playAllSong(in String[] ids,in Map map,int index);  //播放队列
    void resumeSong();
    void pauseSong();

    //void playFromList(String albumId,int index,in List<AlbumListItemTrack> songList);//从列表中播放歌曲

    String[] getQueueIds();  //拿到播放列表中的所有id
    String getSongLrc();
    String getAlbumCover();
    String getSongAuthor();
    String getSongTitle();
    String getSongId();
    boolean isPlayListExist(String albumId);  //判断播放列表是否已在服务中存在
    int getSongStatus(String songId);  //拿到当前歌曲在服务中的的状态
    int getPlayingStatus();     //拿到播放的状态    1.播放中 2.暂停中 3.缓冲中

    int getCurPlayingIndex();  //当前播放的音乐在列表中的位置
    String getLatestSongAuthor();
    String getLatestSongName();
    String getLatestSongSmallPic();
    String getLatestSongBigPic();
    String getLatestSongLrc();
    String getLatestPlayingSongId();
    List<AlbumListItemTrack>  getPlayingSongList();
    AlbumListItemTrack  getPlayingSongInfo();
}
