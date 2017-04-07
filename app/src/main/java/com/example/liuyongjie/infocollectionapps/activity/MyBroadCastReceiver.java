package com.example.liuyongjie.infocollectionapps.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.liuyongjie.infocollectionapps.center.DataCenter;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;
import com.example.liuyongjie.infocollectionapps.util.FilePathConstants;
import com.example.liuyongjie.infocollectionapps.util.FileUtil;

import org.json.JSONObject;

/**
 * Created by liuyongjie on 2017/3/29.
 */

public class MyBroadCastReceiver extends BroadcastReceiver {
    private ILogger log = LoggerFactory.getLogger("MyBroadCastReceiver");

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取广播的Action
        String action = intent.getAction();
        log.verbose(Author.liuyongjie, Business.dev_test, "action={}", action);
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {//连接
            WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
            log.verbose(Author.liuyongjie, Business.dev_test, "wifiInfo={}", wifiInfo);
            //如果wifiInfo不为null。表示已经接入一个路由器
            if (wifiInfo != null) {
                DataCenter center = new DataCenter();
                JSONObject wifiJsonObject = center.getWifiJsonObject(LoggerFactory.getRealContext());
                boolean isSuccess = FileUtil.writeFileFromString(FilePathConstants.SENSOR_PATH, wifiJsonObject.toString(), false);
                if (isSuccess) {
                    //发送文件到服务器,并且删掉文件
                }
            }
        }

        if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
            int rssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0);
            int length = WifiManager.calculateSignalLevel(rssi, 100);
            Log.d("TAG", "rssi=" + rssi + "length=" + length);

        }

    }
}
