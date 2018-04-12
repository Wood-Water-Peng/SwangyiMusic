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
import com.example.jackypeng.swangyimusic.rx.bean.Mix_1_ItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.Mix_1_ResultBean;
import com.example.jackypeng.swangyimusic.ui.activity.AlbumDetailActivity;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class FreshMusicColumnMix_1_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Mix_1_ResultBean mix_1_resultBean;
    private Context mContext;

    public FreshMusicColumnMix_1_Adapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreshMusicColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_column_item_holder, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mix_1_resultBean == null) return;

        List<Mix_1_ItemBean> list = mix_1_resultBean.getResult();
        final Mix_1_ItemBean mix_1_itemBean = list.get(position);
        FreshMusicColumnHolder columnHolder = (FreshMusicColumnHolder) holder;
        columnHolder.album_name.setText(mix_1_itemBean.getTitle());
        Glide.with(mContext).load(mix_1_itemBean.getPic()).into(columnHolder.icon);
        columnHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("albumid", String.valueOf(mix_1_itemBean.getType_id()));
                bundle.putString("albumart", String.valueOf(mix_1_itemBean.getPic()));
                bundle.putString("albumname", String.valueOf(mix_1_itemBean.getTitle()));
                bundle.putString("artistname", String.valueOf(mix_1_itemBean.getDesc()));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mix_1_resultBean != null) {
            return mix_1_resultBean.getResult().size();
        } else {
            return 0;
        }
    }

    public void setData(Mix_1_ResultBean mix_1_resultBean) {
        this.mix_1_resultBean = mix_1_resultBean;
        notifyDataSetChanged();
    }

    static class FreshMusicColumnHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView album_name;
        TextView artist_name;

        public FreshMusicColumnHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_fresh_music_column_item_icon);
            album_name = (TextView) itemView.findViewById(R.id.item_fresh_music_column_item_icon_album_name);
            artist_name = (TextView) itemView.findViewById(R.id.item_fresh_music_column_item_icon_artist_name);
        }
    }
}
