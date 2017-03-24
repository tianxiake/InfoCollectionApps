package com.example.liuyongjie.infocollectionapps.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import java.util.Locale;


/**
 * Created by liuyongjie on 2016/11/22.
 */

public class AppUtil {

    private AppUtil() {
        throw new UnsupportedOperationException("这是一个App工具类，不容许被实例!");
    }

//    private static ILogger log = LoggerFactory.getLogger("AppUtil");
    private static ILogger log = LoggerFactory.getLogger("AppUtil");


    /**
     * 获取应用程序名称
     *
     * @param context 上下文对象
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context 上下文对象
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获得当前软件的安装路径 如：/data/data/com.test.vlife
     *
     * @param context 上下文对象
     */
    public static String getCurrentAppPath(Context context) {
        try {
            String path = context.getFilesDir().getAbsolutePath();
            int index = path.lastIndexOf("/");
            if (index == -1) {
                return path;
            } else {
                return path.substring(0, index);
            }
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }


    /**
     * 获得软件的hash值
     *
     * @param context 上下文对象
     * @return 获取不到返回-1
     */
    public static int getAppHashCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            int hashcode = sign.hashCode();
            return hashcode;
        } catch (PackageManager.NameNotFoundException e) {
            log.error(Author.liuyongjie, e);
        }
        return -1;
    }

    /**
     * 获取该程序的安装包路径
     *
     * @param context 上下文对象
     */
    public static String getPackageResourcePath(Context context) {
        try {
            return context.getApplicationContext().getPackageResourcePath();
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取该程序的包名
     *
     * @param context 上下文对象
     */
    public static String getPackageName(Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取程序包名", e);
        }
        return null;
    }

    /**
     * 获取该程序的软件版本号 返回的是 包名+版本名+版本号
     *
     * @param context 上下文对象
     */
    public static String getSoftVersion(Context context) {
        String versionName;
        try {
            String packageName = context.getPackageName();
            versionName = context.getPackageManager().getPackageInfo(
                    packageName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
            return packageName + "" + versionName + "" + versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取客户端语言
     */
    public static String getLanguage() {
        try {
            //Locale curLocale = context.getResources().getConfiguration().locale;
            Locale curLocale = Locale.getDefault();
            return curLocale.getLanguage();
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取客户端语言", e);
        }
        return null;
    }

    /**
     * 获取软件进程
     *
     * @param context 上下文对象
     */
    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取软件进程", e);
        }
        return null;
    }

    /**
     * 渠道号
     *
     * @param context 上下文对象
     */
    public static String getChannelCode(Context context) {
        try {
            String code = getMetaData(context, "channel");
            if (code != null) {
                return code;
            }
            return "C_000";
        } catch (Exception e) {
            log.warn(Author.liuyongjie, "无法获取渠道号", e);
        }
        return null;
    }

    /**
     * 获得清单配置文件中MetaData的字符串
     *
     * @param context 上下文对象
     * @param key     key
     * @return 返回一个字符串
     */
    private static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

}
