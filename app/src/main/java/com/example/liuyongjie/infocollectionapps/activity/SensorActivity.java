package com.example.liuyongjie.infocollectionapps.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.liuyongjie.infocollectionapps.R;

import java.util.Arrays;

public class SensorActivity extends Activity implements SensorEventListener {
    private TextView messageTextView;
    private SensorManager sensorManager;
    //传感器数据采集的时间
    private int time = 5 * 1000 * 1000;

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
//        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
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

    private void setListener() {
    }

    private void initView() {
        messageTextView = (TextView) findViewById(R.id.messageTextView);
    }

    private float[] oldFloat;

    //传感器数值变化时回调
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;//最新的数据
        oldFloat = values;
        int type = event.sensor.getType();
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_GYROSCOPE:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_LIGHT:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_PRESSURE:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_PROXIMITY:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_ORIENTATION:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_GRAVITY:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                Log.d("TAG", "type=" + type + "," + Arrays.toString(values));
                break;
        }
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
}
