package com.example.jackypeng.swangyimusic.rx.bean.playingListDetail;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/20.
 */

public class PlayingListDetailResult {
    private int code;
    private PlayingListDetailItem result;


    public static class PlayingListDetailItem {
        private List<PlayingListDetailTrack> tracks;
        private String name;
        private String id;
        private String coverImgUrl;
        private JSONObject creator;

        public List<PlayingListDetailTrack> getTracks() {
            return tracks;
        }

        public void setTracks(List<PlayingListDetailTrack> tracks) {
            this.tracks = tracks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public JSONObject getCreator() {
            return creator;
        }

        public void setCreator(JSONObject creator) {
            this.creator = creator;
        }
    }

    public static class PlayingListDetailTrack {
        private String name;
        private String id;
        private String position;

        private JSONArray artists;
        private JSONObject album;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public JSONArray getArtists() {
            return artists;
        }

        public void setArtists(JSONArray artists) {
            this.artists = artists;
        }

        public JSONObject getAlbum() {
            return album;
        }

        public void setAlbum(JSONObject album) {
            this.album = album;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PlayingListDetailItem getResult() {
        return result;
    }

    public void setResult(PlayingListDetailItem result) {
        this.result = result;
    }


}
