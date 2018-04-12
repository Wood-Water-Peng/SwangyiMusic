package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResultBean;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/8.
 * 专辑详情里的播放列表
 */

public class RadioDetailSongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0xff01;  //头部
    public static final int TYPE_COMMON_ITEM = 0xff02;  //普通播放条目
    private static final String TAG = "AlbumDetailSongListAdapter";
    private List<RadioDetailItemBean> songList;
    private RadioDetailResultBean radioDetailResultBean;
    private Context context;

    public RadioDetailSongListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new ListHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radio_detail_song_list_header, null));
        } else if (viewType == TYPE_COMMON_ITEM) {
            return new ListItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radio_detail_song_list_item, null));
        }
        return null;
    }


    public void setData(RadioDetailResultBean data) {
        this.radioDetailResultBean = data;
        this.songList = radioDetailResultBean.getLatest_song();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (songList == null) return;
        if (holder instanceof ListItem) {
            final RadioDetailItemBean itemBean = songList.get(position - 1);
            final ListItem listItem = (ListItem) holder;
            listItem.songName.setText(itemBean.getSong_name());
            listItem.number.setText(String.valueOf(position));
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
                            RadioDetailItemBean bean = songList.get(i);
                            AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                            itemTrack.setSongId(bean.getSong_id());
                            itemTrack.setPic_big_url(radioDetailResultBean.getAlbum_pic().get(0).getPic_url());
                            itemTrack.setPic_small_url(radioDetailResultBean.getAlbum_pic().get(1).getPic_url());
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
        TextView number;

        public ListItem(View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.radio_detail_song_list_item_name);
            number = (TextView) itemView.findViewById(R.id.radio_detail_song_list_item_number);
        }
    }
}
