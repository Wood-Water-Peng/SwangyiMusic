package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.ui.activity.AlbumDetailActivity;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class RecommendMVListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecommendMVResult recommendMVResult;
    private Context mContext;

    public RecommendMVListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendMusicListColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_music_list_column_item_holder, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (recommendMVResult == null) return;

        List<RecommendMVResult.RecommendMVItem> recommendMVItems = recommendMVResult.getResult();
        RecommendMVResult.RecommendMVItem item = recommendMVItems.get(position);
        RecommendMusicListColumnHolder columnHolder = (RecommendMusicListColumnHolder) holder;
//        columnHolder.album_name.setText(item.getName());
        Glide.with(mContext).load(item.getPicUrl()).into(columnHolder.icon);

        columnHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetailActivity.class);
                Bundle bundle = new Bundle();
//                bundle.putString("albumid", String.valueOf(item.getType_id()));
//                bundle.putString("albumart", String.valueOf(item.getPic()));
//                bundle.putString("albumname", String.valueOf(item.getTitle()));
//                bundle.putString("artistname", String.valueOf(item.getDesc()));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recommendMVResult != null) {
            return recommendMVResult.getResult().size();
        } else {
            return 0;
        }
    }

    public void setData(RecommendMVResult result) {
        if (result != null) {
            this.recommendMVResult = result;
            notifyDataSetChanged();
        }
    }

    static class RecommendMusicListColumnHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView album_name;
        TextView artist_name;

        public RecommendMusicListColumnHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_fresh_music_column_item_icon);
            album_name = (TextView) itemView.findViewById(R.id.item_fresh_music_column_item_icon_album_name);
            artist_name = (TextView) itemView.findViewById(R.id.item_fresh_music_column_item_icon_artist_name);
        }
    }
}
