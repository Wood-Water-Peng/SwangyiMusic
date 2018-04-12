package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.PlayingMusicStatusConstants;
import com.example.jackypeng.swangyimusic.eventBus.DownloadSongFinishedEvent;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumSongItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.DownloadInfoEntity;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/19.
 * <p>
 * 下载完成了的歌曲
 * <p>
 * 该页面的数据从数据库中读取，还要接受"下载中"页面的数据通知，可使用EventBus实现
 */

public class DownloadFinishedFragment extends BaseFragment {
    @BindView(R.id.fragment_download_finished_recycler_view)
    RecyclerView recyclerView;
    private FragmentDownloadFinishedAdapter downloadFinishedAdapter;
    private List<DownloadInfoEntity> mData = new ArrayList<>();
    private static final int TYPE_HEAD = 1;
    private static final int TYPE_ITEM = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_download_finished;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initAdapter();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadSongFinishedEvent(DownloadSongFinishedEvent event) {
        //刷新界面
        initData();
    }

    private void initData() {
        List<DownloadInfoEntity> finishedSongList = DownloadDBManager.getInstance().getFinishedSongList();
        if (finishedSongList != null) {
            mData = finishedSongList;
            downloadFinishedAdapter.notifyDataSetChanged();
        }
    }

    private void initAdapter() {
        downloadFinishedAdapter = new FragmentDownloadFinishedAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(downloadFinishedAdapter);
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    class FragmentDownloadFinishedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEAD) {
                return new HeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_finished_song_head, parent, false));
            } else if (viewType == TYPE_ITEM) {
                return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_finished_song_item, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof HeadHolder) {
                HeadHolder headHolder = (HeadHolder) holder;
                headHolder.tv_count.setText("共(" + String.valueOf(mData.size()) + ")首");
            } else if (holder instanceof ItemHolder) {
                final ItemHolder itemHolder = (ItemHolder) holder;
                final DownloadInfoEntity infoEntity = mData.get(position - 1);
                itemHolder.tv_song_name.setText(infoEntity.getSongName());
                itemHolder.tv_author_name.setText(infoEntity.getAuthorName());
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //播放歌曲
                        try {
                            //判断歌曲的状态,仅仅响应歌曲没有播放的状态
                            int songStatus = MusicPlayer.getInstance().getSongStatus(infoEntity.getSongId());
                            if (songStatus != PlayingMusicStatusConstants.INIT) {
                                return;
                            }
                            //将专辑中的歌曲列表传递过去
                            String[] musicIds = new String[mData.size()];
                            HashMap<String, AlbumListItemTrack> musicMap = new HashMap<>();
                            for (int i = 0; i < mData.size(); i++) {
                                DownloadInfoEntity entity = mData.get(i);
                                AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                                itemTrack.setSongId(entity.getSongId());
                                itemTrack.setSongName(entity.getSongName());
                                itemTrack.setAuthor(entity.getAuthorName());
                                itemTrack.setPic_big_url(entity.getPic_big());
                                itemTrack.setPic_small_url(entity.getPic_small());
                                itemTrack.setLrc(entity.getLrc());
                                musicIds[i] = entity.getSongId();
                                musicMap.put(entity.getSongId(), itemTrack);
                            }
                            MusicPlayer.getInstance().playAll(musicIds, musicMap, holder.getAdapterPosition());
                        } catch (Exception e) {

                        }
                    }
                });

                itemHolder.iv_song_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //显示歌曲详情
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEAD;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public int getItemCount() {
            return mData.size() + 1;
        }

        class HeadHolder extends RecyclerView.ViewHolder {
            TextView tv_count;

            public HeadHolder(View itemView) {
                super(itemView);
                tv_count = (TextView) itemView.findViewById(R.id.fragment_total_download_item_header_play_all_number);
            }
        }

        class ItemHolder extends RecyclerView.ViewHolder {

            TextView tv_song_name;
            TextView tv_author_name;
            ImageView iv_song_detail;

            public ItemHolder(View itemView) {
                super(itemView);
                tv_song_name = (TextView) itemView.findViewById(R.id.fragment_total_download_item_common_song_name);
                tv_author_name = (TextView) itemView.findViewById(R.id.fragment_total_download_item_common_author_name);
                iv_song_detail = (ImageView) itemView.findViewById(R.id.fragment_total_download_item_common_button_right);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
