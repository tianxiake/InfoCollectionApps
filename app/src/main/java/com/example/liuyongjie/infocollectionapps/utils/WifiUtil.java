package com.example.liuyongjie.infocollectionapps.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.example.liuyongjie.infocollectionapps.entity.MyWifiInfo;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/23.
 */

public class WifiUtil {
    private static ILogger log = LoggerFactory.getLogger("WifiUtil");
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;

    // 构造器
    public WifiUtil(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    //获取当前手机wifi列表。6.0系统需要用户开启定位才能拿到wifi列表
    public List<MyWifiInfo> getNearbyWifiList() {
        List<MyWifiInfo> infos = null;
        try {
            List<ScanResult> results = mWifiManager.getScanResults();
            infos = new ArrayList<>();
            log.verbose(Author.liuyongjie, Business.dev_test, "扫描到的wifi列表集合长度{}", results.size());
            for (ScanResult result : results) {
                MyWifiInfo info = new MyWifiInfo(result.SSID, result.BSSID, WifiManager.calculateSignalLevel(result.level, 5));
                infos.add(info);
            }
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return infos;
    }

    //返回客户端的状态信息,返回值是一个枚举值,
    public SupplicantState getSupplicanState() {
        return mWifiInfo.getSupplicantState();
    }

    // 得到接入点的SSID,就是连接的wifi名
    public String getSSID() {
        String ssid = mWifiInfo.getSSID();
        ssid = ssid.substring(1, ssid.length() - 1);
        return ssid == null ? "" : ssid;
    }

    // 得到wifi的接入点地址(就是路由器的Mac地址)
    public String getBSSID() {
        return (mWifiInfo == null) ? "" : mWifiInfo.getBSSID();
    }

    // 得到当前wifi设备的MAC地址,通过执行本地命令获取
    public String getMacAddress() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("cat /sys/class/net/wlan0/address");
            InputStream inputStream = process.getInputStream();
            byte[] bytes = new byte[18];
            byte[] result = new byte[18];
            while (inputStream.read(bytes) != -1) {
                result = bytes;
            }
            String string = new String(result, "ascii").substring(0, bytes.length - 1);
            return string;
        } catch (IOException e) {
            log.error(Author.liuyongjie, e);
        }
        return "";
    }

    // 得到wifi的IP地址
    public String getIPAddress() {
        return TransformUtil.intToIp(mWifiInfo.getIpAddress());
    }

    //获取wifi的连接速度,单位是默认是Mbps
    public int getLinkSpeed() {
        return mWifiInfo.getLinkSpeed();
    }

    //获取wifi信号的强度
    public int getWifiStrength() {
        int rssi = mWifiInfo.getRssi();
        int strength = WifiManager.calculateSignalLevel(rssi, 5);
        return strength;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    //获取当前wifi的连接频率，默认单位是MHz
    public int getFrequency() {
        return mWifiInfo.getFrequency();
    }

}
