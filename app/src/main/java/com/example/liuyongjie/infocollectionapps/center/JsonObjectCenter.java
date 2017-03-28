package com.example.liuyongjie.infocollectionapps.center;

import android.content.Context;

import com.example.liuyongjie.infocollectionapps.entity.CustomWifiInfo;
import com.example.liuyongjie.infocollectionapps.entity.ImageInfo;
import com.example.liuyongjie.infocollectionapps.entity.MyAppInfo;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.utils.AppUtil;
import com.example.liuyongjie.infocollectionapps.utils.JsonArrayUtil;
import com.example.liuyongjie.infocollectionapps.utils.SdcardUtil;
import com.example.liuyongjie.infocollectionapps.utils.WifiUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by liuyongjie on 2017/3/24.
 * 获取各种各样的数据，从这个类中获取
 */

public class JsonObjectCenter {

    private ILogger log = LoggerFactory.getLogger("JsonObjectCenter");

    /**
     * 获取一个完整的wifi信息的JsonObject
     *
     * @return
     */
    public JSONObject getWifiJsonObject(Context context) {
        JSONObject wifiJsonObject = null;
        WifiUtil wifiUtil = new WifiUtil(context);
        try {
            wifiJsonObject = new JSONObject();
            wifiJsonObject.put("ssid", wifiUtil.getSSID());
            wifiJsonObject.put("bssid", wifiUtil.getBSSID());
            wifiJsonObject.put("ip", wifiUtil.getIPAddress());
            wifiJsonObject.put("macAddress", wifiUtil.getMacAddress());
            wifiJsonObject.put("frequency", wifiUtil.getFrequency());
            wifiJsonObject.put("linkSpeed", wifiUtil.getLinkSpeed());
            wifiJsonObject.put("level", wifiUtil.getwifiStrength());
            //jsonArray部分
            List<CustomWifiInfo> customWifiInfos = wifiUtil.getNearbyWifiList();
            JsonArrayUtil jsonArrayUtil = new JsonArrayUtil();
            JSONArray wifiArray = jsonArrayUtil.getJsonArray(customWifiInfos);
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

//    public  JSONObject getSdcardJsonObject(){
//        JSONObject sdcardJsonObject = null;
//        try{
//            sdcardJsonObject = new JSONObject();
//            SdcardUtil sdcardUtil = new SdcardUtil();
//            sdcardUtil.getAllFiles();
//
//        }catch (Exception e)
//    }

}
