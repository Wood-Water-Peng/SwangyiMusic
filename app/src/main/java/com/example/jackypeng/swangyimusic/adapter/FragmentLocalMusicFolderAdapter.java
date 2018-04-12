package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.LocalAlbumDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalFolderDetailInfo;
import com.example.jackypeng.swangyimusic.ui.activity.MusicListActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class FragmentLocalMusicFolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalFolderDetailInfo> localFolderDetailInfos = new ArrayList<>();
    private Context mContext;

    public FragmentLocalMusicFolderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalMusicFolderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_music_folder_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final LocalFolderDetailInfo detailInfo = localFolderDetailInfos.get(position);
        final LocalMusicFolderHolder musicFolderHolder = (LocalMusicFolderHolder) holder;
        musicFolderHolder.icon.setImageResource(R.mipmap.list_icn_folder);
        musicFolderHolder.tv_folder_name.setText(detailInfo.folder_name);
        musicFolderHolder.tv_song_count_path.setText(detailInfo.folder_count + "首" + " " + detailInfo.folder_path);
        musicFolderHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MusicListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", MusicListActivity.TYPE_FOLDER);
                bundle.putParcelable("folder", detailInfo);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    public void setData(List<LocalFolderDetailInfo> data) {
        if (data != null) {
            localFolderDetailInfos = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return localFolderDetailInfos.size();
    }

    private static class LocalMusicFolderHolder extends RecyclerView.ViewHolder {
        ImageView iv_folder_detail;
        ImageView icon;
        TextView tv_folder_name;
        TextView tv_song_count_path;

        public LocalMusicFolderHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.item_local_music_folder_item_icon);
            iv_folder_detail = (ImageView) itemView.findViewById(R.id.item_local_music_folder_item_folder_detail);
            tv_folder_name = (TextView) itemView.findViewById(R.id.item_local_music_folder_item_folder_name);
            tv_song_count_path = (TextView) itemView.findViewById(R.id.item_local_music_folder_item_song_count_path);
        }
    }
}
