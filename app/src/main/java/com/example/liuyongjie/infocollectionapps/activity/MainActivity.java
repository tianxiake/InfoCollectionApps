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
import com.example.liuyongjie.infocollectionapps.util.FileUtil;
import com.example.liuyongjie.infocollectionapps.util.HttpUtil;
import com.example.liuyongjie.infocollectionapps.util.RSAUtil;
import com.example.liuyongjie.infocollectionapps.util.SdcardUtil;
import com.example.liuyongjie.infocollectionapps.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import static com.example.liuyongjie.infocollectionapps.util.FileUtil.readFile2String;
import static com.example.liuyongjie.infocollectionapps.util.FileUtil.writeFileFromString;
import static com.example.liuyongjie.infocollectionapps.util.ZipUtil.zipFile;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button sensorButton;
    private Button postButton;
    private static String url = "http://10.0.1.166:4949/test";
    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALgG+1YD01hDZeuw8Cw80gjNNLC9" +
            "6EH7tROKFEhO87GNJgNUxzXQ3VnL+TyVly4yJ3vQ7lUbkpnc7e8JLQgiqneouL+MEFXBXWObdmXz" +
            "t+E4TpTnjQHTqiBfIR3CcsvIK9OsWIrkSzILOOcTrpf04nX6HGn4EIVBvGUwFh24FKW3AgMBAAEC" +
            "gYAcYhdJwPVL27lQjM2+RPMwIFZMHD5CTwwyo01VibfUXqzKPr0q87fwLaGXUosquNmWIzdfMQ1/" +
            "Za/c+lFTu+UGpKSo/TFVP2UvSChtrYR7kaFuYLwYCa8lpCzQ54u+nWjytQTyQxS6BkdZxtsL5xWK" +
            "0Qs9Oc7JUpkbTEfFhHqSwQJBAOC5pn2zvufdEYbjUn2l06KA7WdmzPHC7im6xyI1xJ/PF/GXEK0V" +
            "V2fBoPfLbJmNBvFXUfns61tUfyNDFJVYhU8CQQDRo1/sXYpe3Ak1Gd4gAdENUafZ1/qWJZ4d1aVu" +
            "yOBt+HABEiKV2w5UaZieC2tUAMuP9hF7jjJuf9wwNCR7YA8ZAkBmdv/c7Oos2nW1ZU0lkUjQHa0h" +
            "qpPj4Ber20gU5yNCIrEuLM0jvkleO8FjetOHp+/0dvYf2NDvkbVupJVsCzKtAkEAwgjYeRsfPquk" +
            "rgySRsfHqe9BT+WTH7QTxIOByqeM6tx/Ns6FSnTlr4XJZ8ckAS13PHlCNz0nOpWHxOoONk9zyQJA" +
            "cehKjJm94jIuzoP7czxezfwTtncbYOoIausOs2gNQfYbtDaIQDWFULtbD/YvDjToIlSJoQNKOfns" +
            "2YgQOzICVw==";
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4BvtWA9NYQ2XrsPAsPNIIzTSwvehB+7UTihRI" +
            "TvOxjSYDVMc10N1Zy/k8lZcuMid70O5VG5KZ3O3vCS0IIqp3qLi/jBBVwV1jm3Zl87fhOE6U540B" +
            "06ogXyEdwnLLyCvTrFiK5EsyCzjnE66X9OJ1+hxp+BCFQbxlMBYduBSltwIDAQAB";

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

//        try {
//            TimeUtil.currentTime("Time", "start");
//            FileRWTest fileRWTest = new FileRWTest();
//            fileRWTest.readFileToFile("/sdcard/Android/text.txt", "/sdcard/Android/dest.txt");
//            TimeUtil.currentTime("Time", "end");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            KeyPair keyPair = RSAUtil.createKeyPairs();
//            byte[] publicByte = keyPair.getPublic().getEncoded();
//            byte[] privateByte = keyPair.getPrivate().getEncoded();
//            String publicKey = new String(Base64.encode(publicByte, Base64.DEFAULT));
//            String privateKey = new String(Base64.encode(privateByte, Base64.DEFAULT));
//            FileUtil.writeFileFromString("/sdcard/Android/privateKey.txt", privateKey, false);
//            FileUtil.writeFileFromString("/sdcard/Android/publicKey.txt", privateKey, false);
//            Log.d("TAG", privateKey);
//            Log.d("TAG", publicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        httpTest();

