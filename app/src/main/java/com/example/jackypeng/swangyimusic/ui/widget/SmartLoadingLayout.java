package com.example.jackypeng.swangyimusic.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.jackypeng.swangyimusic.R;

/**
 * Created by jackypeng on 2017/12/15.
 */

public class SmartLoadingLayout extends FrameLayout implements View.OnClickListener {

    public static final int LOADING = 1;   //加载中
    public static final int SUCCESS = 2;//加载完成
    public static final int ERROR = 3; //加载错误
    public static final int EMPTY = 4; //空页面

    private final LayoutInflater mInflater;
    protected View mLoadingView;
    protected View mContentView;
    protected View mEmptyView;
    protected View mErrorView;
    private int mContainerId = 0;

    public SmartLoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public SmartLoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = LayoutInflater.from(context);
        {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SmartLoadingLayout, 0, 0);
            mContainerId = arr.getResourceId(R.styleable.SmartLoadingLayout_sll_content, mContainerId);

            mLoadingView = mInflater.inflate(R.layout.smartloadinglayout_view_on_loading, null);
            mEmptyView = mInflater.inflate(R.layout.smartloadinglayout_view_on_empty, null);
            mErrorView = mInflater.inflate(R.layout.smartloadinglayout_view_on_error, null);
            addView(mLoadingView);
            addView(mEmptyView);
            addView(mErrorView);
            arr.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (mContentView == null && mContainerId != 0) {
            mContentView = findViewById(mContainerId);
        }
        String loading = mLoadingView.getClass().getCanonicalName();
        String empty = mEmptyView.getClass().getCanonicalName();
    }

    public void setOnReloadClicked(OnClickListener listener) {
        if (listener != null) {
            mErrorView.findViewById(R.id.general_error_view_btn).setOnClickListener(listener);
            onLoading();
        }
    }

    public void onLoading() {
        updateViewStatus(LOADING);
    }

    public void onSuccess() {
        updateViewStatus(SUCCESS);
    }

    public void onError() {
        updateViewStatus(ERROR);
    }

    public void onEmpty() {
        updateViewStatus(EMPTY);
    }

    private void updateViewStatus(int status) {
        if (mContentView == null) {
            throw new NullPointerException("The loading view with this layout was not set");
        }
        switch (status) {
            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                mEmptyView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mContentView.setVisibility(GONE);
                break;
            case SUCCESS:
                mContentView.setVisibility(VISIBLE);
                mLoadingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                break;
            case ERROR:
                mErrorView.setVisibility(VISIBLE);
                mContentView.setVisibility(GONE);
                mLoadingView.setVisibility(GONE);
                mEmptyView.setVisibility(GONE);
                break;
            case EMPTY:
                mEmptyView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mContentView.setVisibility(GONE);
                mLoadingView.setVisibility(GONE);
                break;
        }
    }

    public boolean isContentViewVisible() {
        if (mContentView != null) {
            return mContentView.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.general_error_view_btn:
                onLoading();
                break;
        }
    }
}
