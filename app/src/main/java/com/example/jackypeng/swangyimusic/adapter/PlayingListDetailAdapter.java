package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class PlayingListDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_CONTENT = 2;
    private PlayingListDetailResult playingListDetailResult;

    public PlayingListDetailAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new PlayingListDetailHeaderColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playing_list_detail_header_column_item_holder, parent, false));
        } else if (viewType == TYPE_CONTENT) {
            return new PlayingListDetailContentColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playing_list_detail_content_column_item_holder, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (playingListDetailResult == null) return;
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            PlayingListDetailContentColumnHolder contentColumnHolder = (PlayingListDetailContentColumnHolder) holder;
            contentColumnHolder.tv_total_count.setText("(共" + playingListDetailResult.getResult().getTracks().size() + "首）");
            contentColumnHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            PlayingListDetailContentItemAdapter itemAdapter = new PlayingListDetailContentItemAdapter();
            contentColumnHolder.recyclerView.setAdapter(itemAdapter);
        } else if (type == TYPE_HEADER) {
            PlayingListDetailHeaderColumnHolder headerColumnHolder = (PlayingListDetailHeaderColumnHolder) holder;
            Glide.with(mContext).load(playingListDetailResult.getResult().getCoverImgUrl()).into(headerColumnHolder.cover);
            headerColumnHolder.tv_title.setText(playingListDetailResult.getResult().getName());
            headerColumnHolder.tv_creator.setText(playingListDetailResult.getResult().getCreator().getString("nickname"));
            Glide.with(mContext).load(playingListDetailResult.getResult().getCreator().getString("avatarUrl")).into(headerColumnHolder.creatpr_avatar);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_CONTENT;
        }
    }

    public void setData(PlayingListDetailResult result) {
        if (result != null) {
            this.playingListDetailResult = result;
            notifyDataSetChanged();
        }
    }

    static class PlayingListDetailHeaderColumnHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        ImageView creatpr_avatar;
        TextView tv_title;
        TextView tv_creator;


        public PlayingListDetailHeaderColumnHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.item_playing_list_detail_header_cover);
            creatpr_avatar = (ImageView) itemView.findViewById(R.id.item_playing_list_detail_creator_avatar);
            tv_title = (TextView) itemView.findViewById(R.id.item_playing_list_detail_title);
            tv_creator = (TextView) itemView.findViewById(R.id.item_playing_list_detail_creator_name);
        }
    }

    static class PlayingListDetailContentColumnHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView tv_total_count;

        public PlayingListDetailContentColumnHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_content);
            tv_total_count = (TextView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_header_tv_total_count);
        }
    }

    class PlayingListDetailContentItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PlayingListDetailContentItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_playing_list_detail_recycler_view_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if (playingListDetailResult == null) return;
            final PlayingListDetailResult.PlayingListDetailTrack track = playingListDetailResult.getResult().getTracks().get(position);
            PlayingListDetailContentItemHolder itemHolder = (PlayingListDetailContentItemHolder) holder;

            /**
             * 判断当前是否播放的是当前歌曲
             */
            try {
                AlbumListItemTrack songTrack = MusicPlayer.getInstance().getPlayingSongTrack();
                if (songTrack != null && songTrack.getSongId().equals(track.getId())) {
                    itemHolder.showPlayingStatus();
                } else {
                    itemHolder.showSongIndex();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            itemHolder.tv_index.setText(String.valueOf(position + 1));
            itemHolder.tv_name.setText(track.getName());
            itemHolder.tv_artist.setText(track.getArtists().getJSONObject(0).getString("name"));
            itemHolder.tv_album.setText(track.getAlbum().getString("name"));
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击播放
                    /**
                     * 1.正在播放    ---->进入播放详情界面
                     * 1.未播放      ---->开始播放
                     */
                    try {
                        AlbumListItemTrack songTrack = MusicPlayer.getInstance().getPlayingSongTrack();
                        if (songTrack == null) {   //说明还未播放音乐,直接播放音乐
                            List<PlayingListDetailResult.PlayingListDetailTrack> tracks = playingListDetailResult.getResult().getTracks();
                            String[] musicIds = new String[tracks.size()];
                            HashMap<String, AlbumListItemTrack> musicMap = new HashMap<>();
                            for (int i = 0; i < tracks.size(); i++) {
                                PlayingListDetailResult.PlayingListDetailTrack detailTrack = tracks.get(i);
                                AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                                itemTrack.setSongId(detailTrack.getId());
                                itemTrack.setSongName(detailTrack.getName());
                                itemTrack.setAuthor(detailTrack.getArtists().getJSONObject(0).getString("name"));
                                itemTrack.setAlbum_id(detailTrack.getAlbum().getString("id"));
                                itemTrack.setPic_big_url(detailTrack.getAlbum().getString("picUrl"));
                                musicIds[i] = detailTrack.getId();
                                musicMap.put(detailTrack.getId(), itemTrack);
                            }
                            MusicPlayer.getInstance().playAll(musicIds, musicMap, holder.getAdapterPosition());
                        } else if (songTrack.getSongId().equals(track.getId())) {   //进入播放详情页面

                        } else {      //播放音乐
                            List<PlayingListDetailResult.PlayingListDetailTrack> tracks = playingListDetailResult.getResult().getTracks();
                            String[] musicIds = new String[tracks.size()];
                            HashMap<String, AlbumListItemTrack> musicMap = new HashMap<>();
                            for (int i = 0; i < tracks.size(); i++) {
                                PlayingListDetailResult.PlayingListDetailTrack detailTrack = tracks.get(i);
                                AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                                itemTrack.setSongId(detailTrack.getId());
                                itemTrack.setSongName(detailTrack.getName());
                                itemTrack.setAuthor(detailTrack.getArtists().getJSONObject(0).getString("name"));
                                itemTrack.setAlbum_id(detailTrack.getAlbum().getString("id"));
                                itemTrack.setPic_big_url(detailTrack.getAlbum().getString("picUrl"));
                                itemTrack.setPic_small_url(detailTrack.getAlbum().getString("picUrl"));
                                musicIds[i] = detailTrack.getId();
                                musicMap.put(detailTrack.getId(), itemTrack);
                            }
                            MusicPlayer.getInstance().playAll(musicIds, musicMap, holder.getAdapterPosition());
                        }


                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            if (playingListDetailResult == null) return 0;
            return playingListDetailResult.getResult().getTracks().size();
        }


        class PlayingListDetailContentItemHolder extends RecyclerView.ViewHolder {

            TextView tv_index;
            TextView tv_name;
            TextView tv_alias;
            TextView tv_artist;
            TextView tv_album;
            ImageView iv_playing_icon;

            public PlayingListDetailContentItemHolder(View itemView) {
                super(itemView);
                tv_index = (TextView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_item_music_index);
                tv_name = (TextView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_item_music_name);
                tv_alias = (TextView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_item_music_alias);
                tv_artist = (TextView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_item_music_artist);
                tv_album = (TextView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_item_music_album);
                iv_playing_icon = (ImageView) itemView.findViewById(R.id.item_playing_list_detail_recycler_view_item_music_iv_playing_icon);
            }

            void showSongIndex() {
                tv_index.setVisibility(View.VISIBLE);
                iv_playing_icon.setVisibility(View.GONE);
            }

            void showPlayingStatus() {
                tv_index.setVisibility(View.GONE);
                iv_playing_icon.setVisibility(View.VISIBLE);
            }
        }
    }

}
