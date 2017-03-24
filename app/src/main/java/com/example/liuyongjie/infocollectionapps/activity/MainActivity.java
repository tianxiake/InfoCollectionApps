package com.example.liuyongjie.infocollectionapps.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.liuyongjie.infocollectionapps.R;
import com.example.liuyongjie.infocollectionapps.center.JsonObjectCenter;

import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testAppList();


    }

    //wifi 信息测试区
    public void testWifiInfo() {
        JsonObjectCenter jsonObjectUtil = new JsonObjectCenter();
        JSONObject jsonObject = jsonObjectUtil.getWifiJsonObject(this);
        Log.d("TAG", jsonObject.toString());
    }

    //应用列表测试区
    public void testAppList() {
        JsonObjectCenter center = new JsonObjectCenter();
        JSONObject appListJsonObject = center.getAppListJsonObject(this);
        Log.d("TAG", appListJsonObject.toString());
    }

}

