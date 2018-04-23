package com.example.jackypeng.swangyimusic.rx.bean.radioDetail;

import java.util.List;

/**
 * Created by jackypeng on 2018/4/19.
 * Fragment---电台节目
 */

public class RadioProgramResult {
    private int code;
    private int count;
    private boolean more;
    private List<RadioProgramItem> programs;


    public static class RadioProgramItemMainSong{
        private String id;   //可通过该ID来获取音频信息

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    public static class RadioProgramItem {
        private long duration;
        private long createTime;
        private int listenerCount;
        private RadioProgramItemMainSong mainSong;
        private String coverUrl;
        private String name;
        private String id;
        private String serialNum;

        public RadioProgramItemMainSong getMainSong() {
            return mainSong;
        }

        public void setMainSong(RadioProgramItemMainSong mainSong) {
            this.mainSong = mainSong;
        }

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getListenerCount() {
            return listenerCount;
        }

        public void setListenerCount(int listenerCount) {
            this.listenerCount = listenerCount;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
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
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public List<RadioProgramItem> getPrograms() {
        return programs;
    }

    public void setPrograms(List<RadioProgramItem> programs) {
        this.programs = programs;
    }
}
