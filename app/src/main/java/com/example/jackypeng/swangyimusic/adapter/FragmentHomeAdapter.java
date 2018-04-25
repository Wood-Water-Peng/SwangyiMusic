package com.example.jackypeng.swangyimusic.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.RecentPlayBean;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserPlayListBean;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.ui.activity.DownLoadActivity;
import com.example.jackypeng.swangyimusic.ui.activity.LocalMusicActivity;
import com.example.jackypeng.swangyimusic.ui.activity.PlayingListDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/18.
 */

public class FragmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalMusicDetailInfo> commonItem = new ArrayList<>();  //本地的数据
    private List<RecentPlayBean> recentPlayList = new ArrayList<>();  //最近播放
    private List<UserPlayListBean.UserPlayListItem> userPlayListItems = new ArrayList<>();  //用户创建的歌单
    private List<UserPlayListBean.UserPlayListItem> totalList = new ArrayList<>();  //用户显示的歌单列表
    private boolean isCreatListExpended = false;   //创建的歌单是否展开
    private static final String TAG = "FragmentHomeAdapter";

    public static final int COMMON_TYPE = 1;
    public static final int CREATED_SONG_LIST_HEAD = 2;
    public static final int CREATED_SONG_LIST_ITEM = 3;

    private Context mContext;

    public FragmentHomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "viewType:" + viewType);
        switch (viewType) {
            case COMMON_TYPE:
                return new CommonItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_common_item, parent, false));
            case CREATED_SONG_LIST_HEAD:
                return new SongListHeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_songlist_head, parent, false));
            case CREATED_SONG_LIST_ITEM:
                return new SongListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_songlist_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case COMMON_TYPE:
                CommonItemHolder commonItemHolder = (CommonItemHolder) holder;
                if (position == 0) {
                    commonItemHolder.iv_icon.setImageResource(R.mipmap.music_icn_local);
                    commonItemHolder.tv_name.setText("本地音乐");
                    commonItemHolder.tv_count.setText(String.valueOf("(" + commonItem.size() + ")"));
                    commonItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, LocalMusicActivity.class));
                        }
                    });
                } else if (position == 1) {
                    commonItemHolder.iv_icon.setImageResource(R.mipmap.music_icn_recent);
                    commonItemHolder.tv_name.setText("最近播放");
                    commonItemHolder.tv_count.setText(String.valueOf("(" + recentPlayList.size() + ")"));
                } else if (position == 2) {
                    commonItemHolder.iv_icon.setImageResource(R.mipmap.music_icn_dld);
                    commonItemHolder.tv_name.setText("下载管理");
                    commonItemHolder.tv_count.setText(String.valueOf("(" + DownloadDBManager.getInstance().getFinishedSongList().size() + ")"));
                    commonItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, DownLoadActivity.class));
                        }
                    });
                } else if (position == 3) {
                    commonItemHolder.iv_icon.setImageResource(R.mipmap.music_icn_artist);
                    commonItemHolder.tv_name.setText("我的歌手");
                    commonItemHolder.tv_count.setText(String.valueOf("(" + recentPlayList.size() + ")"));
                }
                break;
            case CREATED_SONG_LIST_HEAD:
                final SongListHeadHolder headHolder = (SongListHeadHolder) holder;
                headHolder.tv_name.setText("创建的歌单");
                if (totalList.size() > 0) {
                    isCreatListExpended = true;
                    ObjectAnimator anim = ObjectAnimator.ofFloat(headHolder.iv_icon, "rotation", 0, 90);
                    anim.setDuration(100);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.start();
                }
                headHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(headHolder.iv_icon, "rotation", 0, 90);
                        anim.setDuration(100);
                        anim.setInterpolator(new LinearInterpolator());
                        if (isCreatListExpended) {
                            totalList.removeAll(userPlayListItems);
                            notifyItemRangeRemoved(5, userPlayListItems.size());
                            anim.reverse();
                        } else {
                            totalList.addAll(userPlayListItems);
                            notifyItemRangeInserted(5, userPlayListItems.size());
                            anim.start();
                        }
                        isCreatListExpended = !isCreatListExpended;
                    }
                });
                break;
            case CREATED_SONG_LIST_ITEM:
                SongListItemHolder itemHolder = (SongListItemHolder) holder;
                final UserPlayListBean.UserPlayListItem item = totalList.get(position - 5);
                itemHolder.iv_icon.setImageURI(item.getCoverImgUrl());
                itemHolder.tv_name.setText(item.getName());
                itemHolder.tv_count.setText(item.getTrackCount() + "首");
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PlayingListDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", item.getId());
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "totalSize:" + totalList.size());
        return 5 + totalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                return COMMON_TYPE;
            case 4:
                return CREATED_SONG_LIST_HEAD;
        }
        Log.i(TAG, "position:" + position);
        if ((totalList.size() + 4) >= position) {
            return CREATED_SONG_LIST_ITEM;
        }
        return -1;
    }

    public void setData(List<LocalMusicDetailInfo> localMusicDetailInfoList, List<RecentPlayBean> recentPlayList) {
        if (localMusicDetailInfoList != null) {
            this.commonItem = localMusicDetailInfoList;
        }
        if (recentPlayList != null) {
            this.recentPlayList = recentPlayList;
        }
        notifyDataSetChanged();
    }

    //用户创建的歌单
    public void setUserPlaylist(List<UserPlayListBean.UserPlayListItem> userPlayListItems) {
        if (userPlayListItems == null || userPlayListItems.size() == 0) {
            return;
        }
        this.userPlayListItems = userPlayListItems;
        this.totalList.addAll(userPlayListItems);
        notifyDataSetChanged();
    }

    static class CommonItemHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_count;

        CommonItemHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.fragment_home_item_img);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_home_item_title);
            tv_count = (TextView) itemView.findViewById(R.id.fragment_home_item_count);
        }
    }

    static class SongListHeadHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        ImageView iv_menu;
        TextView tv_name;
        TextView tv_count;

        SongListHeadHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.fragment_home_songlist_head_icon);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_home_songlist_head_name_tv);
            tv_count = (TextView) itemView.findViewById(R.id.fragment_home_songlist_head_song_count_tv);
            iv_menu = (ImageView) itemView.findViewById(R.id.fragment_home_songlist_head_menu_iv);
        }
    }

    static class SongListItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_icon;
        TextView tv_name;
        TextView tv_count;

        SongListItemHolder(View itemView) {
            super(itemView);
            iv_icon = (SimpleDraweeView) itemView.findViewById(R.id.fragment_main_playlist_item_img);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_main_playlist_item_title);
            tv_count = (TextView) itemView.findViewById(R.id.fragment_main_playlist_item_count);
        }
    }
}
