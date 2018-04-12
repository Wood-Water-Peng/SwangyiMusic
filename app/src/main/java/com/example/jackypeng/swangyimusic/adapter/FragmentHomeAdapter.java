package com.example.jackypeng.swangyimusic.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.RecentPlayBean;
import com.example.jackypeng.swangyimusic.rx.bean.SongListInfo;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.ui.activity.DownLoadActivity;
import com.example.jackypeng.swangyimusic.ui.activity.LocalMusicActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/18.
 */

public class FragmentHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SongListInfo> createdList = new ArrayList<>();   //创建的歌单
    private List<SongListInfo> collectedList = new ArrayList<>();  //收藏的歌单
    private List<LocalMusicDetailInfo> commonItem = new ArrayList<>();  //本地的数据
    private List<RecentPlayBean> recentPlayList = new ArrayList<>();  //最近播放
    private boolean isCreatListExpended = false;   //创建的歌单是否展开
    private boolean isCollectListExpended = false; //收藏的歌单是否展开
    private List<SongListInfo> totalList = new ArrayList<>();


    public static final int COMMON_TYPE = 1;
    public static final int CREATED_SONG_LIST_HEAD = 2;
    public static final int CREATED_SONG_LIST_ITEM = 3;
    public static final int COLLECTED_SONG_LIST_HEAD = 4;
    public static final int COLLECTED_SONG_LIST_ITEM = 5;

    private Context mContext;

    public FragmentHomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMMON_TYPE:
                return new CommonItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_common_item, parent, false));
            case CREATED_SONG_LIST_HEAD:
                return new SongListHeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_songlist_head, parent, false));
            case CREATED_SONG_LIST_ITEM:
                return new SongListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_songlist_item, parent, false));
            case COLLECTED_SONG_LIST_HEAD:
                return new SongListHeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_songlist_head, parent, false));
            case COLLECTED_SONG_LIST_ITEM:
                return new SongListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_songlist_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonItemHolder) {
            CommonItemHolder itemHolder = (CommonItemHolder) holder;
            if (position == 0) {
                itemHolder.iv_icon.setImageResource(R.mipmap.music_icn_local);
                itemHolder.tv_name.setText("本地音乐");
                itemHolder.tv_count.setText(String.valueOf("(" + commonItem.size() + ")"));
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, LocalMusicActivity.class));
                    }
                });
            } else if (position == 1) {
                itemHolder.iv_icon.setImageResource(R.mipmap.music_icn_recent);
                itemHolder.tv_name.setText("最近播放");
                itemHolder.tv_count.setText(String.valueOf("(" + recentPlayList.size() + ")"));
            } else if (position == 2) {
                itemHolder.iv_icon.setImageResource(R.mipmap.music_icn_dld);
                itemHolder.tv_name.setText("下载管理");
                itemHolder.tv_count.setText(String.valueOf("(" + DownloadDBManager.getInstance().getFinishedSongList().size() + ")"));
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, DownLoadActivity.class));
                    }
                });
            } else if (position == 3) {
                itemHolder.iv_icon.setImageResource(R.mipmap.music_icn_artist);
                itemHolder.tv_name.setText("我的歌手");
                itemHolder.tv_count.setText(String.valueOf("(" + recentPlayList.size() + ")"));
            }
        } else if (holder instanceof SongListHeadHolder) {
            final SongListHeadHolder headHolder = (SongListHeadHolder) holder;
            int type = getItemViewType(position);
            if (type == CREATED_SONG_LIST_HEAD) {
                headHolder.tv_name.setText("创建的歌单");
                headHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(headHolder.iv_icon, "rotation", 0, 90);
                        anim.setDuration(100);
                        anim.setInterpolator(new LinearInterpolator());
                        if (isCreatListExpended) {
                            totalList.removeAll(createdList);
                            notifyItemRangeRemoved(5, createdList.size());
                            anim.reverse();
                        } else {
                            totalList.addAll(createdList);
                            notifyItemRangeInserted(5, createdList.size());
                            anim.start();
                        }
                        isCreatListExpended = !isCreatListExpended;
                    }
                });
            } else if (type == COLLECTED_SONG_LIST_HEAD) {
                headHolder.tv_name.setText("收藏的歌单");
                headHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectAnimator anim = ObjectAnimator.ofFloat(headHolder.iv_icon, "rotation", 0, 90);
                        anim.setDuration(100);
                        anim.setInterpolator(new LinearInterpolator());
                        if (isCollectListExpended) {
                            totalList.removeAll(collectedList);
                            notifyItemRangeRemoved(totalList.size() - collectedList.size() - 1, collectedList.size());
                            anim.reverse();
                        } else {
                            totalList.addAll(collectedList);
                            notifyItemRangeInserted(totalList.size() - collectedList.size() - 1, collectedList.size());
                            anim.start();
                        }
                        isCollectListExpended = !isCollectListExpended;
                    }
                });
            }
        } else if (holder instanceof SongListItemHolder) {
            SongListItemHolder itemHolder = (SongListItemHolder) holder;
        }

    }

    @Override
    public int getItemCount() {
        return 6 + totalList.size();
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
        if ((createdList.size() + 4) > position) {
            return CREATED_SONG_LIST_ITEM;
        } else {
            if (position == createdList.size() + 5) {
                return COLLECTED_SONG_LIST_HEAD;
            } else if ((collectedList.size() + createdList.size() + 6) > position) {
                return COLLECTED_SONG_LIST_ITEM;
            }
        }
        return 0;
    }

    public void setData(List<LocalMusicDetailInfo> localMusicDetailInfoList, List<RecentPlayBean> recentPlayList) {
        this.commonItem = localMusicDetailInfoList;
        this.recentPlayList = recentPlayList;
        //初始化数据
        if (isCollectListExpended) {
            totalList.addAll(collectedList);
        } else if (isCreatListExpended) {
            totalList.addAll(createdList);
        }

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
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_count;

        SongListItemHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.fragment_home_item_img);
            tv_name = (TextView) itemView.findViewById(R.id.fragment_home_item_title);
            tv_count = (TextView) itemView.findViewById(R.id.fragment_home_item_count);
        }
    }
}
