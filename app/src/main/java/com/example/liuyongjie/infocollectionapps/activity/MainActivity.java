package com.example.liuyongjie.infocollectionapps.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liuyongjie.infocollectionapps.R;
import com.example.liuyongjie.infocollectionapps.center.DataCenter;
import com.example.liuyongjie.infocollectionapps.utils.FileUtil;
import com.example.liuyongjie.infocollectionapps.utils.SdcardUtil;
import com.example.liuyongjie.infocollectionapps.utils.ToastUtil;
import com.example.liuyongjie.infocollectionapps.utils.ZipUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button sensorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

//        InnerBroadCastReceiver broadCastReceiver = new InnerBroadCastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        registerReceiver(broadCastReceiver,filter);
//        testAppList();
//        testImage();
//        memoryTest();
//        testFile();
//        startThread();
//        fileFilterTest();
//        zipTest();
//        sensorsTest();

    }

    private void initView() {
        sensorButton = (Button) findViewById(R.id.sensor_button);
    }

    private void setListener() {
        sensorButton.setOnClickListener(this);
    }

//
//    public void sensorsTest() {
//        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        List<Sensor> listSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor sensor : listSensors) {
//            Log.d("TAG", sensor.getName() + "," + sensor.getPower());
//        }
//
//    }

    //往sdcard中写入文件
    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                otherTest();
            }
        }.start();
    }

    //sdcard相册测试区
    public void testImage() {
        DataCenter center = new DataCenter();
        JSONObject imageJsonObject = center.getImageJsonObject();
        int byteCount = imageJsonObject.toString().getBytes().length;
        Log.d("TAG", "content=" + imageJsonObject.toString());
        Log.d("TAG", "byteCount=" + byteCount);
    }

    //wifi 信息测试区
    public void testWifiInfo() {
        DataCenter jsonObjectUtil = new DataCenter();
        JSONObject jsonObject = jsonObjectUtil.getWifiJsonObject(this);
        Log.d("TAG", jsonObject.toString());
    }

    //应用列表测试区
    public void testAppList() {
        DataCenter center = new DataCenter();
        JSONObject appListJsonObject = center.getAppListJsonObject(this);
        Log.d("TAG", appListJsonObject.toString());
        FileUtil.writeFileFromString(FILE_PATH + "b.dat", appListJsonObject.toString(), false);
    }

    //    public void memoryTest() {
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            MyAppInfo myAppInfo = new MyAppInfo("packagename" + i);
//        }
//    }
    private static String FILE_PATH = "/sdcard/Android/";

    //文件写入测试区
    public void testFile() {
        DataCenter center = new DataCenter();
        JSONObject jsonObject = center.getImageJsonObject();
        if (jsonObject != null) {
            String jsonStr = jsonObject.toString();
            Log.d("TAG", "jsonStr=" + jsonStr);
            FileUtil.writeFileFromString(FILE_PATH + "C.dat", jsonStr, false);
        }

        try {
            String jsonStr = FileUtil.readFile2String(new File(FILE_PATH + "C.dat"), "utf-8");
            JSONObject json = new JSONObject(jsonStr);
            Log.d("TAG", "json" + json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private File files = new File("/sdcard/DCIM");

    private FileFilter fileFilter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            if (pathname.equals(files)) {
                return false;
            }
            return true;
        }
    };

    public void otherTest() {
        try {
            SdcardUtil sdcardUtil = new SdcardUtil();
//            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            sdcardUtil.getAllFiles(jsonArray, new File("/sdcard"), fileFilter);
//            jsonObject.put("sdcard", jsonArray);
//            Log.d("TAG", jsonObject.toString());
            FileUtil.writeFileFromString("/sdcard/Android/sdcard.txt", jsonArray.toString(), false);
            Log.d("TAG", jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //文件过滤测试区
    public void fileFilterTest() {
        try {
            File file = new File("/sdcard");
            File[] listFile = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.equals(files)) {
                        return false;
                    }
                    return true;
                }
            });
            for (File file1 : listFile) {
                Log.d("TAG", file1.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //文件压缩测试区
    public void zipTest() {
        try {
            Boolean success = ZipUtil.zipFile(new File("/sdcard/Android/sdcard.txt"), new File("/sdcard/Android/sdcard.zip"), null);
            if (success == true) {
                ToastUtil.show(this, "压缩成功", Toast.LENGTH_LONG);
            } else {
                ToastUtil.show(this, "压缩失败", Toast.LENGTH_LONG);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SensorActivity.class);
        startActivity(intent);
    }

    //动态广播
    public class InnerBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", intent.getAction());
        }
    }
}

