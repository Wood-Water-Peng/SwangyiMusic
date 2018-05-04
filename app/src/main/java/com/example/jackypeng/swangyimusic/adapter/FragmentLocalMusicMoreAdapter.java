package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.ui.fragment.LocalMusicMoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class FragmentLocalMusicMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<LocalMusicMoreFragment.FlowItem> mItemList = new ArrayList<>();
    private T songDetail;

    public FragmentLocalMusicMoreAdapter(Context context, List<LocalMusicMoreFragment.FlowItem> itemList, T songDetail) {
        this.mContext = context;
        this.mItemList = itemList;
        this.songDetail = songDetail;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_music_more_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        LocalMusicMoreFragment.FlowItem flowItem = mItemList.get(position);
        itemHolder.iv_icon.setImageResource(flowItem.getAvatar());
        itemHolder.tv_name.setText(flowItem.getTitle());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 2:    //下载操作
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_name;

        public ItemHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.fragment_music_more_item_icon);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_music_more_item_name);
        }
    }

}
