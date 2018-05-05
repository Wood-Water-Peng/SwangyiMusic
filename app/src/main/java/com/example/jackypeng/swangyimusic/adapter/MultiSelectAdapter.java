package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.download_music.DownloadInfo;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.ui.activity.MultiSelectActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jackypeng on 2018/4/28.
 * 歌单列表中多选界面
 */

public class MultiSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PlayingListDetailResult.PlayingListDetailTrack> itemBeanList = new ArrayList<>();
    private Context mContext;
    private SparseArray<DownloadInfo> selectedItems = new SparseArray<>();
    private List<DownloadInfo> selectedInfos = new ArrayList<>();
    private MultiSelectActivity.ItemSelectListener itemSelectedListener;

    public MultiSelectAdapter(Context mContext, MultiSelectActivity.ItemSelectListener listener) {
        this.mContext = mContext;
        itemSelectedListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MultiSelectHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_select_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MultiSelectHolder multiSelectHolder = (MultiSelectHolder) holder;
        final PlayingListDetailResult.PlayingListDetailTrack itemBean = itemBeanList.get(position);
        if (DownloadDBManager.getInstance().getDownloadEntity(itemBean.getId()) != null) {
            multiSelectHolder.iv_isDownloaded.setVisibility(View.VISIBLE);
        } else {
            multiSelectHolder.iv_isDownloaded.setVisibility(View.GONE);
        }
        multiSelectHolder.tv_musicName.setText(itemBean.getName());
        multiSelectHolder.tv_artistName.setText(itemBean.getArtists().getJSONObject(0).getString("name"));
        multiSelectHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = multiSelectHolder.cb_isSelected.isChecked();
                checked = !checked;
                multiSelectHolder.cb_isSelected.setChecked(checked);
                if (checked) {
                    DownloadInfo info = new DownloadInfo();
                    info.setSongId(itemBean.getId());
                    info.setSongName(itemBean.getName());
                    selectedItems.put(holder.getAdapterPosition(), info);
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onItemSelectedCount(selectedItems.size());
                    }
                } else {
                    selectedItems.remove(holder.getAdapterPosition());
                    if (itemSelectedListener != null) {
                        itemSelectedListener.onItemSelectedCount(selectedItems.size());
                    }
                }
                //通知MultiSelectedActivity
            }
        });

        if (selectedItems.get(position) != null) {
            multiSelectHolder.cb_isSelected.setChecked(true);
        } else {
            multiSelectHolder.cb_isSelected.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return itemBeanList.size();
    }

    public void setData(List<PlayingListDetailResult.PlayingListDetailTrack> tracks) {
        if (tracks != null && tracks.size() > 0) {
            itemBeanList = tracks;
            notifyDataSetChanged();
        }
    }

    public List<DownloadInfo> getSelectedItems() {
        selectedInfos.clear();
        for (int i = 0; i < selectedItems.size(); i++) {
            DownloadInfo valueAt = selectedItems.valueAt(i);
            if (valueAt != null) {
                selectedInfos.add(valueAt);
            }
        }
        return selectedInfos;
    }

    //选中
    public void selectAll() {
        for (int i = 0; i < getItemCount(); i++) {
            PlayingListDetailResult.PlayingListDetailTrack itemBean = itemBeanList.get(i);
            DownloadInfo info = new DownloadInfo();
            info.setSongId(itemBean.getId());
            info.setSongName(itemBean.getName());
            selectedItems.put(i, info);
        }
        itemSelectedListener.onItemSelectedCount(selectedItems.size());
        notifyDataSetChanged();
    }

    //取消全选
    public void unSelectAll() {
        selectedItems.clear();
        itemSelectedListener.onItemSelectedCount(selectedItems.size());
        notifyDataSetChanged();
    }


    static class MultiSelectHolder extends RecyclerView.ViewHolder {

        private CheckBox cb_isSelected;
        private ImageView iv_isDownloaded;
        private TextView tv_musicName;
        private TextView tv_artistName;

        public MultiSelectHolder(View itemView) {
            super(itemView);
            cb_isSelected = (CheckBox) itemView.findViewById(R.id.item_multi_select_item_cb);
            iv_isDownloaded = (ImageView) itemView.findViewById(R.id.item_multi_select_item_download_flag);
            tv_musicName = (TextView) itemView.findViewById(R.id.item_multi_select_item_music_name);
            tv_artistName = (TextView) itemView.findViewById(R.id.item_multi_select_item_artist_name);
        }
    }

}
