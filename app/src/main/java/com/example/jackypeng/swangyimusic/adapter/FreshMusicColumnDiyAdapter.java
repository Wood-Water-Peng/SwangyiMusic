package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.DiyResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.DiyResultItemBean;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class FreshMusicColumnDiyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DiyResultBean diyResultBean;
    private Context mContext;

    public FreshMusicColumnDiyAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreshMusicColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_column_item_holder, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (diyResultBean == null) return;

        List<DiyResultItemBean> list = diyResultBean.getResult();
        FreshMusicColumnHolder columnHolder = (FreshMusicColumnHolder) holder;
        columnHolder.album_name.setText(list.get(position).getTitle());
        Glide.with(mContext).load(list.get(position).getPic()).into(columnHolder.icon);
    }

    @Override
    public int getItemCount() {
        if (diyResultBean != null) {
            return diyResultBean.getResult().size();
        } else {
            return 0;
        }
    }

    public void setData(DiyResultBean diyResultBean) {
        this.diyResultBean = diyResultBean;
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
