package com.example.jackypeng.swangyimusic.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.adapter.FragmentRadioProgramAdapter;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.rx.contract.RadioProgramContract;
import com.example.jackypeng.swangyimusic.rx.model.BaseModel;
import com.example.jackypeng.swangyimusic.rx.model.RadioProgramModel;
import com.example.jackypeng.swangyimusic.rx.presenter.BasePresenter;
import com.example.jackypeng.swangyimusic.rx.presenter.RadioProgramPresenter;
import com.example.jackypeng.swangyimusic.rx.view.rxView.BaseView;
import com.example.jackypeng.swangyimusic.ui.widget.SmartLoadingLayout;

import butterknife.BindView;

/**
 * Created by jackypeng on 2018/4/18.
 * 电台节目
 */

public class ProgramDetailFragment extends BaseFragment<RadioProgramContract.Model, RadioProgramContract.Presenter> implements RadioProgramContract.View {


    @BindView(R.id.fragment_program_detail_smartloadinglayout)
    SmartLoadingLayout smartLoadingLayout;
    @BindView(R.id.fragment_program_detail_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_program_detail_serial_count)
    TextView tv_serial_num;
    private String rid;
    private FragmentRadioProgramAdapter adapter;

    public static ProgramDetailFragment newInstance(String rid) {
        Bundle bundle = new Bundle();
        bundle.putString("rid", rid);
        ProgramDetailFragment fragment = new ProgramDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_program_detail;
    }

    @Override
    protected BaseView getViewImpl() {
        return this;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            rid = bundle.getString("rid");
        }
        adapter = new FragmentRadioProgramAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void lazyFetchData() {
        smartLoadingLayout.onLoading();
        mPresenter.getRadioProgramList(rid);
    }

    @Override
    protected RadioProgramContract.Model getModel() {
        return new RadioProgramModel();
    }

    @Override
    protected RadioProgramContract.Presenter getPresenter() {
        return new RadioProgramPresenter();
    }

    @Override
    public void showErrorWithStatus(String msg) {
        smartLoadingLayout.onError();
    }

    @Override
    public void getRadioProgramListView(RadioProgramResult resultBean) {
        smartLoadingLayout.onSuccess();
        tv_serial_num.setText("共" + resultBean.getCount() + "期");
        adapter.setData(resultBean);
    }
}
