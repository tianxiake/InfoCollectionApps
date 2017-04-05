package com.example.liuyongjie.infocollectionapps.util;

import android.util.Log;

/**
 * Created by liuyongjie on 201o/3/31.
 */

public class TimeUtil {

    public static long currentTime(String tag, String message) {
        long time = System.currentTimeMillis();
        Log.d(tag, message + "=" + time);
        return time;
    }
}