//        try {
//            byte[] encryptByte = RSAUtil.encrypt(RSAUtil.getPrivateKey(privateKey), new byte[]{11, 22, 3, 44, 12, 45});
//            Log.d("TAG", Arrays.toString(encryptByte));
//            byte[] decryptByte = RSAUtil.decrypt(RSAUtil.getPublicKey(publicKey), encryptByte);
//            Log.d("TAG", Arrays.toString(decryptByte));
//
//        } catch (Exception e) {
//
//        }
        encryptTest();
    }

    private void initView() {
        sensorButton = (Button) findViewById(R.id.sensor_button);
        postButton = (Button) findViewById(R.id.post_button);
    }

    private void setListener() {
        sensorButton.setOnClickListener(this);
        postButton.setOnClickListener(this);
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
        writeFileFromString(FILE_PATH + "b.dat", appListJsonObject.toString(), false);
    }

    //    public void memoryTest() {
//        for (int i = 0; i < Integer.MAX_VALUE; i++) {
//            MyAppInfo myAppInfo = new MyAppInfo("packageName" + i);
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
            writeFileFromString(FILE_PATH + "C.dat", jsonStr, false);
        }

        try {
            String jsonStr = readFile2String(new File(FILE_PATH + "C.dat"), "utf-8");
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
            writeFileFromString("/sdcard/Android/sdcard.txt", jsonArray.toString(), false);
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
            Boolean success = zipFile(new File("/sdcard/Android/sdcard.txt"), new File("/sdcard/Android/sdcard.zip"), null);
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
        switch (v.getId()) {
            case R.id.sensor_button:
                Intent intent = new Intent(this, SensorActivity.class);
                startActivity(intent);
                break;
            case R.id.post_button:
                ToastUtil.show(this, "开始上传文件!", Toast.LENGTH_LONG);
                //发送文件
                httpTest(url);
                break;
        }

    }

    //动态广播
    public class InnerBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", intent.getAction());
        }
    }

    //http测试区
    public void httpTest(String url) {
        HttpUtil httpUtil = new HttpUtil();
        try {
//            boolean zipSuccess = ZipUtil.zipFile(new File(FilePathConstants.SENSOR_PATH), new File(FilePathConstants.ZIP_SENSOR_PATH), null);
//            if (zipSuccess) {
//                //对文件进行加密
////                RSAUtil.encrypt()
////                File file = new File(FilePathConstants.ZIP_SENSOR_PATH);
//                byte[] bytes = FileUtil.readFile2String(new File(FilePathConstants.ZIP_SENSOR_PATH), "utf-8").getBytes();
////                byte[] RSAUtil.encrypt(RSAUtil.getPublicKey(""),bytes);
//                httpUtil.httpPostFile(url, new File(FilePathConstants.ZIP_SENSOR_PATH));
//            }
            httpUtil.httpPostFile(url, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加密测试
    public void encryptTest() {
//        String string = FileUtil.readFile2String(new File("/sdcard/Android/sdcard.txt"), "utf-8");
//        for (int i = 0; i < 20; i++) {
//            FileUtil.writeFileFromString("/sdcard/Android/sdcard.txt", string, true);
//        }
//        byte[] strBytes = FileUtil.readFile2String(new File("/sdcard/Android/sdcard.txt"), "utf-8").getBytes();
//        byte[] encryptByte = RSAUtil.encrypt(RSAUtil.getPublicKey(publicKey), strBytes);
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
//        boolean success = writeFileFromIS(new File("/sdcard/Android/encryptByte.txt"), new ByteArrayInputStream(encryptByte), false);
//        if (success) {
//            ToastUtil.show(this, "写入文件成功", Toast.LENGTH_LONG);
//        }
//        byte[] bytes = FileUtil.readFile2String(new File("/sdcard/Android/encryptByte.txt"), "utf-8").getBytes();
//        Log.d("TAG", bytes.length + "");
//        byte[] decryptByte = RSAUtil.decrypt(RSAUtil.getPrivateKey(privateKey), bytes);
//        try {
//            String str = new String(decryptByte, "utf-8");
//            boolean writeFile = FileUtil.writeFileFromString("/sdcard/Android/decryptByte.txt", str, false);
//            if (writeFile) {
//                ToastUtil.show(this, "写入文件成功", Toast.LENGTH_LONG);
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        boolean writeFile = FileUtil.writeFileFromIS(new File("/sdcard/Android/decryptByte.txt"), new ByteArrayInputStream(decryptByte), false);
//        if (writeFile) {
//            ToastUtil.show(this, "写入文件成功", Toast.LENGTH_LONG);
        new Thread() {
            @Override
            public void run() {
                try {
                    byte[] encryptByte = FileUtil.readFile2Bytes("/sdcard/Android/encryptByte.txt");
                    byte[] decryptByte = RSAUtil.decrypt(RSAUtil.getPrivateKey(privateKey), encryptByte);
                    Log.d("TAG", decryptByte + "");
                    if (decryptByte != null) {
                        boolean write = FileUtil.writeFileFromIS(new File("/sdcard/Android/decryptByte.txt"), new ByteArrayInputStream(decryptByte), false);
                        Log.d("TAG", "write=" + write);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}

