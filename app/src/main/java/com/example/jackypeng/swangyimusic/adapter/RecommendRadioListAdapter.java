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
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.ui.activity.AlbumDetailActivity;
import com.example.jackypeng.swangyimusic.ui.activity.RadioDetailActivity;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class RecommendRadioListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecommendRadioResult recommendRadioResult;
    private Context mContext;

    public RecommendRadioListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendRadioListColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_radio_list_column_item_holder, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (recommendRadioResult == null) return;

        List<RecommendRadioResult.RecommendListRadioItem> listRadioItems = recommendRadioResult.getPrograms();
        final RecommendRadioResult.RecommendListRadioItem item = listRadioItems.get(position);
        RecommendRadioListColumnHolder columnHolder = (RecommendRadioListColumnHolder) holder;
        columnHolder.album_name.setText(item.getName());
        columnHolder.artist_name.setText(item.getListenerCount());
        Glide.with(mContext).load(item.getRadio().getPicUrl()).into(columnHolder.icon);

        columnHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RadioDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("rid",item.getRadio().getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recommendRadioResult != null) {
            return recommendRadioResult.getPrograms().size();
        } else {
            return 0;
        }
    }

    public void setData(RecommendRadioResult result) {
        if (result != null) {
            this.recommendRadioResult = result;
            notifyDataSetChanged();
        }
    }

    static class RecommendRadioListColumnHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView album_name;
        TextView artist_name;

        public RecommendRadioListColumnHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_recommend_radio_column_item_icon);
            album_name = (TextView) itemView.findViewById(R.id.item_recommend_radio_column_item_icon_album_name);
            artist_name = (TextView) itemView.findViewById(R.id.item_recommend_radio_column_item_icon_artist_name);
        }
    }
}
