package com.example.liuyongjie.infocollectionapps.entity;

/**
 * Created by liuyongjie on 2017/4/10.
 */

public class MyWifiConfiguration {
    //网络分配的id
    private int ID;
    //网络的名字
    private String ssid;
    //网络的优先级
    private int prio;

    private int numAssociation;

    private String keyMgmt;

    private String protocol;

    private String pairwiseCiphers;

    private String groupCiphers;

    private String ipAssignment;

    private String proxySetting;

    private String httpProxy;

    private String script;

    private int cuid;

    private String cname;

    private String userApproved;

    private boolean noInternetAccessExpected;

    private long roamingFailureBlackListTimeMilli;

    public MyWifiConfiguration() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public void setNumAssociation(int numAssociation) {
        this.numAssociation = numAssociation;
    }

    public void setKeyMgmt(String keyMgmt) {
        this.keyMgmt = keyMgmt;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setPairwiseCiphers(String pairwiseCiphers) {
        this.pairwiseCiphers = pairwiseCiphers;
    }

    public void setGroupCiphers(String groupCiphers) {
        this.groupCiphers = groupCiphers;
    }

    public void setIpAssignment(String ipAssignment) {
        this.ipAssignment = ipAssignment;
    }

    public void setProxySetting(String proxySetting) {
        this.proxySetting = proxySetting;
    }

    public void setHttpProxy(String httpProxy) {
        this.httpProxy = httpProxy;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setCuid(int cuid) {
        this.cuid = cuid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setUserApproved(String userApproved) {
        this.userApproved = userApproved;
    }

    public void setNoInternetAccessExpected(boolean noInternetAccessExpected) {
        this.noInternetAccessExpected = noInternetAccessExpected;
    }

    public void setRoamingFailureBlackListTimeMilli(long roamingFailureBlackListTimeMilli) {
        this.roamingFailureBlackListTimeMilli = roamingFailureBlackListTimeMilli;
    }

    public int getID() {
        return ID;
    }

    public String getSsid() {
        return ssid;
    }

    public int getPrio() {
        return prio;
    }

    public int getNumAssociation() {
        return numAssociation;
    }

    public String getKeyMgmt() {
        return keyMgmt;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPairwiseCiphers() {
        return pairwiseCiphers;
    }

    public String getGroupCiphers() {
        return groupCiphers;
    }

    public String getIpAssignment() {
        return ipAssignment;
    }

    public String getProxySetting() {
        return proxySetting;
    }

    public String getHttpProxy() {
        return httpProxy;
    }

    public String getScript() {
        return script;
    }

    public int getCuid() {
        return cuid;
    }

    public String getCname() {
        return cname;
    }

    public String getUserApproved() {
        return userApproved;
    }

    public boolean isNoInternetAccessExpected() {
        return noInternetAccessExpected;
    }

    public long getRoamingFailureBlackListTimeMilli() {
        return roamingFailureBlackListTimeMilli;
    }

    @Override
    public String toString() {
        return "MyWifiConfiguration{" +
                "ID=" + ID +
                ", ssid='" + ssid + '\'' +
                ", prio=" + prio +
                ", numAssociation=" + numAssociation +
                ", keyMgmt='" + keyMgmt + '\'' +
                ", protocol='" + protocol + '\'' +
                ", pairwiseCiphers='" + pairwiseCiphers + '\'' +
                ", groupCiphers='" + groupCiphers + '\'' +
                ", ipAssignment='" + ipAssignment + '\'' +
                ", proxySetting='" + proxySetting + '\'' +
                ", httpProxy='" + httpProxy + '\'' +
                ", script='" + script + '\'' +
                ", cuid=" + cuid +
                ", cname='" + cname + '\'' +
                ", userApproved='" + userApproved + '\'' +
                ", noInternetAccessExpected=" + noInternetAccessExpected +
                ", roamingFailureBlackListTimeMilli=" + roamingFailureBlackListTimeMilli +
                '}';
    }
}
