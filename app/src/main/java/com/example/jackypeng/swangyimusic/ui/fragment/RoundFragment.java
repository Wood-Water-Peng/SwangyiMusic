package com.example.jackypeng.swangyimusic.ui.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/3/10.
 * 显示播放详情中的原形碟片
 */

public class RoundFragment extends BaseFragment {

    private ObjectAnimator rotate;
    public static final String TAG = "RoundFragment";

    public static RoundFragment newInstance(String url, boolean shouldDoRotate) {
        RoundFragment roundFragment = new RoundFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("shouldDoRotate", shouldDoRotate);
        roundFragment.setArguments(bundle);
        return roundFragment;
    }

    public void setRotatePending(boolean shouldDoRotate) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBoolean("shouldDoRotate", shouldDoRotate);
        setArguments(bundle);
    }

    public static RoundFragment newInstance(String url) {
        return newInstance(url, false);
    }

    @BindView(R.id.fragment_round_album_cover)
    SimpleDraweeView album_cover;
    @BindView(R.id.content)
    View content;
    private boolean shouldDoRotate = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_round;
    }

    @Override
    protected BaseView getViewImpl() {
        return null;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            album_cover.setImageURI(bundle.getString("url"));
            shouldDoRotate = bundle.getBoolean("shouldDoRotate");
        }
        initAnim();
    }

    private void initAnim() {
//        Log.i(TAG, "---initAnim---:" + toString());
        rotate = ObjectAnimator.ofFloat(content, "rotation", 0f, 360f);//添加旋转动画，旋转中心默认为控件中点
        rotate.setDuration(20000);//设置动画时间
        rotate.setInterpolator(new LinearInterpolator());//动画时间线性渐变
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setRepeatMode(ObjectAnimator.RESTART);
        if (shouldDoRotate) {
            startRotate();
        }
    }

    //
    public void startRotate() {
        Log.i(TAG, "---startRotate---:" + toString());
        if (rotate == null) {
            initAnim();
        }
        if (rotate.isStarted()) {
            Log.i(TAG, "---isStarted---");
            rotate.resume();
        } else {
            rotate.start();
        }
    }

    public void resumeRotate() {
        Log.i(TAG, "---resumeRotate---");
        if (rotate != null) {
            rotate.resume();
        } else {
            Log.i(TAG, "---rotate==null---");
        }
    }

    public void pauseRotate() {
        Log.i(TAG, "---pauseRotate---");
        if (rotate != null) {
            rotate.pause();
        }
    }

    @Override
    protected BaseModel getModel() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

}
