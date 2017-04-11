package com.example.liuyongjie.infocollectionapps.notforwork;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/24.
 */

public class MyWifiUtil {
    private static ILogger log = LoggerFactory.getLogger("WifiUtil");
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    // 扫描出的网络连接列表
    private List mWifiList;
    // 网络连接列表
    private List mWifiConfiguration;
    // 定义一个WifiLock
    WifiManager.WifiLock mWifiLock;


    // 构造器
    public MyWifiUtil(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    //返回客户端的状态信息,返回值是一个枚举值,
    public SupplicantState getSupplicanState() {
        return mWifiInfo.getSupplicantState();
    }

    // 得到接入点的SSID,就是连接的wifi名
    public String getSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }

    // 得到wifi的接入点地址(就是路由器的Mac地址)
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
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
            String string = new String(result, "ascii");
            return string;
        } catch (IOException e) {
            log.error(Author.liuyongjie, e);
        }
        return "";
    }

    // 得到wifi的IP地址
    public int getIPAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    //获取wifi的连接速度,单位是默认是Mbps
    public int getLinkSpeed() {
        return mWifiInfo.getLinkSpeed();
    }

    //获取wifi信号的强度
    public int getwifiStrength() {
        int rssi = mWifiInfo.getRssi();
        int strength = WifiManager.calculateSignalLevel(rssi, 5);
        return strength;
    }

    ////////////////////////////////////////////////////////////////////
    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    // 得到配置好的网络
    public List getConfiguration() {
        return mWifiConfiguration;
    }

    public void startScan() {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
    }


    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder
                    .append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }


    // 得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    // 得到WifiInfo的所有信息包
    public String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    //获取当前wifi的连接频率，默认单位是MHz
    public int getFrequency() {
        return mWifiInfo.getFrequency();
    }

    // 添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean b = mWifiManager.enableNetwork(wcgID, true);
        System.out.println("a--" + wcgID);
        System.out.println("b--" + b);
    }

    // 断开指定ID的网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }
//
//    public String getMacAddr() {
//        try {
//            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface nif : all) {
//                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
//
//                byte[] macBytes = nif.getHardwareAddress();
//                if (macBytes == null) {
//                    return "";
//                }
//
//                StringBuilder res1 = new StringBuilder();
//                for (byte b : macBytes) {
//                    res1.append(String.format("%02X:", b));
//                }
//
//                if (res1.length() > 0) {
//                    res1.deleteCharAt(res1.length() - 1);
//                }
//                return res1.toString();
//            }
//        } catch (Exception e) {
//            Log.e("TAG", "获取mac地址失败", e);
//        }
//        return "02:00:00:00:00:00";
//    }
}
