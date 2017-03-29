package com.example.liuyongjie.infocollectionapps.center;

import android.content.Context;

import com.example.liuyongjie.infocollectionapps.entity.MyWifiInfo;
import com.example.liuyongjie.infocollectionapps.entity.ImageInfo;
import com.example.liuyongjie.infocollectionapps.entity.MyAppInfo;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;
import com.example.liuyongjie.infocollectionapps.utils.AppUtil;
import com.example.liuyongjie.infocollectionapps.utils.FileUtil;
import com.example.liuyongjie.infocollectionapps.utils.JsonArrayUtil;
import com.example.liuyongjie.infocollectionapps.utils.SdcardUtil;
import com.example.liuyongjie.infocollectionapps.utils.WifiUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/24.
 * 获取各种各样的数据，从这个类中获取
 */

public class DataCenter {

    private ILogger log = LoggerFactory.getLogger("DataCenter");

    /**
     * 获取一个完整的wifi信息的JsonObject
     *
     * @return 返回一个包含wifi信息的JSONObject对象
     */
    public JSONObject getWifiJsonObject(Context context) {
        JSONObject wifiJsonObject;
        WifiUtil wifiUtil = new WifiUtil(context);
        try {
            wifiJsonObject = new JSONObject();
            wifiJsonObject.put("ssid", wifiUtil.getSSID());
            wifiJsonObject.put("bssid", wifiUtil.getBSSID());
            wifiJsonObject.put("ip", wifiUtil.getIPAddress());
            wifiJsonObject.put("macAddress", wifiUtil.getMacAddress());
            wifiJsonObject.put("frequency", wifiUtil.getFrequency());
            wifiJsonObject.put("linkSpeed", wifiUtil.getLinkSpeed());
            wifiJsonObject.put("level", wifiUtil.getWifiStrength());
            //jsonArray部分
            List<MyWifiInfo> listWifiInfo = wifiUtil.getNearbyWifiList();
            JsonArrayUtil jsonArrayUtil = new JsonArrayUtil();
            JSONArray wifiArray = jsonArrayUtil.getJsonArray(listWifiInfo);
            //jsonArray部分
            wifiJsonObject.put("nearbyWifi", wifiArray);
            return wifiJsonObject;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * 获取一个包含已安装应用列表信息的JSONObject
     *
     * @param context
     * @return
     */
    public JSONObject getAppListJsonObject(Context context) {
        JSONObject appListJsonObject = null;
        try {
            appListJsonObject = new JSONObject();
            List<MyAppInfo> appInfos = AppUtil.getInstallAppList(context);
            JsonArrayUtil jsonArrayUtil = new JsonArrayUtil();
            JSONArray jsonArray = jsonArrayUtil.getJsonArray(appInfos);
            appListJsonObject.put("applist", jsonArray);
            return appListJsonObject;
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
        JSONObject imageJsonObject = null;
        try {
            imageJsonObject = new JSONObject();
            SdcardUtil sdcardUtil = new SdcardUtil();
            List<ImageInfo> imageInfos = sdcardUtil.getImageInfo();
            JsonArrayUtil jsonArrayUtil = new JsonArrayUtil();
            JSONArray jsonArray = jsonArrayUtil.getJsonArray(imageInfos);
            imageJsonObject.putOpt("image", jsonArray);
            return imageJsonObject;
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


}
