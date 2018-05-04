package com.example.jackypeng.swangyimusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumSongItemBean;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;
import com.example.jackypeng.swangyimusic.ui.fragment.PlayListMusicMoreFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/8.
 * 专辑详情里的播放列表
 */

public class AlbumDetailSongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0xff01;  //头部
    public static final int TYPE_COMMON_ITEM = 0xff02;  //普通播放条目
    private static final String TAG = "AlbumDetailSongListAdapter";
    private List<AlbumSongItemBean> songList;
    private Context mContext;

    public AlbumDetailSongListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new ListHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail_song_list_header, null));
        } else if (viewType == TYPE_COMMON_ITEM) {
            return new ListItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail_song_list_item, null));
        }
        return null;
    }

    public void setData(List<AlbumSongItemBean> data) {
        this.songList = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (songList == null) return;
        if (holder instanceof ListItem) {
            final AlbumSongItemBean itemBean = songList.get(position - 1);
            final ListItem listItem = (ListItem) holder;
            listItem.songName.setText(itemBean.getTitle());
            listItem.songAuthor.setText(itemBean.getAuthor());
            listItem.number.setText(String.valueOf(position));
            listItem.iv_show_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    PlayListMusicMoreFragment playListMusicMoreFragment = PlayListMusicMoreFragment.newInstance(itemBean);
//                    if (mContext instanceof Activity) {
//                        playListMusicMoreFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "playListMusicMoreFragment");
//                    }
                }
            });

            listItem.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //判断歌曲的状态,仅仅响应歌曲没有播放的状态
                        int songStatus = MusicPlayer.getInstance().getSongStatus(itemBean.getSong_id());
                        if (songStatus != PlayingMusicStatusConstants.INIT) {
                            return;
                        }
                        String[] musicIds = new String[songList.size()];
                        HashMap<String, AlbumListItemTrack> musicMap = new HashMap<>();
                        for (int i = 0; i < songList.size(); i++) {
                            AlbumSongItemBean bean = songList.get(i);
                            AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                            itemTrack.setSongId(bean.getSong_id());
                            itemTrack.setSongName(bean.getTitle());
                            itemTrack.setAuthor(bean.getAuthor());
                            itemTrack.setPic_big_url(bean.getPic_big());
                            itemTrack.setPic_small_url(bean.getPic_small());
                            itemTrack.setAlbum_id(bean.getAlbum_id());
                            itemTrack.setLrc(bean.getLrclink());
                            musicIds[i] = bean.getSong_id();
                            musicMap.put(bean.getSong_id(), itemTrack);
                        }
                        MusicPlayer.getInstance().playAll(musicIds, musicMap, holder.getAdapterPosition());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (holder instanceof ListHeader) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_COMMON_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (songList == null) {
            return 1;
        } else {
            return songList.size() + 1;
        }
    }

    static class ListHeader extends RecyclerView.ViewHolder {
        public ListHeader(View itemView) {
            super(itemView);
        }
    }

    static class ListItem extends RecyclerView.ViewHolder {
        TextView songName;
        TextView songAuthor;
        TextView number;
        ImageView iv_show_detail;

        public ListItem(View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.album_detail_song_list_item_name);
            songAuthor = (TextView) itemView.findViewById(R.id.album_detail_song_list_item_author);
            number = (TextView) itemView.findViewById(R.id.album_detail_song_list_item_number);
            iv_show_detail = (ImageView) itemView.findViewById(R.id.album_detail_song_list_item_popup_menu);
        }
    }
}
