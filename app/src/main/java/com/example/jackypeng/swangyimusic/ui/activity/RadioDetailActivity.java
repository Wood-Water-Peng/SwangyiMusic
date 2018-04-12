
package com.example.jackypeng.swangyimusic.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.RadioDetailSongListAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.RadioDetailResponseBean;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.RadioDetailModel;
import com.example.jackypeng.swangyimusic.rx.presenter.RadioDetailPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.rx.view.rxView.RadioDetailView;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import butterknife.BindView;

/**
 * 专辑详情
 */
public class RadioDetailActivity extends BaseActivity<RadioDetailModel, RadioDetailPresenter> implements RadioDetailView {
    private String radioId, radioPath, radioName, radioDes, artistName;

    @BindView(R.id.radio_detail_song_list)
    RecyclerView recyclerView;
    private RadioDetailSongListAdapter songListAdapter;

    @BindView(R.id.radio_detail_header_cover)
    ImageView radio_cover;
    @BindView(R.id.radio_detail_header_title)
    TextView radio_title;
    private MusicPlayer musicPlayer;

    @Override
    protected void onInitView(Bundle bundle) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            radioId = extras.getString("albumid");
            radioPath = extras.getString("albumart");
            radioName = extras.getString("albumname");
            radioDes = extras.getString("albumdetail");
            artistName = extras.getString("artistname");
        }
        mPresenter.getRadioDetail(radioId, 10);
        songListAdapter = new RadioDetailSongListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(songListAdapter);
        musicPlayer = MusicPlayer.getInstance();
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected BaseModel getModel() {
        return new RadioDetailModel();
    }

    @Override
    protected RadioDetailPresenter getPresenter() {
        return new RadioDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_radio_detail;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void doFetchRadioDetail(RadioDetailResponseBean radioDetailBean) {
        Glide.with(this).load(radioPath).into(radio_cover);
        radio_title.setText(radioName);
        songListAdapter.setData(radioDetailBean.getResult());
    }


}
