package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.RadioItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioResultBean;
import com.example.jackypeng.swangyimusic.ui.activity.RadioDetailActivity;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class FreshMusicColumnRadioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RadioResultBean radioResultBean;
    private Context mContext;

    public FreshMusicColumnRadioAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreshMusicColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_column_item_holder, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (radioResultBean == null) return;

        List<RadioItemBean> list = radioResultBean.getResult();
        final RadioItemBean radioItemBean = list.get(position);
        FreshMusicColumnHolder columnHolder = (FreshMusicColumnHolder) holder;
        columnHolder.album_name.setText(radioItemBean.getTitle());
        Glide.with(mContext).load(radioItemBean.getPic()).into(columnHolder.icon);
        columnHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RadioDetailActivity.class);
                intent.putExtra("albumid", radioItemBean.getAlbum_id());
                intent.putExtra("albumart", radioItemBean.getPic());
                intent.putExtra("albumname", radioItemBean.getTitle());
                intent.putExtra("artistname", radioItemBean.getDesc());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (radioResultBean != null) {
            return radioResultBean.getResult().size();
        } else {
            return 0;
        }
    }

    public void setData(RadioResultBean radioResultBean) {
        if(radioResultBean!=null) {
            this.radioResultBean = radioResultBean;
            notifyDataSetChanged();
        }
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
