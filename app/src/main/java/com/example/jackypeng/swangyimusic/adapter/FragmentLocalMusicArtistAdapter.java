package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.RetrofitUtil;
import com.example.jackypeng.swangyimusic.rx.api.NewMusicApi;
import com.example.jackypeng.swangyimusic.rx.bean.ArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalArtistDetailInfo;
import com.example.jackypeng.swangyimusic.ui.activity.MusicListActivity;
import com.example.jackypeng.swangyimusic.util.QueryUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackypeng on 2018/3/21.
 */

public class FragmentLocalMusicArtistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LocalArtistDetailInfo> localArtistDetailInfos = new ArrayList<>();
    public static final String TAG = "FragmentLocalMusicArtistAdapter";
    private Context mContext;

    public FragmentLocalMusicArtistAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalMusicArtistHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_music_artist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final LocalArtistDetailInfo detailInfo = localArtistDetailInfos.get(position);
        final LocalMusicArtistHolder musicSongHolder = (LocalMusicArtistHolder) holder;
        musicSongHolder.tv_artist_name.setText(detailInfo.artist_name);
        musicSongHolder.tv_song_count.setText(detailInfo.number_of_tracks + "首");
        musicSongHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MusicListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", MusicListActivity.TYPE_ARTIST);
                bundle.putParcelable("artist", detailInfo);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        QueryUtil.getInstance()
                .getRetrofitClient()
                .builder(NewMusicApi.class)
                .queryArtist(detailInfo.getArtist_name())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject s) {
                        JSONObject artist = s.getJSONObject("artist");
                        String url = JSONObject.parseArray(artist.getString("image")).getJSONObject(2).getString("#text");
                        musicSongHolder.icon.setImageURI(url);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return localArtistDetailInfos.size();
    }

    public void setData(List<LocalArtistDetailInfo> data) {
        if (data != null) {
            localArtistDetailInfos = data;
            notifyDataSetChanged();
        }
    }

    private static class LocalMusicArtistHolder extends RecyclerView.ViewHolder {
        ImageView iv_artist_detail;
        TextView tv_artist_name;
        TextView tv_song_count;
        SimpleDraweeView icon;

        public LocalMusicArtistHolder(View itemView) {
            super(itemView);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.item_local_music_artist_item_icon);
            iv_artist_detail = (ImageView) itemView.findViewById(R.id.item_local_music_artist_item_artist_detail);
            tv_artist_name = (TextView) itemView.findViewById(R.id.item_local_music_artist_item_artist_name);
            tv_song_count = (TextView) itemView.findViewById(R.id.item_local_music_artist_item_song_count);
        }
    }
}
