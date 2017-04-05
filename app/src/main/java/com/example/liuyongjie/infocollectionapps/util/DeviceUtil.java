package com.example.liuyongjie.infocollectionapps.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.util.TimeZone;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * Created by liuyongjie on 2016/11/23.
 */

public class DeviceUtil {
    private static ILogger log = LoggerFactory.getLogger("DeviceUtil");

    /**
     * 获取手机型号
     */
    public static String getModel() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取手机型号", e);
        }
        return null;
    }

    /**
     * 获取android_id
     *
     * @param context 上下文对象
     */
    public static String getAndroidId(Context context) {
        try {
            String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            return androidId;
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取手机android_id", e);
        }
        return null;
    }

    /**
     * 获取手机设备IMEI android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE
     *
     * @param context 上下文对象
     */
    public static String getDeviceIMEI(Context context) {
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            String deviceIMEI = TelephonyMgr.getDeviceId();
            return deviceIMEI;
        } catch (Exception e) {
            log.warn(Author.liuyongjie, Business.protocol, "无法获取手机设备IMEI");
        }
        return null;
    }

    /**
     * 获取时区
     */
    public static String getTimeZone() {
        try {
            TimeZone tz = TimeZone.getDefault();
            return tz.getDisplayName(false, TimeZone.SHORT);
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取手机的时区", e);
        }
        return null;
    }

    //获取SIM卡的序列号 android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE
    public static String getSimSerialNumber(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSimSerialNumber();
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }
//    //获取Line1number {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}  {@link android.Manifest.permission#READ_SMS}
//    public static String getLine1Number(Context context) {
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            String number = telephonyManager.getLine1Number();
//            return number;
//        } catch (Exception e) {
//            log.error(Author.liuyongjie, e);
//        }
//        return null;
//    }

//    /**
//     * 获取SIM卡的序列号
//     *
//     * @param context 上下文对象
//     * @return 返回序列号或者是“”
//     */
//    public  String getSimSerialNumber(Context context) {
//        String serialNumber = "";
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            serialNumber = telephonyManager.getSimSerialNumber();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return (serialNumber == null) ? "" : serialNumber;
//
//    }

    /**
     * 获取手机号码
     *
     * @param context 上下文对象
     * @return 返回手机号码或者是“”
     */
    public String getLine1Number(Context context) {
        String line1Number = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            line1Number = telephonyManager.getLine1Number();
            return line1Number;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line1Number;
    }
}
