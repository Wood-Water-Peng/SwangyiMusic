package com.example.jackypeng.swangyimusic.rx.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by jackypeng on 2018/4/25.
 */

public class LoginResultBean {
    private int code;     //200-成功    502-密码错误     501-账号不存在
    private Account account;
    private Profile profile;

    public int getCode() {
        return code;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class Account {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Profile {
        private String avatarUrl;
        private String backgroundUrl;
        private String nickname;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getBackgroundUrl() {
            return backgroundUrl;
        }

        public void setBackgroundUrl(String backgroundUrl) {
            this.backgroundUrl = backgroundUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
