package com.example.jackypeng.swangyimusic.rx.bean;

import java.util.List;

/**
 * Created by jackypeng on 2018/3/6.
 */

public class DiyResultItemBean {
    private int position;
    private String tag;
    private List songidlist;
    private String pic;
    private String title;
    private String type;
    private String listid;
    private int collectnum;
    private int listennum;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List getSongidlist() {
        return songidlist;
    }

    public void setSongidlist(List songidlist) {
        this.songidlist = songidlist;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public int getCollectnum() {
        return collectnum;
    }

    public void setCollectnum(int collectnum) {
        this.collectnum = collectnum;
    }

    public int getListennum() {
        return listennum;
    }

    public void setListennum(int listennum) {
        this.listennum = listennum;
    }
}
