package com.example.jackypeng.swangyimusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.NetworkMsgConstants;
import com.example.jackypeng.swangyimusic.network.NetworkStatusInfo;
import com.example.jackypeng.swangyimusic.rx.bean.PlayListMusicMoreFragmentBean;
import com.example.jackypeng.swangyimusic.ui.fragment.DownloadMusicPlayListFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.PlayListMusicMoreFragment;
import com.example.jackypeng.swangyimusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class FragmentMusicMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<PlayListMusicMoreFragment.FlowItem> mItemList = new ArrayList<>();
    private PlayListMusicMoreFragmentBean songDetail;
    private static final String TAG = "FragmentMusicMoreAdapter";

    public FragmentMusicMoreAdapter(Context context, List<PlayListMusicMoreFragment.FlowItem> itemList, PlayListMusicMoreFragmentBean songDetail) {
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
        PlayListMusicMoreFragment.FlowItem flowItem = mItemList.get(position);
        itemHolder.iv_icon.setImageResource(flowItem.getAvatar());
        itemHolder.tv_name.setText(flowItem.getTitle());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 2:    //下载操作，将该DialogFragment取消
                        Intent intent = new Intent(BroadcastConstants.HIDE_MUSIC_MORE_FRAGMENT);
                        mContext.sendBroadcast(intent);
                        tryDownloadSong();
                        break;
                }
            }
        });
    }

    private void tryDownloadSong() {
        Log.i(TAG, "network_status:" + NetworkStatusInfo.getInstance().getStatus());
        switch (NetworkStatusInfo.getInstance().getStatus()) {
            case NetworkMsgConstants.WIFI_MOBILE:
            case NetworkMsgConstants.WIFI_NO_MOBILE:
                //wifi可用
                Intent intent = new Intent(BroadcastConstants.SHOW_MUSIC_MORE_DOWNLOAD_TO_PLAYLIST_FRAGMENT);
                Bundle bundle = new Bundle();
                bundle.putParcelable("songDetail", songDetail);
                intent.putExtras(bundle);
                mContext.sendBroadcast(intent);
                break;
            case NetworkMsgConstants.NO_WIFI_MOBILE:
                //移动网络可用
                break;
            case NetworkMsgConstants.NETWORK_DISCONNECT:
                //网络不可用
                ToastUtil.getInstance().toast("网络不可用");
                break;

        }
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
