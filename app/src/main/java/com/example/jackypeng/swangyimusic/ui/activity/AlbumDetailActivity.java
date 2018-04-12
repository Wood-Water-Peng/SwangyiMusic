
package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.AlbumDetailSongListAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumDetailBean;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumInfoBean;
import com.example.jackypeng.swangyimusic.rx.model.AlbumDetailModel;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.AlbumDetailPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.AlbumDetailView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import butterknife.BindView;

/**
 * 专辑详情
 */
public class AlbumDetailActivity extends BaseActivity<AlbumDetailModel, AlbumDetailPresenter> implements AlbumDetailView {
    private String albumId, albumPath, albumName, albumDes, artistName;

    @BindView(R.id.album_detail_song_list)
    RecyclerView recyclerView;
    private AlbumDetailSongListAdapter songListAdapter;

    @BindView(R.id.album_detail_header_cover)
    ImageView album_cover;
    @BindView(R.id.album_detail_header_title)
    TextView album_title;

    @Override
    protected void onInitView(Bundle bundle) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            albumId = extras.getString("albumid");
            albumPath = extras.getString("albumart");
            albumName = extras.getString("albumname");
            albumDes = extras.getString("albumdetail");
            artistName = extras.getString("artistname");
        }
        mPresenter.getAlbumDetail(albumId);
        songListAdapter = new AlbumDetailSongListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(songListAdapter);
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected BaseModel getModel() {
        return new AlbumDetailModel();
    }

    @Override
    protected AlbumDetailPresenter getPresenter() {
        return new AlbumDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_album_detail;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void doFetchAlbumDetail(AlbumDetailBean albumDetailBean) {
        AlbumInfoBean albuminfo = albumDetailBean.getAlbuminfo();
        Glide.with(this).load(albumPath).into(album_cover);
        album_title.setText(albumName);
        songListAdapter.setData(albumDetailBean.getSonglist());

        //根据songId，继续请求每一首歌曲的信息
    }


}
