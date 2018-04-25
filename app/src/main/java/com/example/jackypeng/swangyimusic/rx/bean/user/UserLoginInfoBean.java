package com.example.jackypeng.swangyimusic.rx.bean.user;

/**
 * Created by jackypeng on 2018/4/25.
 * <p>
 * 用户的登录信息，简单信息，如id等
 */

public class UserLoginInfoBean {
    private static UserLoginInfoBean sInstance;

    private UserLoginInfoBean() {
    }

    public static UserLoginInfoBean getInstance() {
        synchronized (UserLoginInfoBean.class) {
            if (sInstance == null) {
                sInstance = new UserLoginInfoBean();
            }
        }
        return sInstance;
    }

    private String userId;
    private boolean isUserLogined;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserIsLogined(boolean flag) {
        this.isUserLogined = flag;
    }
}
