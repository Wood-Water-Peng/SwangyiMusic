package com.example.jackypeng.swangyimusic.rx.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.jackypeng.swangyimusic.R;

import static com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants.DOWNLOADING;
import static com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants.FINISHED;
import static com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants.INIT;
import static com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants.PAUSED;
import static com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants.PREPARING;
import static com.example.jackypeng.swangyimusic.constants.DownloadStatusConstants.WAITING;

/**
 * Created by jackypeng on 2018/3/1.
 */

@SuppressLint("AppCompatCustomView")
public class DownloadButton extends Button {

    private Context mContext;

    public DownloadButton(Context context) {
        this(context, null);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setStatus(int status) {
        switch (status) {
            case INIT:
                setText(mContext.getResources().getString(R.string.btn_status_init));
                setBackground(mContext.getResources().getDrawable(R.drawable.btn_download_bg));
                break;
            case PREPARING:
                setText(mContext.getResources().getString(R.string.btn_status_preparing));
                setBackground(mContext.getResources().getDrawable(R.drawable.btn_download_bg));
                break;
            case WAITING:
                setText(mContext.getResources().getString(R.string.btn_status_waiting));
                setBackground(mContext.getResources().getDrawable(R.drawable.btn_download_bg));
                break;
            case DOWNLOADING:
                setText(mContext.getResources().getString(R.string.btn_status_downloading));
                setBackground(mContext.getResources().getDrawable(R.drawable.btn_download_downloading_bg));
                break;
            case PAUSED:
                setText(mContext.getResources().getString(R.string.btn_status_paused));
                setBackground(mContext.getResources().getDrawable(R.drawable.btn_download_paused_bg));
                break;
            case FINISHED:
                setText(mContext.getResources().getString(R.string.btn_status_finished));
                setBackground(mContext.getResources().getDrawable(R.drawable.btn_download_finished_bg));
                break;
        }
    }
}
