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
import com.example.jackypeng.swangyimusic.adapter.PlayingQueueFragmentAdapter;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jackypeng on 2018/3/14.
 */

public class PlayingQueueFragment extends DialogFragment {

    @BindView(R.id.fragment_playing_queue_list_number)
    TextView tv_title;
    @BindView(R.id.fragment_playing_queue_recycler_view)
    RecyclerView recyclerView;
    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.bottom_controller_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playing_queue, null);
        unbinder = ButterKnife.bind(this, rootView);
        PlayingQueueFragmentAdapter adapter = new PlayingQueueFragmentAdapter(getContext());
        adapter.updateDataSet();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        try {
            int size = MusicPlayer.getInstance().getPlayingListTrack().size();
            tv_title.setText("播放列表（" + size + "）");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置从底部弹出
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setAttributes(params);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
