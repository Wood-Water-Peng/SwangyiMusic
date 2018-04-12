package com.example.jackypeng.swangyimusic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class FragmentTotalDownloadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DownloadInfoEntity> items = new ArrayList<>();
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_HEADER == viewType) {
            return new HeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_finished_song_head, parent, false));
        } else if (TYPE_ITEM == viewType) {
            return new HeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_finished_song_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder) {
            HeadHolder headHolder = (HeadHolder) holder;
            headHolder.tv_song_number.setText("共" + String.valueOf(items.size()) + "首");
        } else if (holder instanceof CommonItemHolder) {
            CommonItemHolder commonItemHolder = (CommonItemHolder) holder;
            DownloadInfoEntity item = items.get(position);
            commonItemHolder.tv_song_name.setText(item.getSongName());
            commonItemHolder.tv_author_name.setText(item.getAuthorName());
        }
    }

    @Override
    public int getItemCount() {
        return 1 + items.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    static class HeadHolder extends RecyclerView.ViewHolder {

        TextView tv_song_number;

        HeadHolder(View itemView) {
            super(itemView);
            tv_song_number = (TextView) itemView.findViewById(R.id.fragment_total_download_item_header_play_all_number);
        }
    }

    static class CommonItemHolder extends RecyclerView.ViewHolder {
        ImageView iv_playing_state;
        TextView tv_song_name;
        TextView tv_author_name;
        ImageView iv_show_detail;

        CommonItemHolder(View itemView) {
            super(itemView);
            iv_playing_state = (ImageView) itemView.findViewById(R.id.fragment_total_download_item_common_play_state);
            tv_song_name = (TextView) itemView.findViewById(R.id.fragment_total_download_item_common_song_name);
            tv_author_name = (TextView) itemView.findViewById(R.id.fragment_total_download_item_common_author_name);
            iv_show_detail = (ImageView) itemView.findViewById(R.id.fragment_total_download_item_common_button_right);
        }
    }
}
