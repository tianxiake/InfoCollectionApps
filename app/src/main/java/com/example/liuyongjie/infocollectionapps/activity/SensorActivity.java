package com.example.liuyongjie.infocollectionapps.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.liuyongjie.infocollectionapps.R;
import com.example.liuyongjie.infocollectionapps.util.FileUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends Activity implements SensorEventListener {
    private TextView messageTextView;
    private SensorManager sensorManager;
    //传感器数据采集的时间,采样时间单位是微妙值,采样时间最大是1s,超过1s默认就是1s
    private int time = 2000 * 1000;
    //控制传感器数据收集的时间,单位是毫秒值
    private int timeControl = 60 * 1000;
    //控制的数据的采集与否
    private boolean openDoor = false;
    //    private int count = 0;
    //记录数据开始收集时的开始时间
    private long doorOpenTime = 0;

    private List<JSONArray> listBuilder = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //每隔一定时间，这个方法被执行一次
            //读取传感器数据,追加到sdcard文件中
            Log.v("TAG", "writeTime start=" + System.currentTimeMillis());
            writeSensorData(listBuilder);
            if (listBuilder != null) {
                //清除容器中的所有数据
                listBuilder.clear();
            }
            Log.v("TAG", "writeTime end=" + System.currentTimeMillis());
            handler.postDelayed(this, timeControl);
            openDoor = true;
            doorOpenTime = System.currentTimeMillis();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sersor);
        initView();
        initValue();
        setListener();
    }

    private void initValue() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //启动定时器
        handler.postDelayed(runnable, timeControl);
        //刚开始就收集数据
        openDoor = true;
        doorOpenTime = System.currentTimeMillis();
    }

    private void setListener() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), time);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), time);
    }

    private void initView() {
        messageTextView = (TextView) findViewById(R.id.messageTextView);
    }


    //传感器数值变化时回调
    @Override
    public void onSensorChanged(SensorEvent event) {
        //到了10秒去采集一次数据,openDoor为真就采集数据
        if (openDoor) {
            long start = System.currentTimeMillis();
//            count++;
//            //采集了三次数据
//            if (count > 3) {
//                openDoor = false;
//                count = 0;
//                return;
//            }
            //每隔一分钟采集一次，每次采集时间为2秒钟
            if (start > (doorOpenTime + 2000)) {
                //记录开门的时间
                openDoor = false;
                doorOpenTime = 0;
            } else {
                int type = event.sensor.getType();
                float[] values = event.values;
                JSONArray jsonArray = new JSONArray();
                try {
                    jsonArray.put(type);
                    jsonArray.put(System.currentTimeMillis() / 1000);
                    for (float value : values) {
                        jsonArray.put(value);
                    }
                } catch (Exception e) {
                    Log.e("TAG", e.toString());
                }
                listBuilder.add(jsonArray);
            }
        }

//        switch (event.sensor.getType()) {
//            case Sensor.TYPE_ACCELEROMETER:
//            case Sensor.TYPE_MAGNETIC_FIELD:
//            case Sensor.TYPE_ROTATION_VECTOR:
//            case Sensor.TYPE_GYROSCOPE:
//            case Sensor.TYPE_ORIENTATION:
//            case Sensor.TYPE_LINEAR_ACCELERATION:
//                break;
//
//
//            case Sensor.TYPE_LIGHT:
//            case Sensor.TYPE_PRESSURE:
//            case Sensor.TYPE_PROXIMITY:
//            case Sensor.TYPE_RELATIVE_HUMIDITY:
//            case Sensor.TYPE_GRAVITY:
//                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
//                break;
//
//
//            case Sensor.TYPE_AMBIENT_TEMPERATURE:
//                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
//                break;
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    /**
     * 写sensor数据到sdcard中
     *
     * @param lists
     */
    public void writeSensorData(List<JSONArray> lists) {
        if (lists == null || lists.size() == 0) {
            return;
        }
        Log.v("TAG", "length=" + lists.size() + "list{" + lists.toString() + "}");
        for (JSONArray builder : lists) {
            FileUtil.writeFileFromString("/sdcard/Android/sensor.txt", builder.toString(), true);
        }
    }
}
