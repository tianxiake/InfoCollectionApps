package com.example.liuyongjie.infocollectionapps.center;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;

import com.example.liuyongjie.infocollectionapps.entity.ImageInfo;
import com.example.liuyongjie.infocollectionapps.entity.MyWifiInfo;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;
import com.example.liuyongjie.infocollectionapps.util.FileUtil;
import com.example.liuyongjie.infocollectionapps.util.JsonArrayUtil;
import com.example.liuyongjie.infocollectionapps.util.SdcardUtil;
import com.example.liuyongjie.infocollectionapps.util.WifiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/24.
 * 获取各种各样的数据，从这个类中获取
 */

public class DataCenter {

    private static final ILogger log = LoggerFactory.getLogger("DataCenter");

    /**
     * 获取一个完整的wifi信息的JsonObject
     *
     * @return 返回一个包含wifi信息的JSONObject对象
     */
    public JSONObject getWifiJsonObject(Context context) {
        JSONObject wifiJsonObject;
        WifiUtil wifiUtil = new WifiUtil(context);
        try {
            //最外层JsonObject对象
            wifiJsonObject = new JSONObject();

            //当前设备wifi的状态
            wifiJsonObject.put("wifiState", wifiUtil.getWifiState());

            //当前wifi的信息
            JSONObject connInfoJsonObject = getConnInfoJsonObject(context);

            //当前设备已经配置的信息
            JSONArray wifiConfigJsonArray = getWifiConfigurations(context);

            //当前连接wifi的DHCPinfo
            JSONObject wifiDHCPInfo = getDHCPInfo(context);

            //当前手机的wifi列表
            JSONArray wifiScanResults = getScanResults(context);

            wifiJsonObject.put("connectionInfo", connInfoJsonObject);
            wifiJsonObject.put("wifiConfigurations", wifiConfigJsonArray);
            wifiJsonObject.put("dhcpInfo", wifiDHCPInfo);
            wifiJsonObject.put("scanResults", wifiScanResults);

            return wifiJsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }


    /**
     * 获取手机相册图片信息对象
     *
     * @return 返回一个JsonObject对象
     */
    public JSONObject getImageJsonObject() {
        JSONObject imageJsonObject;
        try {
            imageJsonObject = new JSONObject();
            SdcardUtil sdcardUtil = new SdcardUtil();
            List<ImageInfo> listImageInfo = sdcardUtil.getImageInfo();
            JsonArrayUtil<ImageInfo> jsonArrayUtil = new JsonArrayUtil();
            JSONArray jsonArray = jsonArrayUtil.getJsonArray(listImageInfo);
            imageJsonObject.putOpt("image", jsonArray);
            return imageJsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获得安装应用的包名信息，包括安装和为安装的
     *
     * @param context
     * @return
     */
    public JSONArray getInstallPackgeInfosJsonArray(Context context) {
        try {
            List<PackageInfo> installedPackages = getInstalledPackages(context);
            JsonArrayUtil<PackageInfo> util = new JsonArrayUtil<>();
            JSONArray jsonArray = util.getJsonArray(installedPackages);
            return jsonArray;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获得已安装的应用
     *
     * @param context
     * @return
     */
    public JSONArray getApplicationInfosJsonArray(Context context) {
        try {
            List<ApplicationInfo> applicationInfos = getApplicationInfos(context);
            JsonArrayUtil<ApplicationInfo> util = new JsonArrayUtil<>();
            JSONArray jsonArray = util.getJsonArray(applicationInfos);
            return jsonArray;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }


    private File files = new File("/sdcard/DCIM");

    private FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathName) {
            if (pathName.equals(files)) {
                return false;
            }
            return true;
        }
    };

    /**
     * 返回一个sdcard JsonArray字符串
     *
     * @return
     */
    public JSONArray getSdcardJsonArray() {
        JSONArray jsonArray = null;
        try {
            SdcardUtil sdcardUtil = new SdcardUtil();
            jsonArray = new JSONArray();
            sdcardUtil.getAllFiles(jsonArray, new File("/sdcard"), fileFilter);
            FileUtil.writeFileFromString("/sdcard/Android/sdcard.txt", jsonArray.toString(), false);
            log.debug(Author.liuyongjie, Business.dev_test, "sdcard内容{}", jsonArray.toString());
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return jsonArray;
    }


    /**
     * 获取当前连接的Wifi的信息 ConnectionInfo
     *
     * @param context
     * @return
     */
    private JSONObject getConnInfoJsonObject(Context context) {
        try {
            WifiUtil wifiUtil = new WifiUtil(context);
            //ConnectionInfo部分
            JSONObject connInfoJsonObject = new JSONObject();
            connInfoJsonObject.put("wifiSsid", wifiUtil.getSSID());
            connInfoJsonObject.put("mBssid", wifiUtil.getBSSID());
            connInfoJsonObject.put("ip", wifiUtil.getIPAddress());
            connInfoJsonObject.put("mMacAddress", wifiUtil.getMacAddress());
            connInfoJsonObject.put("mFrequency", wifiUtil.getFrequency());
            connInfoJsonObject.put("mLinkSpeed", wifiUtil.getLinkSpeed());
            connInfoJsonObject.put("mRssi", wifiUtil.getWifiStrength());
            log.verbose(Author.liuyongjie, Business.dev_test, "connectionInfo={}", connInfoJsonObject.toString());
            return connInfoJsonObject;
        } catch (JSONException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取当前连接wifi的DHCPInfo
     *
     * @param context
     * @return
     */
    private JSONObject getDHCPInfo(Context context) {
        WifiUtil wifiUtil = new WifiUtil(context);
        try {
            DhcpInfo dhcpInfo = wifiUtil.getDHCPInfo();
            JSONObject dhcpJsonObject = new JSONObject();
            dhcpJsonObject.put("ipAddress", dhcpInfo.ipAddress);
            dhcpJsonObject.put("gateway", dhcpInfo.gateway);
            dhcpJsonObject.put("dns1", dhcpInfo.dns1);
            dhcpJsonObject.put("dns2", dhcpInfo.dns2);
            dhcpJsonObject.put("serverAddress", dhcpInfo.serverAddress);
            dhcpJsonObject.put("lease", dhcpInfo.leaseDuration);
            log.verbose(Author.liuyongjie, Business.dev_test, "dhcpInfo={}", dhcpInfo.toString());
            return dhcpJsonObject;
        } catch (JSONException e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取wifi列表 android6.0手机必须要开启定位才可以获取到
     *
     * @param context
     * @return
     */
    private JSONArray getScanResults(Context context) {
        try {
            WifiUtil wifiUtil = new WifiUtil(context);
            //wifi列表信息
            List<MyWifiInfo> listWifiInfo = wifiUtil.getScanResults();
            log.verbose(Author.liuyongjie, Business.dev_test, "listWifiInfo={}", listWifiInfo);
            if (listWifiInfo != null) {
                JsonArrayUtil jsonArrayUtil = new JsonArrayUtil();
                JSONArray wifiArray = jsonArrayUtil.getJsonArray(listWifiInfo);
                log.verbose(Author.liuyongjie, Business.dev_test, "wifisInfo={}", wifiArray.toString());
                return wifiArray;
            }
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获得当前手机已经保存的wifi的配置信息
     *
     * @param context
     * @return
     */
    private JSONArray getWifiConfigurations(Context context) {
        JSONArray jsonArray = null;
        try {
            WifiUtil wifiUtil = new WifiUtil(context);
            List<WifiConfiguration> configuredNetworks = wifiUtil.getConfiguredNetworks();
            JsonArrayUtil<WifiConfiguration> util = new JsonArrayUtil<>();
            jsonArray = util.getJsonArray(configuredNetworks);
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return jsonArray;
    }

    /**
     * 获取已安装的应用包列表
     *
     * @param context
     * @return
     */
    public List<PackageInfo> getInstalledPackages(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return packageInfos;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取已安装的应用列表
     *
     * @param context
     * @return
     */
    public List<ApplicationInfo> getApplicationInfos(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            List<ApplicationInfo> AppInfos = packageManager.getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return AppInfos;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

}
