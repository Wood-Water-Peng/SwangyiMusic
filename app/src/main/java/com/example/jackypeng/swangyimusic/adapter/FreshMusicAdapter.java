package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jackypeng.swangyimusic.MainApplication;
import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.constants.ResponseErrorCode;
import com.example.jackypeng.swangyimusic.rx.bean.DiyResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.FreshMusicResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.Mix_1_ResultBean;
import com.example.jackypeng.swangyimusic.rx.bean.RadioResultBean;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * Created by jackypeng on 2018/3/7.
 */

public class FreshMusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private FreshMusicResultBean freshMusicResultData;
    private Context mContext;
    private List<String> bannerUrl;

    public FreshMusicAdapter(Context context) {
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
        } else {
            return new BottomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_music_bottom_holder, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (freshMusicResultData == null || holder == null) return;
        if (getItemViewType(position) == TYPE_4 && bannerUrl != null) {
            CarouselHolder carouselHolder = (CarouselHolder) holder;
            carouselHolder.banner.setPageChangeDuration(2000);
            carouselHolder.banner.setData(bannerUrl, null);
        } else if (getItemViewType(position) == TYPE_1) {
            ColumnHolder columnHolder = (ColumnHolder) holder;
            RadioResultBean radioResultBean = freshMusicResultData.getRadioResultBean();
            columnHolder.columnName.setText("主播电台");
            columnHolder.columnIcon.setImageResource(R.mipmap.recommend_playlist);
            columnHolder.more.setText("更多>");
            columnHolder.columnList.setLayoutManager(new GridLayoutManager(mContext, 3));
            FreshMusicColumnRadioAdapter adapter = new FreshMusicColumnRadioAdapter(mContext);
            columnHolder.columnList.setAdapter(adapter);
            adapter.setData(radioResultBean);
        } else if (getItemViewType(position) == TYPE_2) {
            ColumnHolder columnHolder = (ColumnHolder) holder;
            DiyResultBean diyResultBean = freshMusicResultData.getDiy();
            columnHolder.columnName.setText("新专辑上架");
            columnHolder.columnIcon.setImageResource(R.mipmap.recommend_radio);
            columnHolder.more.setText("更多>");
            if (diyResultBean.getError_code() == ResponseErrorCode.ERROR) {
                return;
            } else if (diyResultBean.getError_code() == ResponseErrorCode.SUCCESS) {
                columnHolder.columnList.setLayoutManager(new GridLayoutManager(mContext, 3));
                FreshMusicColumnDiyAdapter adapter = new FreshMusicColumnDiyAdapter(mContext);
                adapter.setData(diyResultBean);
                columnHolder.columnList.setAdapter(adapter);
            }
        } else if (getItemViewType(position) == TYPE_3) {
            ColumnHolder columnHolder = (ColumnHolder) holder;
            Mix_1_ResultBean mix1ResultBean = freshMusicResultData.getMix_1_resultBean();
            columnHolder.columnName.setText("推荐歌单");
            columnHolder.columnIcon.setImageResource(R.mipmap.recommend_single);
            columnHolder.more.setText("更多>");
            columnHolder.columnList.setLayoutManager(new GridLayoutManager(mContext, 3));
            FreshMusicColumnMix_1_Adapter adapter = new FreshMusicColumnMix_1_Adapter(mContext);
            adapter.setData(mix1ResultBean);
            columnHolder.columnList.setAdapter(adapter);
        }
    }

    public static final int TYPE_1 = 0xff01;  //推荐歌单
    public static final int TYPE_2 = 0xff02;  //新专辑上架
    public static final int TYPE_3 = 0xff03;  //主播电台
    public static final int TYPE_4 = 0xff04;  //顶部轮播图
    public static final int TYPE_5 = 0xff05;  //栏目列表
    public static final int TYPE_6 = 0xff06;  //底部

    @Override
    public int getItemViewType(int position) {
        int bottom_index = getItemCount() - 1;
        if (position == 0) {
            return TYPE_4;

        } else if (position == 1) {

            return TYPE_5;
        } else if (position == 2) {

            return TYPE_1;
        } else if (position == 3) {

            return TYPE_2;
        } else if (position == 4) {

            return TYPE_3;
        } else if (position == bottom_index) {

            return TYPE_6;
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        if (freshMusicResultData == null) {
            return 0;
        } else {
            return 6;
        }
    }

    public void setData(FreshMusicResultBean freshMusicResult) {
        if (freshMusicResult != null) {
            this.freshMusicResultData = freshMusicResult;
            notifyDataSetChanged();
        }
    }

    public void setCarouselData(List<String> bannerUrl) {
        if (bannerUrl != null && bannerUrl.size() > 0) {
            this.bannerUrl = bannerUrl;
            notifyDataSetChanged();
        }
    }

    private static class ColumnHolder extends RecyclerView.ViewHolder {
        ImageView columnIcon;
        TextView columnName;   //栏目名称
        TextView more;
        RecyclerView columnList;  //栏目列表

        public ColumnHolder(View itemView) {
            super(itemView);
            columnIcon = (ImageView) itemView.findViewById(R.id.item_fresh_music_column_icon);
            columnName = (TextView) itemView.findViewById(R.id.item_fresh_music_column_name);
            more = (TextView) itemView.findViewById(R.id.item_fresh_music_column_more);
            columnList = (RecyclerView) itemView.findViewById(R.id.item_fresh_music_column_recycle_view);
        }
    }

    //轮播图
    static class CarouselHolder extends RecyclerView.ViewHolder {
        BGABanner banner;

        public CarouselHolder(View itemView) {
            super(itemView);
            banner = (BGABanner) itemView.findViewById(R.id.item_fresh_music_carousel_holder_banner);
            banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                @Override
                public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                    Glide.with(MainApplication.getAppContext())
                            .load(model)
                            .into(itemView);
                }
            });
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
