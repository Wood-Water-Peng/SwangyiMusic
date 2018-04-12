package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentLocalMusicArtistAdapter;
import com.example.jackypeng.swangyimusic.adapter.FragmentLocalMusicSongAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.bean.LocalArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.SongMenuModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.presenter.SongMenuPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.SongMenuView;
import com.example.jackypeng.swangyimusic.util.MusicUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class LocalMusicArtistFragment extends BaseFragment {

    @BindView(R.id.activity_local_music_artist_recycler_view)
    RecyclerView recyclerView;
    private FragmentLocalMusicArtistAdapter adapter;
    private List<LocalArtistDetailInfo> localArtistDetailInfos;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music_artist;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        adapter = new FragmentLocalMusicArtistAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void initData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                localArtistDetailInfos = MusicUtil.queryAllArtists();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.setData(localArtistDetailInfos);
            }
        }.execute();
    }

}
