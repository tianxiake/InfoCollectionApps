package com.example.liuyongjie.infocollectionapps.entity;

/**
 * Created by liuyongjie on 2017/3/23.
 * Wifi信息的实体类
 */

public class MyWifiInfo {
    //wifi的名称
    private String SSID;
    //wifi的接入点地址(路由器的ip地址)
    private String BSSID;
    //wifi网络连接的优先数
    private int level;

    public MyWifiInfo(String SSID, String BSSID, int level) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.level = level;
    }

    public String getSSID() {
        return SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public int getLevel() {
        return level;
    }


    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "MyWifiInfo{" +
                "SSID='" + SSID + '\'' +
                ", BSSID='" + BSSID + '\'' +
                ", level=" + level +
                '}';
    }
}
