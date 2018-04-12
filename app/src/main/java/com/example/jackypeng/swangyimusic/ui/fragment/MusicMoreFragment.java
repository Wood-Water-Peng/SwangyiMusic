package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.os.RemoteException;
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
import com.example.jackypeng.swangyimusic.adapter.FragmentMusicMoreAdapter;
import com.example.jackypeng.swangyimusic.adapter.PlayingQueueFragmentAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.AlbumSongItemBean;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jackypeng on 2018/3/14.
 * 专辑中关于歌曲的更多操作
 */

public class MusicMoreFragment extends DialogFragment {

    @BindView(R.id.fragment_music_more_song_name)
    TextView tv_song_name;
    @BindView(R.id.fragment_music_more_recycler_view)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private FragmentMusicMoreAdapter musicMoreAdapter;

    public static MusicMoreFragment newInstance(AlbumSongItemBean info) {
        MusicMoreFragment fragment = new MusicMoreFragment();
        Bundle args = new Bundle();
        args.putParcelable("songInfo", info);
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
        AlbumSongItemBean songDetail = getArguments().getParcelable("songInfo");
        if (songDetail == null) return;

        String artist = songDetail.getAuthor();
        String albumName = songDetail.getAlbum_title();
        String musicName = songDetail.getTitle();
        tv_song_name.setText(musicName);
        List<FlowItem> itemList = new ArrayList<>();
        itemList.add(new FlowItem("收藏到歌单", R.mipmap.lay_icn_fav));
        itemList.add(new FlowItem("分享", R.mipmap.lay_icn_share));
        itemList.add(new FlowItem("下载", R.mipmap.lay_icn_dld));
        itemList.add(new FlowItem("歌手:" + artist, R.mipmap.lay_icn_artist));
        itemList.add(new FlowItem("专辑:" + albumName, R.mipmap.lay_icn_alb));
        itemList.add(new FlowItem("设为铃声", R.mipmap.lay_icn_ring));
        itemList.add(new FlowItem("查看歌曲信息", R.mipmap.lay_icn_document));
        musicMoreAdapter = new FragmentMusicMoreAdapter(getContext(), itemList, songDetail);
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
