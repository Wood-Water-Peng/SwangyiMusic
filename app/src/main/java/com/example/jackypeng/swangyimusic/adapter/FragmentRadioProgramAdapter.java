package com.example.jackypeng.swangyimusic.adapter;

import android.content.Context;
import android.os.RemoteException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.rx.bean.LocalMusicDetailInfo;
import com.example.jackypeng.swangyimusic.rx.bean.radioDetail.RadioProgramResult;
import com.example.jackypeng.swangyimusic.service.AlbumListItemTrack;
import com.example.jackypeng.swangyimusic.service.MusicPlayer;
import com.example.jackypeng.swangyimusic.util.TimeFormatUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jackypeng on 2018/3/7.
 */

public class FragmentRadioProgramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RadioProgramResult radioProgramResult;
    private List<RadioProgramResult.RadioProgramItem> radioProgramItems;
    private Context mContext;

    public FragmentRadioProgramAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RadioProgramColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_radio_program_adapter_item_holder, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (radioProgramResult == null) return;

        final List<RadioProgramResult.RadioProgramItem> listRadioItems = radioProgramResult.getPrograms();
        final RadioProgramResult.RadioProgramItem item = listRadioItems.get(position);
        final RadioProgramColumnHolder columnHolder = (RadioProgramColumnHolder) holder;
        columnHolder.index.setText(item.getSerialNum());
        columnHolder.name.setText(item.getName());
//        columnHolder.listenerCount.setText(item.getListenerCount());
        columnHolder.duration.setText(TimeFormatUtil.format(item.getDuration(), TimeFormatUtil.MINUTE_SECOND));
        columnHolder.createTime.setText(TimeFormatUtil.format(item.getCreateTime(), TimeFormatUtil.MONTH_DAY));

        columnHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将专辑中的歌曲列表传递过去,需要一个唯一值来作为该数组的key
                //将专辑中的歌曲列表传递过去
                String[] musicIds = new String[radioProgramItems.size()];
                HashMap<String, AlbumListItemTrack> musicMap = new HashMap<>();
                for (int i = 0; i < radioProgramItems.size(); i++) {
                    RadioProgramResult.RadioProgramItem bean = radioProgramItems.get(i);
                    AlbumListItemTrack itemTrack = new AlbumListItemTrack();
                    itemTrack.setSongId(bean.getMainSong().getId());
                    itemTrack.setSongName(bean.getName());
                    itemTrack.setLocal(false);
                    musicIds[i] = bean.getMainSong().getId();
                    musicMap.put(bean.getMainSong().getId(), itemTrack);
                }
                try {
                    MusicPlayer.getInstance().playAll(musicIds, musicMap, columnHolder.getAdapterPosition());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (radioProgramResult != null) {
            return radioProgramResult.getPrograms().size();
        } else {
            return 0;
        }
    }

    public void setData(RadioProgramResult result) {
        if (result != null) {
            this.radioProgramResult = result;
            this.radioProgramItems = radioProgramResult.getPrograms();
            notifyDataSetChanged();
        }
    }

    private static class RadioProgramColumnHolder extends RecyclerView.ViewHolder {

        TextView index;
        TextView name;
        TextView listenerCount;
        TextView duration;
        TextView createTime;

        RadioProgramColumnHolder(View itemView) {
            super(itemView);
            index = (TextView) itemView.findViewById(R.id.item_fragment_radio_adapter_item_holder_serial_index);
            name = (TextView) itemView.findViewById(R.id.item_fragment_radio_adapter_item_holder_name);
            listenerCount = (TextView) itemView.findViewById(R.id.item_fragment_radio_adapter_item_holder_count);
            duration = (TextView) itemView.findViewById(R.id.item_fragment_radio_adapter_item_holder_duration);
            createTime = (TextView) itemView.findViewById(R.id.item_fragment_radio_adapter_item_holder_create_time);
        }
    }
}
