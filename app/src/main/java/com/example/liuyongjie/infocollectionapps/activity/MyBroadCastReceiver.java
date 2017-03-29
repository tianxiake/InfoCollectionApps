package com.example.liuyongjie.infocollectionapps.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by liuyongjie on 2017/3/29.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取广播的Action
        String action = intent.getAction();
        Log.d("TAG", action);
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
//            Bundle bundle = intent.getExtras();
//            String bssid = bundle.getString(WifiManager.EXTRA_BSSID, "");
//            String netwokrInfo = bundle.getString(WifiManager.EXTRA_NETWORK_INFO, "");
//            String wifiInfo = bundle.getString(WifiManager.EXTRA_WIFI_INFO, "");
            String bssid = intent.getStringExtra(WifiManager.EXTRA_BSSID);
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            Log.d("TAG", "bssid=" + bssid);
            Log.d("TAG", "wifiInfo=" + wifiInfo);
            Log.d("TAG", "networkInfo=" + networkInfo);
        }
        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int now = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
            int pre = intent.getIntExtra(WifiManager.EXTRA_PREVIOUS_WIFI_STATE, -1);
            Log.d("TAG", now + " " + pre);
//            Bundle bundle = intent.getExtras();
//            String previous = bundle.getString(WifiManager.EXTRA_PREVIOUS_WIFI_STATE);
//            String now = bundle.getString(WifiManager.EXTRA_WIFI_STATE);
//            Log.d("TAG", previous + " " + now);

        }


    }
}
