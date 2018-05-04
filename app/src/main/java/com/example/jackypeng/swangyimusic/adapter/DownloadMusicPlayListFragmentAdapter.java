package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.download_music.DownloadInfo;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.download_music.MusicDownloadTrack;
import com.example.jackypeng.swangyimusic.download_music.SimpleDownloadTaskListener;
import com.example.jackypeng.swangyimusic.ui.fragment.DownloadMusicPlayListFragment;
import com.example.jackypeng.swangyimusic.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/4/26.
 */

public class DownloadMusicPlayListFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private Context mContext;
    private List<DownloadMusicPlayListFragment.DownloadMusicPlaylistItem> musicPlaylistItems = new ArrayList<>();

    public DownloadMusicPlayListFragmentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new DownloadPlayListHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_music_playlist_header, parent, false));
        } else if (viewType == TYPE_ITEM) {
            return new DownloadPlayListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_music_playlist_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_HEADER) {
            DownloadPlayListHeaderHolder headerHolder = (DownloadPlayListHeaderHolder) holder;
        } else if (type == TYPE_ITEM) {
            final DownloadPlayListItemHolder itemHolder = (DownloadPlayListItemHolder) holder;
            final DownloadMusicPlayListFragment.DownloadMusicPlaylistItem item = musicPlaylistItems.get(position - 1);
            itemHolder.iv_icon.setImageURI(item.getIcon_url());
            itemHolder.tv_name.setText(item.getTitle());
            itemHolder.tv_count.setText(item.getTotal_count() + "首");
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * 1.判断网络状态
                     * 2.确定下载到某个歌单
                     * 3.开始下载
                     * 4.歌单界面需要监听下载状态
                     *
                     * 此时将下载任务提交(下载可能会失败)
                     */
                    Intent intent = new Intent(BroadcastConstants.HIDE_MUSIC_MORE_DOWNLOAD_TO_PLAYLIST_FRAGMENT);
                    mContext.sendBroadcast(intent);
                    DownloadInfo info = new DownloadInfo();
                    info.setSongId(item.getPlayListMusicMoreFragmentBean().getId());
                    info.setSongName(item.getPlayListMusicMoreFragmentBean().getMusicName());
                    DownloadManager.getInstance().startDownload(info, new SimpleDownloadTaskListener() {
                        @Override
                        public void onStart(MusicDownloadTrack downloadTask) {
                            ToastUtil.getInstance().toast("歌曲开始下载");
                            //创建一条数据库记录
                        }

                        @Override
                        public void onDownloading(MusicDownloadTrack downloadTask) {

                        }

                        @Override
                        public void onCompleted(MusicDownloadTrack downloadTask) {
                            ToastUtil.getInstance().toast("歌曲下载完成");

                        }

                        @Override
                        public void onError(MusicDownloadTrack downloadTask, int errorCode) {
                            ToastUtil.getInstance().toast("歌曲下载出错");
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return musicPlaylistItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setData(List<DownloadMusicPlayListFragment.DownloadMusicPlaylistItem> data) {
        if (data != null && data.size() > 0) {
            this.musicPlaylistItems = data;
            notifyDataSetChanged();
        }
    }

    static class DownloadPlayListItemHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView iv_icon;
        TextView tv_name;
        TextView tv_count;

        public DownloadPlayListItemHolder(View itemView) {
            super(itemView);
            iv_icon = (SimpleDraweeView) itemView.findViewById(R.id.fragment_download_music_playlist_item_icon);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_download_music_playlist_item_title);
            tv_count = (TextView) itemView.findViewById(R.id.fragment_download_music_playlist_item_count);
        }
    }

    static class DownloadPlayListHeaderHolder extends RecyclerView.ViewHolder {

        public DownloadPlayListHeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
