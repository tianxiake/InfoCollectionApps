package com.example.liuyongjie.infocollectionapps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.liuyongjie.infocollectionapps.utils.WifiUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                WifiUtil wifiUtil = new WifiUtil(this);
        Log.d("TAG", "SSID=" + wifiUtil.getSSID());
        Log.d("TAG", "BSSID=" + wifiUtil.getBSSID());
//        Log.d("TAG", "frequency=" + wifiUtil.getFrequency());
        Log.d("TAG", "Mac=" + wifiUtil.getMacAddress());
//        Log.d("TAG", "ip=" + wifiUtil.getIPAddress());
//        Log.d("TAG", "speed=" + wifiUtil.getLinkSpeed());
//        Log.d("TAG", "nearby=" + wifiUtil.getNearbyWifiList());
//        List<CustomWifiInfo> infos = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            CustomWifiInfo customWifiInfo = new CustomWifiInfo("ssid" + i, "bssid" + i, i);
//            infos.add(customWifiInfo);
//        }
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("ssid", "vlifeTop");
//            jsonObject.put("bssid", "bssid");
//            jsonObject.put("ip", "ip");
//            jsonObject.put("mac", "mac");
//            jsonObject.put("frequency", "frequency");
//            JsonArrayUtil<CustomWifiInfo> arrayUtil = new JsonArrayUtil<>();
//            JSONArray jsonArray = arrayUtil.creatJsonArray(infos);
//            jsonObject.put("nearByWifi", jsonArray);
//            String jsonStr = jsonObject.toString();
//            Log.d("json", jsonStr);
//        } catch (JSONException e) {
//            e.printStackTrace();
////        }

//        WifiUtil wifiUtil = new WifiUtil(this);
//        Log.d("TAG", wifiUtil.getwifiStrength() + "");
//        ReflactTest reflactTest = new ReflactTest();
//        Class clazz = reflactTest.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for(int i=0;i<fields.length;i++){
//            Log.d("TAG",fields[i].getName());
//        }

    }
}

