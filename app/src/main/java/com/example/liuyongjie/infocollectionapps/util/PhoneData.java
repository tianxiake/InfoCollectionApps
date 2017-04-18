package com.example.liuyongjie.infocollectionapps.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by liuyongjie on 2017/4/14.
 * 这个类的数据都来自于TelephonyManager类
 */

public class PhoneData {
    private TelephonyManager telephonyManager;

    public PhoneData(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    //获取手机IMEI号
    public String getDeviceId() {
        return telephonyManager.getDeviceId();
    }

    //获取所有的CellInfo信息
    public List<CellInfo> getAllCellInfo() {
        return telephonyManager.getAllCellInfo();
    }

    //获取SIM序列号，不可用在返回null
    public String getSimSerialNumber() {
        return telephonyManager.getSimSerialNumber();
    }

    /**
     * 获得手机电话的类型
     * <p>
     * PHONE_TYPE_NONE
     * PHONE_TYPE_GSM
     * PHONE_TYPE_CDMA
     * PHONE_TYPE_SIP
     * </p>
     */
    public int getPhoneType() {
        return telephonyManager.getPhoneType();
    }

    //获取sim所在国家的ISO code
    public String getSimCountryIso() {
        return telephonyManager.getSimCountryIso();
    }

    public String getDeviceSoftwareVersion() {
        return telephonyManager.getDeviceSoftwareVersion();
    }

    //CDMA网络不可靠
    public String getNetworkCountryIso() {
        return telephonyManager.getNetworkCountryIso();
    }

    /**
     * <p>
     * 0 ：声音,sms,data全部可用
     * 1 ：表示手机是单卡
     * 2 ：表示手机是双卡
     * </p>
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public int getPhoneCount() {
        return telephonyManager.getPhoneCount();
    }

    /**
     * 获得MMS(多媒体短信业务)的代理商
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getMmsUserAgent() {
        return telephonyManager.getMmsUserAgent();
    }

    /**
     * 获得MMS(多媒体短信业务)的代理商代理文件的URL
     *
     * @return 取不到
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getMmsUAProfUrl() {
        return telephonyManager.getMmsUAProfUrl();
    }


    /**
     * 返回数据网络类型
     * <p>
     * NETWORK_TYPE_UNKNOWN
     * NETWORK_TYPE_GPRS
     * NETWORK_TYPE_EDGE
     * NETWORK_TYPE_UMTS
     * NETWORK_TYPE_HSDPA
     * NETWORK_TYPE_HSUPA
     * NETWORK_TYPE_HSPA
     * NETWORK_TYPE_CDMA
     * NETWORK_TYPE_EVDO_0
     * NETWORK_TYPE_EVDO_A
     * NETWORK_TYPE_EVDO_B
     * NETWORK_TYPE_1xRTT
     * NETWORK_TYPE_IDEN
     * NETWORK_TYPE_LTE
     * NETWORK_TYPE_EHRPD
     * NETWORK_TYPE_HSPAP
     * </p>
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N)
    public int getDataNetworkType() {
        return telephonyManager.getDataNetworkType();
    }

    /**
     * Returns the numeric name (MCC+MNC) of current registered operator
     * 对CDMA不可靠
     *
     * @return 取不到返回 ""
     */
    public String getNetworkOperator() {
        return telephonyManager.getNetworkOperator();
    }

    /**
     * Returns the alphabetic name of current registered operator
     * 对CMDA不可靠
     *
     * @return 取不到返回 ""
     */
    public String getNetworkOperatorName() {
        return telephonyManager.getNetworkOperatorName();
    }


}
