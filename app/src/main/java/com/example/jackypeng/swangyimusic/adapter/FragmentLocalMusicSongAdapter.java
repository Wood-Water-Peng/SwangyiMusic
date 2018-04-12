package com.example.jackypeng.swangyimusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumSongItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicMoreFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.PlayingQueueFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class FragmentLocalMusicSongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalMusicDetailInfo> localMusicDetailInfoList = new ArrayList<>();
    private Context mContext;

    public FragmentLocalMusicSongAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalMusicSongHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_music_song_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final LocalMusicDetailInfo detailInfo = localMusicDetailInfoList.get(position);
        LocalMusicSongHolder musicSongHolder = (LocalMusicSongHolder) holder;
        musicSongHolder.tv_song_name.setText(detailInfo.musicName);
        musicSongHolder.tv_artist_name.setText(detailInfo.artist);
        musicSongHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 播放本地音乐
                 * 1.判断该列表是否在服务中已经存在
                 */
                try {
                    //将专辑中的歌曲列表传递过去,需要一个唯一值来作为该数组的key
                    //将专辑中的歌曲列表传递过去
                    String[] musicIds = new String[localMusicDetailInfoList.size()];
                    HashMap<String, AlbumListItemTrack> musicMap = new HashMap<>();
                    for (int i = 0; i < localMusicDetailInfoList.size(); i++) {
                        LocalMusicDetailInfo bean = localMusicDetailInfoList.get(i);
                        AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                        itemTrack.setSongId(bean.songId);
                        itemTrack.setSongName(bean.musicName);
                        itemTrack.setAuthor(bean.artist);
                        itemTrack.setAlbum_id(bean.albumName);
                        itemTrack.setLocalPath(bean.data);
                        itemTrack.setLrc(bean.lrc);
                        itemTrack.setLocal(true);
                        musicIds[i] = bean.songId;
                        musicMap.put(bean.songId, itemTrack);
                    }
                    MusicPlayer.getInstance().playAll(musicIds, musicMap, holder.getAdapterPosition());

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        musicSongHolder.iv_song_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalMusicMoreFragment localMusicMoreFragment = LocalMusicMoreFragment.newMusicDetailInstance(detailInfo);
                localMusicMoreFragment.setCancelable(true);
                //设置fragment高度 、宽度
                if (mContext instanceof Activity) {
                    localMusicMoreFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "playing_queue_fragment");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return localMusicDetailInfoList.size();
    }

    public void setData(List<LocalMusicDetailInfo> localMusicInfoList) {
        if (localMusicInfoList != null) {
            localMusicDetailInfoList = localMusicInfoList;
            notifyDataSetChanged();
        }
    }

    static class LocalMusicSongHolder extends RecyclerView.ViewHolder {
        ImageView iv_play_status;
        TextView tv_song_name;
        TextView tv_artist_name;
        ImageView iv_song_detail;

        public LocalMusicSongHolder(View itemView) {
            super(itemView);
            iv_play_status = (ImageView) itemView.findViewById(R.id.item_local_music_song_item_play_state);
            iv_song_detail = (ImageView) itemView.findViewById(R.id.item_local_music_song_item_button_right);
            tv_song_name = (TextView) itemView.findViewById(R.id.item_local_music_song_item_song_name);
            tv_artist_name = (TextView) itemView.findViewById(R.id.item_local_music_song_item_author_name);
        }
    }
}
