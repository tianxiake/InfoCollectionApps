package com.example.liuyongjie.infocollectionapps.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuyongjie on 2017/3/24.
 */

public class JsonUtil {

    public JSONObject createJsonObject() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ssid", "vlifeTop");
            jsonObject.put("bssid", "bssid");
            jsonObject.put("ip", "ip");
            jsonObject.put("macAddress", "mac");
            jsonObject.put("Frequency", "frequency");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


}
