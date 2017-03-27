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
//        testAppList();
        testImage();
//        memoryTest();

    }

    //sdcard相册测试区
    public void testImage() {
//        File file = new File("/sdcard/DCIM");
//        File[] files = file.listFiles();
//        for (File filePath : files) {
//            Log.d("TAG", filePath.getAbsolutePath());
//        }
//        try {
//            ExifInterface exifInterface = new ExifInterface("/sdcard/DCIM/Camera/1482804163199.jpg");
//            String dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
//            Log.d("TAG", dateTime + "");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        SdcardUtil sdcardUtil = new SdcardUtil();
//        List<ImageInfo> imageInfos = sdcardUtil.getImageInfo();
//        Log.d("TAG", "length=" + imageInfos.size());
//        long  count  = imageInfos.toString().getBytes().length;
//        Log.d("TAG", imageInfos.toString());
//        Log.d("TAG","byte counte="+count);

        JsonObjectCenter center = new JsonObjectCenter();
        JSONObject imageJsonObject = center.getImageJsonObject();
        int byteCount = imageJsonObject.toString().getBytes().length;
        Log.d("TAG", "content=" + imageJsonObject.toString());
        Log.d("TAG", "byteCount=" + byteCount);
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

//    public void memoryTest() {
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            MyAppInfo myAppInfo = new MyAppInfo("packagename" + i);
//        }
//    }

}

