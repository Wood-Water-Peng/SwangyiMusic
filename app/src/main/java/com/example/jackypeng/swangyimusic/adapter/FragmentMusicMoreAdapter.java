package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.download_music.DownloadInfo;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumSongItemBean;
import com.example.jackypeng.swangyimusic.ui.fragment.MusicMoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/19.
 */

public class FragmentMusicMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<MusicMoreFragment.FlowItem> mItemList = new ArrayList<>();
    private AlbumSongItemBean songDetail;

    public FragmentMusicMoreAdapter(Context context, List<MusicMoreFragment.FlowItem> itemList, AlbumSongItemBean songDetail) {
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
        MusicMoreFragment.FlowItem flowItem = mItemList.get(position);
        itemHolder.iv_icon.setImageResource(flowItem.getAvatar());
        itemHolder.tv_name.setText(flowItem.getTitle());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 2:    //下载操作
                        tryDownloadSong();
                        break;
                }
            }
        });
    }

    private void tryDownloadSong() {
        new AlertDialog.Builder(mContext).setTitle("要下载音乐吗").
                setPositiveButton(mContext.getString(R.string.sure), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadManager.getInstance().startDownload(new DownloadInfo(songDetail.getSong_id(), songDetail.getAuthor(), songDetail.getTitle(), songDetail.getAlbum_id(), songDetail.getLrclink(), songDetail.getPic_small(), songDetail.getPic_big()));
                        dialog.dismiss();
                    }
                }).
                setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

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
