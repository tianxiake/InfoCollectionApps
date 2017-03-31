package com.example.liuyongjie.infocollectionapps.utils;

import android.util.Log;

import static android.R.attr.start;

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
