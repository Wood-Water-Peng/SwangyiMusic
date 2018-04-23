package com.example.jackypeng.swangyimusic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jackypeng on 2018/4/19.
 */

public class TimeFormatUtil {
    public static final int MONTH_DAY = 1;
    public static final int MINUTE_SECOND = 2;

    public static String format(long millisecond, int format) {
        Date date = new Date(millisecond);
        SimpleDateFormat simpleDateFormat = null;
        switch (format) {
            case MONTH_DAY:
                simpleDateFormat = new SimpleDateFormat("MM-dd");
                break;
            case MINUTE_SECOND:
                simpleDateFormat = new SimpleDateFormat("mm:ss");
                break;
        }
        return simpleDateFormat.format(date).toString();
    }
}
