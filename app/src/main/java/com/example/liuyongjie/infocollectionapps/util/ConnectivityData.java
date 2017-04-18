package com.example.liuyongjie.infocollectionapps.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by liuyongjie on 2017/4/14.
 * 这个类主要是ConnectivityManager中的信息
 */

public class ConnectivityData {
    private ConnectivityManager connectivityManager;

    public ConnectivityData(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }




}
