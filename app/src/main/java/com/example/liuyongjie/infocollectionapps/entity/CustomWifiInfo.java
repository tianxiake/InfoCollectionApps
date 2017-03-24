package com.example.liuyongjie.infocollectionapps.entity;

/**
 * Created by liuyongjie on 2017/3/23.
 */

public class CustomWifiInfo {
    //wifi的名称
    private String ssid;
    //wifi的接入点地址
    private String bssid;
    //wifi网络连接的优先数
    private int level;
//    //wifi的mac地址
//    private String macAddress;
//    //wifi的ip地址
//    private String ipAddress;

    public CustomWifiInfo(String ssid, String bssid, int level) {
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

//    public String getMacAddress() {
//        return macAddress;
//    }
//
//    public String getIpAddress() {
//        return ipAddress;
//    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public void setLevel(int level) {
        this.level = level;
    }

//    public void setMacAddress(String macAddress) {
//        this.macAddress = macAddress;
//    }
//
//    public void setIpAddress(String ipAddress) {
//        this.ipAddress = ipAddress;
//    }


    @Override
    public String toString() {
        return "CustomWifiInfo{" +
                "ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                ", level=" + level +
                '}';
    }
}
