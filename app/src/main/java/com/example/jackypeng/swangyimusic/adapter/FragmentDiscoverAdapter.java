package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.BroadcastConstants;
import com.example.jackypeng.swangyimusic.constants.ResponseErrorCode;
import com.example.jackypeng.swangyimusic.constants.ResultCode;
import com.example.jackypeng.swangyimusic.rx.bean.DiyResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.Mix_1_ResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.BannerResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.discover.ExclusivePartResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendListResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendMVResult;
import com.example.jackypeng.swangyimusic.rx.bean.discover.RecommendRadioResult;
import com.example.jackypeng.swangyimusic.ui.itemDecoration.GridSpacingItemDecoration;
import com.example.jackypeng.swangyimusic.ui.widget.SmartLoadingLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * Created by jackypeng on 2018/3/7.
 */

public class FragmentDiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private BannerResultBean bannerResultBean;
    private RecommendListResult recommendListResult;
    private RecommendMVResult recommendMVResult;
    private ExclusivePartResult exclusivePartResult;
    private RecommendRadioResult recommendRadioResult;

    private boolean fetchBannerError = false;
    private boolean fetchRecommendListError = false;
    private boolean fetchRecommendRadioError = false;
    private boolean fetchExclusivePartError = false;

    public FragmentDiscoverAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_4) {
            return new CarouselHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_carousel_holder, parent, false));
        } else if (viewType == TYPE_5) {
            return new ForumHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_forum_holder, parent, false));
        } else if (viewType == TYPE_1 || viewType == TYPE_2 || viewType == TYPE_3) {
            return new ColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_column_holder, parent, false));
        } else if (viewType == TYPE_8) {
            return new ColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_column_holder, parent, false));
        } else {
            return new BottomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_bottom_holder, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_4) {
            final CarouselHolder carouselHolder = (CarouselHolder) holder;
            if (fetchBannerError && bannerResultBean == null) {
                carouselHolder.smartLoadingLayout.onError();
                carouselHolder.smartLoadingLayout.setOnReloadClicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reload banner data
                        carouselHolder.smartLoadingLayout.onLoading();
                        mContext.sendBroadcast(new Intent(BroadcastConstants.LOAD_BANNER));
                    }
                });
            } else if (!fetchBannerError && bannerResultBean != null) {
                carouselHolder.banner.setPageChangeDuration(2000);
                ArrayList<String> urls = new ArrayList<>();
                List<JSONObject> banners = bannerResultBean.getBanners();
                for (int i = 0; i < banners.size(); i++) {
                    urls.add(banners.get(i).getString("pic"));
                }
                carouselHolder.banner.setData(urls, null);
                carouselHolder.smartLoadingLayout.onSuccess();
            }
        } else if (getItemViewType(position) == TYPE_3) {     //独家放送
            final ColumnHolder columnHolder = (ColumnHolder) holder;
            if (fetchExclusivePartError && exclusivePartResult == null) {
                columnHolder.smartLoadingLayout.onError();
                columnHolder.smartLoadingLayout.setOnReloadClicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reload banner data
                        columnHolder.smartLoadingLayout.onLoading();
                        mContext.sendBroadcast(new Intent(BroadcastConstants.LOAD_EXCLUSIVE_PART));
                    }
                });
            } else if (!fetchExclusivePartError && exclusivePartResult != null) {
                columnHolder.columnName.setText("独家放送");
                columnHolder.columnIcon.setImageResource(R.mipmap.recommend_playlist);
                columnHolder.columnList.setLayoutManager(new GridLayoutManager(mContext, 2));
                columnHolder.columnList.addItemDecoration(new GridSpacingItemDecoration(2, 15, true));
                ExclusivePartAdapter adapter = new ExclusivePartAdapter(mContext);
                columnHolder.columnList.setAdapter(adapter);
                adapter.setData(exclusivePartResult);
                columnHolder.smartLoadingLayout.onSuccess();
            }
        } else if (getItemViewType(position) == TYPE_8) {
            final ColumnHolder columnHolder = (ColumnHolder) holder;
            if (fetchRecommendRadioError && recommendRadioResult == null) {
                columnHolder.smartLoadingLayout.onError();
                columnHolder.smartLoadingLayout.setOnReloadClicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reload banner data
                        columnHolder.smartLoadingLayout.onLoading();
                        mContext.sendBroadcast(new Intent(BroadcastConstants.LOAD_RECOMMEND_RADIO));
                    }
                });
            } else if (!fetchRecommendRadioError && recommendRadioResult != null) {
                columnHolder.columnName.setText("推荐电台");
                columnHolder.columnIcon.setImageResource(R.mipmap.recommend_radio);
                if (recommendRadioResult.isMore()) {
                    columnHolder.more.setText("更多>");
                }
                columnHolder.columnList.setLayoutManager(new GridLayoutManager(mContext, 3));
                columnHolder.columnList.addItemDecoration(new GridSpacingItemDecoration(2, 15, true));
                RecommendRadioListAdapter adapter = new RecommendRadioListAdapter(mContext);
                adapter.setData(recommendRadioResult);
                columnHolder.columnList.setAdapter(adapter);
                columnHolder.smartLoadingLayout.onSuccess();
            }
        } else if (getItemViewType(position) == TYPE_1) {
            final ColumnHolder columnHolder = (ColumnHolder) holder;
            if (fetchRecommendListError && recommendListResult == null) {
                columnHolder.smartLoadingLayout.onError();
                columnHolder.smartLoadingLayout.setOnReloadClicked(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //reload banner data
                        columnHolder.smartLoadingLayout.onLoading();
                        mContext.sendBroadcast(new Intent(BroadcastConstants.LOAD_RECOMMEND_LIST));
                    }
                });
            } else if (!fetchRecommendListError && recommendListResult != null) {
                columnHolder.columnName.setText("推荐歌单");
                columnHolder.columnIcon.setImageResource(R.mipmap.recommend_single);
                columnHolder.more.setText("更多>");
                columnHolder.columnList.setLayoutManager(new GridLayoutManager(mContext, 3));
                columnHolder.columnList.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
                RecommendMusicListAdapter adapter = new RecommendMusicListAdapter(mContext);
                adapter.setData(recommendListResult);
                columnHolder.columnList.setAdapter(adapter);
                columnHolder.smartLoadingLayout.onSuccess();
            }

        }
    }

    public static final int TYPE_1 = 0xff01;  //推荐歌单
    public static final int TYPE_2 = 0xff02;  //新专辑上架
    public static final int TYPE_3 = 0xff03;  //独家放送
    public static final int TYPE_4 = 0xff04;  //顶部轮播图
    public static final int TYPE_5 = 0xff05;  //栏目列表
    public static final int TYPE_6 = 0xff06;  //底部
    //    public static final int TYPE_7 = 0xff07;  //推荐MV
    public static final int TYPE_8 = 0xff08;  //推荐电台

    @Override
    public int getItemViewType(int position) {
        int bottom_index = getItemCount() - 1;
        if (position == 0) {
            return TYPE_4;           //轮播图

        } else if (position == 1) {

            return TYPE_5;           //栏目
        } else if (position == 2) {

            return TYPE_1;            //推荐歌单
        } else if (position == 3) {

            return TYPE_3;          // 独家放送
        } else if (position == 4) {

            return TYPE_8;      //推荐电台
        } else if (position == bottom_index) {

            return TYPE_6;
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        if (bannerResultBean != null) {
            return 5;
        } else {
            return 5;
        }
    }


    public void setBanner(BannerResultBean resultBean) {
        if (resultBean != null && ResultCode.SUCCESS == resultBean.getCode()) {
            this.bannerResultBean = resultBean;
            notifyDataSetChanged();
            fetchBannerError = false;
        }
    }

    public void setRecommendList(RecommendListResult resultBean) {
        if (resultBean != null && ResultCode.SUCCESS == resultBean.getCode()) {
            this.recommendListResult = resultBean;
            notifyDataSetChanged();
        }
    }

    public void setRecommendRadio(RecommendRadioResult resultBean) {
        if (resultBean != null && ResultCode.SUCCESS == resultBean.getCode()) {
            this.recommendRadioResult = resultBean;
            notifyDataSetChanged();
        }
    }

    public void setRecommendMV(RecommendMVResult resultBean) {
        if (resultBean != null && ResultCode.SUCCESS == resultBean.getCode()) {
            this.recommendMVResult = resultBean;
            notifyDataSetChanged();
        }
    }

    public void setExclusivePart(ExclusivePartResult resultBean) {
        if (resultBean != null && ResultCode.SUCCESS == resultBean.getCode()) {
            this.exclusivePartResult = resultBean;
            notifyDataSetChanged();
        }
    }

    public void setBannerError(String msg) {    //抓取轮播图数据错误
        fetchBannerError = true;
        notifyDataSetChanged();
    }

    public void setRecommendListError(String msg) {    //抓取轮播图数据错误
        fetchRecommendListError = true;
        notifyDataSetChanged();
    }

    public void setRecommendRadioError(String msg) {    //抓取轮播图数据错误
        fetchRecommendRadioError = true;
        notifyDataSetChanged();
    }

    public void setExclusivePartError(String msg) {    //抓取轮播图数据错误
        fetchExclusivePartError = true;
        notifyDataSetChanged();
    }

    private static class ColumnHolder extends RecyclerView.ViewHolder {
        ImageView columnIcon;
        TextView columnName;   //栏目名称
        TextView more;
        RecyclerView columnList;  //栏目列表
        SmartLoadingLayout smartLoadingLayout;

        public ColumnHolder(View itemView) {
            super(itemView);
            columnIcon = (ImageView) itemView.findViewById(R.id.item_fresh_music_column_icon);
            columnName = (TextView) itemView.findViewById(R.id.item_fresh_music_column_name);
            more = (TextView) itemView.findViewById(R.id.item_fresh_music_column_more);
            columnList = (RecyclerView) itemView.findViewById(R.id.item_fresh_music_column_recycle_view);
            smartLoadingLayout = (SmartLoadingLayout) itemView.findViewById(R.id.item_fresh_music_column_recycle_view_sll);
            smartLoadingLayout.onLoading();
        }
    }

    //轮播图
    static class CarouselHolder extends RecyclerView.ViewHolder {
        BGABanner banner;
        SmartLoadingLayout smartLoadingLayout;

        public CarouselHolder(View itemView) {
            super(itemView);
            banner = (BGABanner) itemView.findViewById(R.id.item_fresh_music_carousel_holder_banner);
            smartLoadingLayout = (SmartLoadingLayout) itemView.findViewById(R.id.item_fresh_music_carousel_holder_banner_sll);
            banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                    Glide.with(MainApplication.getAppContext())
                            .load(model)
                            .into(itemView);
                }
            });
            smartLoadingLayout.onLoading();
        }
    }

    //版块
    static class ForumHolder extends RecyclerView.ViewHolder {
        RecyclerView columnList;  //栏目列表

        public ForumHolder(View itemView) {
            super(itemView);
        }
    }

    //底部
    static class BottomHolder extends RecyclerView.ViewHolder {

        public BottomHolder(View itemView) {
            super(itemView);
        }
    }
}
