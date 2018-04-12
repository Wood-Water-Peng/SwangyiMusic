package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentLocalMusicAlbumAdapter;
import com.example.jackypeng.swangyimusic.adapter.FragmentLocalMusicFolderAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicBean;
import com.example.jackypeng.swangyimusic.rx.bean.LocalFolderDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.model.SongMenuModel;
import com.example.jackypeng.swangyimusic.rx.presenter.SongMenuPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.SongMenuView;
import com.example.jackypeng.swangyimusic.util.MusicUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class LocalMusicFolderFragment extends BaseFragment {

    @BindView(R.id.activity_local_music_folder_recycler_view)
    RecyclerView recyclerView;
    private FragmentLocalMusicFolderAdapter adapter;
    private List<LocalFolderDetailInfo> localFolderDetailInfos;

    private void initData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                localFolderDetailInfos = MusicUtil.queryAllFolders();
                for (int i = 0; i < localFolderDetailInfos.size(); i++) {
                    List<LocalMusicDetailInfo> albumList = MusicUtil.queryMusic(MainApplication.getAppContext(), localFolderDetailInfos.get(i).folder_path, MusicUtil.START_FROM_FOLDER);
                    localFolderDetailInfos.get(i).folder_count = albumList.size();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.setData(localFolderDetailInfos);
            }
        }.execute();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music_folder;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        adapter = new FragmentLocalMusicFolderAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        initData();
    }

    @Override
    protected SongMenuModel getModel() {
        return null;
    }

    @Override
    protected SongMenuPresenter getPresenter() {
        return null;
    }
}
