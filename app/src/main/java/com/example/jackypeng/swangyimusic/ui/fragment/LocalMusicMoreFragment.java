package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentLocalMusicMoreAdapter;
import com.example.jackypeng.swangyimusic.adapter.FragmentMusicMoreAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumSongItemBean;
import com.example.jackypeng.swangyimusic.rx.bean.LocalAlbumDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalArtistDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalFolderDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jackypeng on 2018/3/14.
 * 专辑中关于歌曲的更多操作
 */

public class LocalMusicMoreFragment extends DialogFragment {

    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_ARTIST = 2;
    public static final int TYPE_ALBUM = 3;
    public static final int TYPE_FOLDER = 4;

    @BindView(R.id.fragment_music_more_song_name)
    TextView tv_song_name;
    @BindView(R.id.fragment_music_more_recycler_view)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private FragmentLocalMusicMoreAdapter musicMoreAdapter;

    public static LocalMusicMoreFragment newMusicDetailInstance(LocalMusicDetailInfo info) {
        LocalMusicMoreFragment fragment = new LocalMusicMoreFragment();
        Bundle args = new Bundle();
        args.putInt("type", TYPE_MUSIC);
        args.putParcelable("songInfo", info);
        fragment.setArguments(args);
        return fragment;
    }

    public static LocalMusicMoreFragment newArtistDetailInstance(LocalArtistDetailInfo info) {
        LocalMusicMoreFragment fragment = new LocalMusicMoreFragment();
        Bundle args = new Bundle();
        args.putInt("type", TYPE_ARTIST);
        args.putParcelable("artistInfo", info);
        fragment.setArguments(args);
        return fragment;
    }

    public static LocalMusicMoreFragment newAlbumDetailInstance(LocalAlbumDetailInfo info) {
        LocalMusicMoreFragment fragment = new LocalMusicMoreFragment();
        Bundle args = new Bundle();
        args.putInt("type", TYPE_ALBUM);
        args.putParcelable("albumInfo", info);
        fragment.setArguments(args);
        return fragment;
    }

    public static LocalMusicMoreFragment newFolderDetailInstance(LocalFolderDetailInfo info) {
        LocalMusicMoreFragment fragment = new LocalMusicMoreFragment();
        Bundle args = new Bundle();
        args.putInt("type", TYPE_FOLDER);
        args.putParcelable("folderInfo", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.bottom_controller_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music_more, null);
        unbinder = ButterKnife.bind(this, rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        initData();
        return rootView;
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) return;

        int type = bundle.getInt("type");
        switch (type) {
            case TYPE_MUSIC:
                initMusicDetail(bundle);
                break;
            case TYPE_ALBUM:
                initAlbumDetail(bundle);
                break;
            case TYPE_ARTIST:
                break;
            case TYPE_FOLDER:
                break;

        }

    }

    private void initAlbumDetail(Bundle bundle) {
        LocalAlbumDetailInfo artistDetail = bundle.getParcelable("albumInfo");
        if (artistDetail == null) return;

        tv_song_name.setText(artistDetail.album_name);
        List<FlowItem> itemList = new ArrayList<>();
        itemList.add(new FlowItem("播放", R.mipmap.lay_icn_play));
        itemList.add(new FlowItem("收藏到歌单", R.mipmap.lay_icn_fav));
        itemList.add(new FlowItem("删除", R.mipmap.lay_icn_delete));
        musicMoreAdapter = new FragmentLocalMusicMoreAdapter<LocalAlbumDetailInfo>(getContext(), itemList, artistDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(musicMoreAdapter);
    }

    private void initMusicDetail(Bundle bundle) {
        LocalMusicDetailInfo songDetail = bundle.getParcelable("songInfo");
        if (songDetail == null) return;

        String artist = songDetail.artist;
        String albumName = songDetail.albumName;
        String musicName = songDetail.musicName;
        tv_song_name.setText(musicName);
        List<FlowItem> itemList = new ArrayList<>();
        itemList.add(new FlowItem("收藏到歌单", R.mipmap.lay_icn_fav));
        itemList.add(new FlowItem("分享", R.mipmap.lay_icn_share));
        itemList.add(new FlowItem("删除", R.mipmap.lay_icn_delete));
        itemList.add(new FlowItem("歌手:" + artist, R.mipmap.lay_icn_artist));
        itemList.add(new FlowItem("专辑:" + albumName, R.mipmap.lay_icn_alb));
        itemList.add(new FlowItem("设为铃声", R.mipmap.lay_icn_ring));
        itemList.add(new FlowItem("查看歌曲信息", R.mipmap.lay_icn_document));
        musicMoreAdapter = new FragmentLocalMusicMoreAdapter(getContext(), itemList, songDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(musicMoreAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        int dialogWidth = (getResources().getDisplayMetrics().widthPixels);
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setAttributes(params);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public static class FlowItem {
        private String title;   //信息标题
        private int avatar; //图片ID

        public FlowItem(String title, int avatar) {
            this.title = title;
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAvatar() {
            return avatar;
        }

        public void setAvatar(int avatar) {
            this.avatar = avatar;
        }
    }
}
