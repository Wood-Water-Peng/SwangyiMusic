package com.example.jackypeng.swangyimusic.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.MultiSelectAdapter;
import com.example.jackypeng.swangyimusic.download_music.DownloadInfo;
import com.example.jackypeng.swangyimusic.download_music.DownloadManager;
import com.example.jackypeng.swangyimusic.rx.bean.playingListDetail.PlayingListDetailResult;
import com.example.jackypeng.swangyimusic.rx.contract.PlayingListDetailContract;
import com.example.jackypeng.swangyimusic.rx.db.DownloadDBManager;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.PlayingListDetailModel;
import com.example.jackypeng.swangyimusic.rx.presenter.PlayingListDetailPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.widget.DownloadTipDialog;
import com.example.jackypeng.swangyimusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by jackypeng on 2018/4/28.
 * <p>
 * 网络请求歌单数据
 */

public class MultiSelectActivity extends BaseActivity<PlayingListDetailContract.Model, PlayingListDetailContract.Presenter> implements PlayingListDetailContract.View {
    private static final String TAG = "MultiSelectActivity";
    @BindView(R.id.activity_multi_select_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.activity_multi_select_tv_selected_item)
    TextView tv_selected_item;
    @BindView(R.id.activity_multi_select_tv_selected_all)
    TextView tv_select_all;
    @BindView(R.id.activity_multi_select_iv_back)
    ImageView iv_back;


    private boolean isAllSelected = false;
    private List<DownloadInfo> undownloadedItems;

    @OnClick(R.id.activity_multi_select_iv_back)
    public void onBackClicked() {
        finish();
    }

    @OnClick(R.id.group_bottom_playlist_controller_download)
    public void onDownloadClicked() {
        /**
         *1.判断音乐是否已经下载或者已经在下载队列中
         *2.拿到选中的条目,加入到下载队列中
         *3.只下载没有下载或没有加入到下载队列中的歌曲
         */

        List<DownloadInfo> selectedItems = adapter.getSelectedItems();
        //还没下载过的歌曲
        undownloadedItems = new ArrayList<>();

        for (DownloadInfo info : selectedItems) {
            String songId = info.getSongId();
            Log.i(TAG, "songName:" + info.getSongName());
            if (DownloadDBManager.getInstance().getDownloadEntity(songId) == null && !DownloadManager.getInstance().isTaskInRunningQueue(songId) && !DownloadManager.getInstance().isTaskInWaitingQueue(songId)) {
                undownloadedItems.add(info);
            }
        }
        int size = undownloadedItems.size();
        if (size == 0) {
            ToastUtil.getInstance().toast("歌曲已下载或者在下载队列中");
            return;
        }
        DownloadTipDialog tipDialog = new DownloadTipDialog(this);
        tipDialog.setButton(BUTTON_POSITIVE, "下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //加入到下载队列
                DownloadManager.getInstance().enqueueTasks(undownloadedItems);
            }
        });
        tipDialog.setButton(BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        tipDialog.setMessage("将下载" + selectedItems.size() + "首歌曲，大约会占用40M空间。");
        tipDialog.show();
        //弹出提示框

        Log.i(TAG, "共提交" + size + "首歌曲");
//
        //清空选择记录

    }

    @OnClick(R.id.activity_multi_select_tv_selected_all)
    public void onSelectAllClicked() {    //全选
        isAllSelected = !isAllSelected;
        if (isAllSelected) {
            adapter.selectAll();
        } else {
            adapter.unSelectAll();
        }
    }

    private MultiSelectAdapter adapter;
    private String albumId;
    private ItemSelectListenerImple itemSelectListenerImple = new ItemSelectListenerImple();
    private int selectedItem = 0;

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            albumId = extras.getString("albumId");
            mPresenter.getPlayingListDetail(albumId);
        }
        adapter = new MultiSelectAdapter(this, itemSelectListenerImple);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mPresenter.getPlayingListDetail(albumId);

        tv_selected_item.setText("已选择" + selectedItem + "项");
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected BaseModel getModel() {
        return new PlayingListDetailModel();
    }

    @Override
    protected PlayingListDetailContract.Presenter getPresenter() {
        return new PlayingListDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multi_select;
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void getPlayingListDetailView(PlayingListDetailResult resultBean) {
        List<PlayingListDetailResult.PlayingListDetailTrack> tracks = resultBean.getResult().getTracks();
        adapter.setData(tracks);
    }

    private class ItemSelectListenerImple implements ItemSelectListener {

        @Override
        public void onItemSelectedCount(int count) {
            selectedItem = count;
            tv_selected_item.setText("已选择" + selectedItem + "项");
        }
    }

    public interface ItemSelectListener {
        void onItemSelectedCount(int count);
    }

}
