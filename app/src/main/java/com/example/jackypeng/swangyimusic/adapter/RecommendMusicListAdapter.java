package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.Mix_1_ItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.Mix_1_ResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.ui.activity.AlbumDetailActivity;
import com.example.jackypeng.swangyimusic.ui.activity.PlayingListDetailActivity;

import java.util.List;

import static android.R.id.list;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class RecommendMusicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecommendListResult recommendListResult;
    private Context mContext;

    public RecommendMusicListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendMusicListColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_music_list_column_item_holder, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (recommendListResult == null) return;

        List<RecommendListResult.RecommendListItem> recommendListItems = recommendListResult.getResult();
        final RecommendListResult.RecommendListItem item = recommendListItems.get(position);
        RecommendMusicListColumnHolder columnHolder = (RecommendMusicListColumnHolder) holder;
        columnHolder.album_name.setText(item.getName());
        columnHolder.tv_listener_count.setText(item.getPlayCount());
        Glide.with(mContext).load(item.getPicUrl()).into(columnHolder.icon);

        columnHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayingListDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(item.getId()));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recommendListResult != null) {
            return recommendListResult.getResult().size();
        } else {
            return 0;
        }
    }

    public void setData(RecommendListResult result) {
        if (result != null) {
            this.recommendListResult = result;
            notifyDataSetChanged();
        }
    }

    static class RecommendMusicListColumnHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView album_name;
        TextView artist_name;
        TextView tv_listener_count;

        public RecommendMusicListColumnHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_recommend_music_column_item_icon);
            album_name = (TextView) itemView.findViewById(R.id.item_recommend_music_column_item_icon_album_name);
            artist_name = (TextView) itemView.findViewById(R.id.item_recommend_music_column_item_icon_artist_name);
            tv_listener_count = (TextView) itemView.findViewById(R.id.item_recommend_music_column_item_tv_listener_count);
        }
    }
}
