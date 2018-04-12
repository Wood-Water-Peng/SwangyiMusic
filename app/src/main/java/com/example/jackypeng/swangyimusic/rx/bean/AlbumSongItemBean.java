package com.example.jackypeng.swangyimusic.rx.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jackypeng on 2018/3/7.
 * 专辑详情中的歌曲信息
 */

public class AlbumSongItemBean implements Parcelable{
    private String title;
    private String song_id;
    private String author;
    private String album_id;
    private String album_title;
    private String relate_status;
    private String is_charge;
    private String all_rate;
    private String high_rate;
    private String all_artist_id;
    private String copy_type;
    private String pic_big ;
    private String pic_small ;
    private int has_mv;
    private String toneid;
    private String resource_type;
    private String is_ksong;
    private int has_mv_mobile;
    private String ting_uid;
    private int is_first_publish;
    private int havehigh;
    private int charge;
    private int learn;
    private String song_source;
    private String piao_id;
    private String korean_bb_song;
    private String resource_type_ext;
    private String mv_provider;
    private String share;
    private String lrclink;

    protected AlbumSongItemBean(Parcel in) {
        title = in.readString();
        song_id = in.readString();
        author = in.readString();
        album_id = in.readString();
        album_title = in.readString();
        relate_status = in.readString();
        is_charge = in.readString();
        all_rate = in.readString();
        high_rate = in.readString();
        all_artist_id = in.readString();
        copy_type = in.readString();
        pic_big = in.readString();
        pic_small = in.readString();
        has_mv = in.readInt();
        toneid = in.readString();
        resource_type = in.readString();
        is_ksong = in.readString();
        has_mv_mobile = in.readInt();
        ting_uid = in.readString();
        is_first_publish = in.readInt();
        havehigh = in.readInt();
        charge = in.readInt();
        learn = in.readInt();
        song_source = in.readString();
        piao_id = in.readString();
        korean_bb_song = in.readString();
        resource_type_ext = in.readString();
        mv_provider = in.readString();
        share = in.readString();
        lrclink = in.readString();
    }

    public static final Creator<AlbumSongItemBean> CREATOR = new Creator<AlbumSongItemBean>() {
        @Override
        public AlbumSongItemBean createFromParcel(Parcel in) {
            return new AlbumSongItemBean(in);
        }

        @Override
        public AlbumSongItemBean[] newArray(int size) {
            return new AlbumSongItemBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public String getRelate_status() {
        return relate_status;
    }

    public void setRelate_status(String relate_status) {
        this.relate_status = relate_status;
    }

    public String getIs_charge() {
        return is_charge;
    }

    public void setIs_charge(String is_charge) {
        this.is_charge = is_charge;
    }

    public String getAll_rate() {
        return all_rate;
    }

    public void setAll_rate(String all_rate) {
        this.all_rate = all_rate;
    }

    public String getHigh_rate() {
        return high_rate;
    }

    public void setHigh_rate(String high_rate) {
        this.high_rate = high_rate;
    }

    public String getAll_artist_id() {
        return all_artist_id;
    }

    public void setAll_artist_id(String all_artist_id) {
        this.all_artist_id = all_artist_id;
    }

    public String getCopy_type() {
        return copy_type;
    }

    public void setCopy_type(String copy_type) {
        this.copy_type = copy_type;
    }

    public int getHas_mv() {
        return has_mv;
    }

    public void setHas_mv(int has_mv) {
        this.has_mv = has_mv;
    }

    public String getToneid() {
        return toneid;
    }

    public void setToneid(String toneid) {
        this.toneid = toneid;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getIs_ksong() {
        return is_ksong;
    }

    public void setIs_ksong(String is_ksong) {
        this.is_ksong = is_ksong;
    }

    public int getHas_mv_mobile() {
        return has_mv_mobile;
    }

    public void setHas_mv_mobile(int has_mv_mobile) {
        this.has_mv_mobile = has_mv_mobile;
    }

    public String getTing_uid() {
        return ting_uid;
    }

    public void setTing_uid(String ting_uid) {
        this.ting_uid = ting_uid;
    }

    public int getIs_first_publish() {
        return is_first_publish;
    }

    public void setIs_first_publish(int is_first_publish) {
        this.is_first_publish = is_first_publish;
    }

    public int getHavehigh() {
        return havehigh;
    }

    public void setHavehigh(int havehigh) {
        this.havehigh = havehigh;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getLearn() {
        return learn;
    }

    public void setLearn(int learn) {
        this.learn = learn;
    }

    public String getSong_source() {
        return song_source;
    }

    public void setSong_source(String song_source) {
        this.song_source = song_source;
    }

    public String getPiao_id() {
        return piao_id;
    }

    public void setPiao_id(String piao_id) {
        this.piao_id = piao_id;
    }

    public String getKorean_bb_song() {
        return korean_bb_song;
    }

    public void setKorean_bb_song(String korean_bb_song) {
        this.korean_bb_song = korean_bb_song;
    }

    public String getLrclink() {
        return lrclink;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    public String getResource_type_ext() {
        return resource_type_ext;
    }

    public void setResource_type_ext(String resource_type_ext) {
        this.resource_type_ext = resource_type_ext;
    }

    public String getMv_provider() {
        return mv_provider;
    }

    public void setMv_provider(String mv_provider) {
        this.mv_provider = mv_provider;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getPic_small() {
        return pic_small;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(song_id);
        dest.writeString(author);
        dest.writeString(album_id);
        dest.writeString(album_title);
        dest.writeString(relate_status);
        dest.writeString(is_charge);
        dest.writeString(all_rate);
        dest.writeString(high_rate);
        dest.writeString(all_artist_id);
        dest.writeString(copy_type);
        dest.writeString(pic_big);
        dest.writeString(pic_small);
        dest.writeInt(has_mv);
        dest.writeString(toneid);
        dest.writeString(resource_type);
        dest.writeString(is_ksong);
        dest.writeInt(has_mv_mobile);
        dest.writeString(ting_uid);
        dest.writeInt(is_first_publish);
        dest.writeInt(havehigh);
        dest.writeInt(charge);
        dest.writeInt(learn);
        dest.writeString(song_source);
        dest.writeString(piao_id);
        dest.writeString(korean_bb_song);
        dest.writeString(resource_type_ext);
        dest.writeString(mv_provider);
        dest.writeString(share);
        dest.writeString(lrclink);
    }
}
