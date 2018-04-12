package com.example.jackypeng.swangyimusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.LocalAlbumDetailInfo;
import com.example.jackypeng.swangyimusic.ui.activity.MusicListActivity;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicMoreFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class FragmentLocalMusicAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LocalAlbumDetailInfo> localAlbumDetailInfos = new ArrayList<>();
    public static final String TAG = "FragmentLocalMusicArtistAdapter";
    private Context mContext;

    public FragmentLocalMusicAlbumAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalMusicAlbumHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_music_album_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final LocalAlbumDetailInfo detailInfo = localAlbumDetailInfos.get(position);
        final LocalMusicAlbumHolder musicSongHolder = (LocalMusicAlbumHolder) holder;
        musicSongHolder.tv_artist_name.setText(detailInfo.album_name);
        musicSongHolder.tv_song_count.setText(detailInfo.number_of_songs + "首");
        musicSongHolder.icon.setImageURI(Uri.parse(detailInfo.album_art));
        musicSongHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MusicListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", MusicListActivity.TYPE_ALBUM);
                bundle.putParcelable("album", detailInfo);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        musicSongHolder.iv_artist_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalMusicMoreFragment localMusicMoreFragment = LocalMusicMoreFragment.newAlbumDetailInstance(detailInfo);
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
        return localAlbumDetailInfos.size();
    }

    public void setData(List<LocalAlbumDetailInfo> data) {
        if (data != null) {
            localAlbumDetailInfos = data;
            notifyDataSetChanged();
        }
    }

    private static class LocalMusicAlbumHolder extends RecyclerView.ViewHolder {
        ImageView iv_artist_detail;
        TextView tv_artist_name;
        TextView tv_song_count;
        SimpleDraweeView icon;

        public LocalMusicAlbumHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.item_local_music_album_item_icon);
            iv_artist_detail = (ImageView) itemView.findViewById(R.id.item_local_music_album_item_album_detail);
            tv_artist_name = (TextView) itemView.findViewById(R.id.item_local_music_album_item_album_name);
            tv_song_count = (TextView) itemView.findViewById(R.id.item_local_music_album_item_song_count);
        }
    }
}
