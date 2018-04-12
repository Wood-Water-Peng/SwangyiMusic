package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/14.
 */

public class PlayingQueueFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AlbumListItemTrack> listItemTracks = new ArrayList<>();
    private AlbumListItemTrack playingSongTrack;

    public PlayingQueueFragmentAdapter(Context mContext) {
        this.mContext = mContext;
        updateDataSet();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playing_queue_fragment_adapter, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AlbumListItemTrack itemTrack = listItemTracks.get(position);
        ItemHolder itemHolder = (ItemHolder) holder;
        if (itemTrack.getSongId().equals(playingSongTrack.getSongId())) {
            itemHolder.iv_playing_status.setImageResource(R.mipmap.song_play_icon);
            itemHolder.iv_playing_status.setVisibility(View.VISIBLE);
        } else {
            itemHolder.iv_playing_status.setVisibility(View.GONE);
        }
        itemHolder.tv_song_name.setText(itemTrack.getSongName());
        itemHolder.tv_song_author.setText(itemTrack.getAuthor());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int songStatus = 0;
                try {
                    songStatus = MusicPlayer.getInstance().getSongStatus(itemTrack.getSongId());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (songStatus != PlayingMusicStatusConstants.INIT) {
                    return;
                }
                try {
                    MusicPlayer.getInstance().playInQueue(position);
                    playingSongTrack = MusicPlayer.getInstance().getPlayingSongTrack();
                    notifyDataSetChanged();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateDataSet() {
        try {
            listItemTracks = MusicPlayer.getInstance().getPlayingListTrack();
            playingSongTrack = MusicPlayer.getInstance().getPlayingSongTrack();
            notifyDataSetChanged();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listItemTracks.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        ImageView iv_playing_status;
        TextView tv_song_name;
        TextView tv_song_author;

        public ItemHolder(View itemView) {
            super(itemView);
            iv_playing_status = (ImageView) itemView.findViewById(R.id.item_playing_list_state);
            tv_song_name = (TextView) itemView.findViewById(R.id.item_playing_list_name);
            tv_song_author = (TextView) itemView.findViewById(R.id.item_playing_list_artist);
        }
    }
}
