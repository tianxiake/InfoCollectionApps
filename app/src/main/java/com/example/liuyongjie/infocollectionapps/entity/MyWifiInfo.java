package com.example.liuyongjie.infocollectionapps.entity;

/**
 * Created by liuyongjie on 2017/3/23.
 * Wifi信息的实体类
 */

public class MyWifiInfo {
    //wifi的名称
    private String ssid;
    //wifi的接入点地址(路由器的ip地址)
    private String bssid;
    //wifi网络连接的优先数
    private int level;

    public MyWifiInfo(String ssid, String bssid, int level) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.level = level;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public int getLevel() {
        return level;
    }


    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "MyWifiInfo{" +
                "ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                ", level=" + level +
                '}';
    }
}
