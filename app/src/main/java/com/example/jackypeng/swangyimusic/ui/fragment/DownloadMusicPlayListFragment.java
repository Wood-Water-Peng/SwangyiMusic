package com.example.jackypeng.swangyimusic.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.DownloadMusicPlayListFragmentAdapter;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.rx.bean.PlayListMusicMoreFragmentBean;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserLoginInfoBean;
import com.example.jackypeng.swangyimusic.rx.bean.user.UserPlayListBean;
import com.example.jackypeng.swangyimusic.rx.contract.UserPlayListContract;
import com.example.jackypeng.swangyimusic.rx.model.UserPlayListModel;
import com.example.jackypeng.swangyimusic.rx.presenter.UserPlayListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jackypeng on 2018/3/14.
 * 歌单中下载单曲弹出选择下载歌单的fragment
 */

public class DownloadMusicPlayListFragment extends DialogFragment {

    @BindView(R.id.fragment_download_music_play_list_recycler_view)
    RecyclerView recyclerView;
    private Unbinder unbinder;
    private DownloadMusicPlayListFragmentAdapter adapter;
    private PlayListMusicMoreFragmentBean info;
    private static final String TAG = "DownloadMusicPlayListFragment";

    public static DownloadMusicPlayListFragment newInstance(PlayListMusicMoreFragmentBean info) {
        DownloadMusicPlayListFragment fragment = new DownloadMusicPlayListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_download_music_play_list, null);
        unbinder = ButterKnife.bind(this, rootView);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        info = getArguments().getParcelable("songInfo");
        initData();
        return rootView;
    }

    private void initData() {
        //获取用户歌单列表
        UserPlayListPresenter presenter = new UserPlayListPresenter();
        presenter.attachModel(new UserPlayListModel());
        presenter.attachView(new UserPlayListContract.View() {
            @Override
            public void getUserPlayListView(UserPlayListBean userPlayListBean) {
                if (userPlayListBean.getCode() == 200) {

                    List<DownloadMusicPlaylistItem> musicPlaylistItems = new ArrayList<>();
                    List<UserPlayListBean.UserPlayListItem> playlist = userPlayListBean.getPlaylist();
                    for (int i = 0; i < playlist.size(); i++) {
                        UserPlayListBean.UserPlayListItem item = playlist.get(i);
                        musicPlaylistItems.add(new DownloadMusicPlaylistItem(item.getCoverImgUrl(), item.getName(), String.valueOf(item.getTrackCount()), "0", info));
                    }
                    adapter.setData(musicPlaylistItems);
                }
            }

            @Override
            public void showErrorWithStatus(String msg) {
                Log.i(TAG, "error:" + msg);
            }
        });
        adapter = new DownloadMusicPlayListFragmentAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        presenter.getUserPlayList(UserLoginInfoBean.getInstance().getUserId());  //获取用户歌单
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

    /**
     * 不仅包含了歌单的数据，还包含了下载歌曲的数量
     */
    public static class DownloadMusicPlaylistItem {
        private String icon_url;
        private String title;
        private String total_count;
        private String download_count;
        private PlayListMusicMoreFragmentBean playListMusicMoreFragmentBean;

        public DownloadMusicPlaylistItem(String icon_url, String title, String total_count, String download_count, PlayListMusicMoreFragmentBean playListMusicMoreFragmentBean) {
            this.icon_url = icon_url;
            this.title = title;
            this.total_count = total_count;
            this.download_count = download_count;
            this.playListMusicMoreFragmentBean = playListMusicMoreFragmentBean;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public String getTitle() {
            return title;
        }

        public String getTotal_count() {
            return total_count;
        }

        public String getDownload_count() {
            return download_count;
        }

        public PlayListMusicMoreFragmentBean getPlayListMusicMoreFragmentBean() {
            return playListMusicMoreFragmentBean;
        }
    }

}
