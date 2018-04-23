
package com.example.jackypeng.swangyimusic.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioDetailResult;
import com.example.jackypeng.swangyimusic.rx.contract.RadioDetailContract;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.RadioDetailModel;
import com.example.jackypeng.swangyimusic.rx.presenter.RadioDetailPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.fragment.BaseFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.ProgramDetailFragment;
import com.example.jackypeng.swangyimusic.ui.fragment.RadioDetailFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 专辑详情
 */
public class RadioDetailActivity extends BaseActivity<RadioDetailContract.Model, RadioDetailContract.Presenter> implements RadioDetailContract.View {
    private String radioId;


    @BindView(R.id.activity_radio_detail_cover)
    ImageView radio_cover;
    @BindView(R.id.activity_radio_detail_slidingTab)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.activity_radio_detail_viewpager)
    ViewPager viewPager;

    @Override
    protected void onInitView(Bundle bundle) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            radioId = extras.getString("rid");
        }
        mPresenter.getRadioDetail(radioId);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(songListAdapter);
//        musicPlayer = MusicPlayer.getInstance();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("详情");
        titles.add("节目");
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                BaseFragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new RadioDetailFragment();
                        break;
                    case 1:
                        fragment = ProgramDetailFragment.newInstance(radioId);
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        slidingTabLayout.setViewPager(viewPager);
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
    protected boolean hasBottomController() {
        return true;
    }

    @Override
    public void getRadioDetailView(RadioDetailResult resultBean) {
        Glide.with(this).load(resultBean.getDjRadio().getPicUrl()).into(radio_cover);
    }
}
