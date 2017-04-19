package com.example.liuyongjie.infocollectionapps.center;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.DhcpInfo;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.ProxyInfo;
import android.net.wifi.WifiConfiguration;

import com.example.liuyongjie.infocollectionapps.entity.ImageInfo;
import com.example.liuyongjie.infocollectionapps.entity.MyWifiInfo;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;
import com.example.liuyongjie.infocollectionapps.util.ConnectivityData;
import com.example.liuyongjie.infocollectionapps.util.FileUtil;
import com.example.liuyongjie.infocollectionapps.util.JSONUtil;
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
            JSONUtil<ImageInfo> JSONUtil = new JSONUtil();
            JSONArray jsonArray = JSONUtil.createJSONArray(listImageInfo);
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
            JSONUtil<PackageInfo> util = new JSONUtil<>();
            JSONArray jsonArray = util.createJSONArray(installedPackages);
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
            JSONUtil<ApplicationInfo> util = new JSONUtil<>();
            JSONArray jsonArray = util.createJSONArray(applicationInfos);
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
     * 获取ConnectivityManager的JSONObject对象
     */
    public JSONObject getConnectivityManagerJsonObject(Context context) {
        try {
            JSONObject connectivityManagerJsonObject = new JSONObject();

            ConnectivityData data = new ConnectivityData(context);
            int status = data.getRestrictBackgroundStatus();
            JSONObject activeNetworkInfoJsonObject = getActiveNetworkInfoJsonObject(context);
            JSONObject defaultProxyJsonObject = getDefaultProxyJsonObject(context);
            JSONArray allNetworkCapabilitiesJsonArray = getAllNetworkCapabilitiesJsonArray(context);
            JSONArray allNetworkInfoJsonArray = getAllNetworkInfoJsonArray(context);
            JSONArray allNetworkLinkPropertiesJsonArray = getAllNetworkLinkPropertiesJsonArray(context);
            JSONObject boundNetworkForProcessJsonObject = getBoundNetworkForProcessJsonObject(context);


            connectivityManagerJsonObject.put("restrictBackgroundStatus", status);
            connectivityManagerJsonObject.put("activeNetworkInfo", activeNetworkInfoJsonObject);
            connectivityManagerJsonObject.put("defaultProxy", defaultProxyJsonObject);
            connectivityManagerJsonObject.put("allNetworkCapabilities", allNetworkCapabilitiesJsonArray);
            connectivityManagerJsonObject.put("allNetworkInfo", allNetworkInfoJsonArray);
            connectivityManagerJsonObject.put("allNetworkLinkProperties", allNetworkLinkPropertiesJsonArray);
            connectivityManagerJsonObject.put("boundNetworkForProcess", boundNetworkForProcessJsonObject);

            return connectivityManagerJsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成ConnectivityManagerJsonObject出异常了---", e);
        }
        return null;
    }

    /**
     * 获取AllNetworkInfoJsonArray Android5.0以上API有效
     *
     * @param context
     * @return
     */
    private JSONArray getAllNetworkInfoJsonArray(Context context) {
        JSONArray jsonArray = null;
        try {
            ConnectivityData data = new ConnectivityData(context);
            List<NetworkInfo> allNetWorkInfo = data.getAllNetWorkInfo();
            JSONUtil util = new JSONUtil();
            jsonArray = util.createJSONArray(allNetWorkInfo);
            return jsonArray;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成AllNetworkInfoJsonArray出异常了---", e);
        }
        return jsonArray;
    }

    /**
     * 获取AllNetworkLinkPropertiesJsonArray Android5.0以上API有效
     *
     * @return
     */
    private JSONArray getAllNetworkLinkPropertiesJsonArray(Context context) {
        JSONArray jsonArray = null;
        try {
            ConnectivityData data = new ConnectivityData(context);
            List<LinkProperties> allNetWorkLinkProperties = data.getAllNetWorkLinkProperties();
            JSONUtil util = new JSONUtil();
            jsonArray = util.createJSONArray(allNetWorkLinkProperties);
            return jsonArray;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成AllNetworkLinkPropertiesJsonArray出异常了---", e);
        }
        return jsonArray;
    }

    /**
     * 获取AllNetworkLinkPropertiesJsonArray Android5.0以上API有效
     *
     * @return
     */
    private JSONArray getAllNetworkCapabilitiesJsonArray(Context context) {
        JSONArray jsonArray = null;
        try {
            ConnectivityData data = new ConnectivityData(context);
            List<NetworkCapabilities> allNetworkCapabilities = data.getAllNetworkCapabilities();
            JSONUtil util = new JSONUtil();
            jsonArray = util.createJSONArray(allNetworkCapabilities);
            return jsonArray;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成AllNetworkCapabilitiesJsonArray出异常了---", e);
        }
        return jsonArray;
    }

    /**
     * 获取默认代理的JSONObject对象,Android6.0以上API有效
     */
    private JSONObject getDefaultProxyJsonObject(Context context) {
        JSONObject jsonObject = null;
        try {
            ConnectivityData data = new ConnectivityData(context);
            ProxyInfo defaultProxy = data.getDefaultProxy();
            JSONUtil util = new JSONUtil();
            jsonObject = util.createJSONObject(defaultProxy);
            return jsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成DefaultProxyJsonObject出异常了---", e);
        }
        return null;
    }

    /**
     * 获取当前可用网络信息的JSONObject对象
     */
    private JSONObject getActiveNetworkInfoJsonObject(Context context) {
        JSONObject jsonObject = null;
        try {
            ConnectivityData data = new ConnectivityData(context);
            NetworkInfo activeNetworkInfo = data.getActiveNetworkInfo();
            JSONUtil util = new JSONUtil();
            jsonObject = util.createJSONObject(activeNetworkInfo);
            return jsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成ActiveNetworkInfoJsonObject出异常了---", e);
        }
        return null;
    }

    /**
     * 获取BoundNetworkForProcessJsonObject Android6.0可用
     */
    private JSONObject getBoundNetworkForProcessJsonObject(Context context) {
        JSONObject jsonObject = null;
        try {
            ConnectivityData data = new ConnectivityData(context);
            Network network = data.getBoundNetworkForProcess();
            NetworkInfo networkInfo = data.getNetworkInfo(network);
            JSONUtil util = new JSONUtil();
            jsonObject = util.createJSONObject(networkInfo);
            return jsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, "---生成BoundNetworkForProcessJsonObject出异常了---", e);
        }
        return null;
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
            dhcpJsonObject.put("leaseDuration", dhcpInfo.leaseDuration);
            dhcpJsonObject.put("netmask", dhcpInfo.netmask);
            log.verbose(Author.liuyongjie, Business.dev_test, "dhcpInfo={}", dhcpJsonObject.toString());
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
            if (listWifiInfo != null) {
                JSONUtil JSONUtil = new JSONUtil();
                JSONArray wifiArray = JSONUtil.createJSONArray(listWifiInfo);
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
            JSONUtil<WifiConfiguration> util = new JSONUtil<>();
            jsonArray = util.createJSONArray(configuredNetworks);
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
    private List<PackageInfo> getInstalledPackages(Context context) {
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
    private List<ApplicationInfo> getApplicationInfos(Context context) {
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
