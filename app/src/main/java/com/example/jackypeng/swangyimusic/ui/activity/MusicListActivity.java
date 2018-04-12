package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.MusicListAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.LocalAlbumDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalFolderDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.widget.CustomDividerItemDecoration;
import com.example.jackypeng.swangyimusic.ui.fragment.BottomControllFragment;
import com.example.jackypeng.swangyimusic.util.MusicUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/23.
 * <p>
 * 歌曲列表    歌手、专辑、文件夹
 */

public class MusicListActivity extends BaseActivity {

    public static final int TYPE_ARTIST = 1;  //歌手
    public static final int TYPE_ALBUM = 2;   //专辑
    public static final int TYPE_FOLDER = 3;  //文件夹

    @BindView(R.id.activity_music_list_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_music_list_recycler_view)
    RecyclerView recyclerView;
    private LocalArtistDetailInfo artistDetailInfo;
    private LocalAlbumDetailInfo albumDetailInfo;
    private LocalFolderDetailInfo folderDetailInfo;
    private List<LocalMusicDetailInfo> musicDetailInfos;
    private MusicListAdapter adapter;
    private int type;

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt("type");
            if (type == TYPE_ARTIST) {
                artistDetailInfo = bundle.getParcelable("artist");
            } else if (type == TYPE_ALBUM) {
                albumDetailInfo = bundle.getParcelable("album");
            } else if (type == TYPE_FOLDER) {
                folderDetailInfo = bundle.getParcelable("folder");
            }
        }
        adapter = new MusicListAdapter(this, type);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.gray_line_divider));
        recyclerView.addItemDecoration(itemDecoration);
        initToolbar();
        initBottomFragment();
        initAdapter();
    }

    private void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.mipmap.actionbar_back);
            switch (type) {
                case TYPE_ARTIST:
                    ab.setTitle(artistDetailInfo.artist_name);
                    break;
                case TYPE_ALBUM:
                    ab.setTitle(albumDetailInfo.album_name);
                    break;
                case TYPE_FOLDER:
                    ab.setTitle(folderDetailInfo.folder_path);
                    break;
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void initAdapter() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                if(type==TYPE_ARTIST) {
                    musicDetailInfos = MusicUtil.queryMusic(MainApplication.getAppContext(), artistDetailInfo.artist_id, MusicUtil.START_FROM_ARTIST);
                }else if(type==TYPE_ALBUM){
                    musicDetailInfos = MusicUtil.queryMusic(MainApplication.getAppContext(), albumDetailInfo.album_id, MusicUtil.START_FROM_ALBUM);
                }else if(type==TYPE_FOLDER){
                    musicDetailInfos = MusicUtil.queryMusic(MainApplication.getAppContext(), folderDetailInfo.folder_path, MusicUtil.START_FROM_FOLDER);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.setData(musicDetailInfos);
                recyclerView.setAdapter(adapter);
            }
        }.execute();
    }

    private void initBottomFragment() {
        BottomControllFragment bottomControllFragment = new BottomControllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_music_list_bottom_controller, bottomControllFragment).commit();
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_list;
    }
}
