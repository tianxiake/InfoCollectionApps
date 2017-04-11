package com.example.liuyongjie.infocollectionapps.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liuyongjie.infocollectionapps.R;
import com.example.liuyongjie.infocollectionapps.center.DataCenter;
import com.example.liuyongjie.infocollectionapps.util.FileUtil;
import com.example.liuyongjie.infocollectionapps.util.RSAUtil;
import com.example.liuyongjie.infocollectionapps.util.SdcardUtil;
import com.example.liuyongjie.infocollectionapps.util.ToastUtil;
import com.example.liuyongjie.infocollectionapps.util.UploadFile;
import com.example.liuyongjie.infocollectionapps.util.ZipUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static com.example.liuyongjie.infocollectionapps.util.FileUtil.readFile2String;
import static com.example.liuyongjie.infocollectionapps.util.FileUtil.writeFileFromString;
import static com.example.liuyongjie.infocollectionapps.util.RSAUtil.decryptByPrivateKey;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button sensorButton;
    private Button postButton;
    private static String url = "http://10.0.1.166:4949/test";
    //    private static String getUrl = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=%E4%B8%AD%E5%9B%BD&rsv_pq=9379c7840002326c&rsv_t=4ae72Sk1opqSg2XqdMYD8i1HGq7GPLVaerK27pjVBehhnYlUSy9b%2FLo%2BlrY&rqlang=cn&rsv_enter=1&rsv_sug3=3&rsv_sug2=0&inputT=1063&rsv_sug4=1558";
//    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALgG+1YD01hDZeuw8Cw80gjNNLC9" +
//            "6EH7tROKFEhO87GNJgNUxzXQ3VnL+TyVly4yJ3vQ7lUbkpnc7e8JLQgiqneouL+MEFXBXWObdmXz" +
//            "t+E4TpTnjQHTqiBfIR3CcsvIK9OsWIrkSzILOOcTrpf04nX6HGn4EIVBvGUwFh24FKW3AgMBAAEC" +
//            "gYAcYhdJwPVL27lQjM2+RPMwIFZMHD5CTwwyo01VibfUXqzKPr0q87fwLaGXUosquNmWIzdfMQ1/" +
//            "Za/c+lFTu+UGpKSo/TFVP2UvSChtrYR7kaFuYLwYCa8lpCzQ54u+nWjytQTyQxS6BkdZxtsL5xWK" +
//            "0Qs9Oc7JUpkbTEfFhHqSwQJBAOC5pn2zvufdEYbjUn2l06KA7WdmzPHC7im6xyI1xJ/PF/GXEK0V" +
//            "V2fBoPfLbJmNBvFXUfns61tUfyNDFJVYhU8CQQDRo1/sXYpe3Ak1Gd4gAdENUafZ1/qWJZ4d1aVu" +
//            "yOBt+HABEiKV2w5UaZieC2tUAMuP9hF7jjJuf9wwNCR7YA8ZAkBmdv/c7Oos2nW1ZU0lkUjQHa0h" +
//            "qpPj4Ber20gU5yNCIrEuLM0jvkleO8FjetOHp+/0dvYf2NDvkbVupJVsCzKtAkEAwgjYeRsfPquk" +
//            "rgySRsfHqe9BT+WTH7QTxIOByqeM6tx/Ns6FSnTlr4XJZ8ckAS13PHlCNz0nOpWHxOoONk9zyQJA" +
//            "cehKjJm94jIuzoP7czxezfwTtncbYOoIausOs2gNQfYbtDaIQDWFULtbD/YvDjToIlSJoQNKOfns" +
//            "2YgQOzICVw==";
//    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4BvtWA9NYQ2XrsPAsPNIIzTSwvehB+7UTihRI" +
//            "TvOxjSYDVMc10N1Zy/k8lZcuMid70O5VG5KZ3O3vCS0IIqp3qLi/jBBVwV1jm3Zl87fhOE6U540B" +
//            "06ogXyEdwnLLyCvTrFiK5EsyCzjnE66X9OJ1+hxp+BCFQbxlMBYduBSltwIDAQAB";

    public static String sk =
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMtJYs4ffxjHiIEm8UEv+huLgXtO" +
                    "g7dGVGM6vQJb9VRzZpROGnc8Du3CLeP3kCvfUf1/fnmgqoDO+e7nynBC7Mrs3WJ9DkKNyObCkv4F" +
                    "97kram+Fl3gX/ctXRmq7OgAjkof3qGmr1jCsXTCsw3nmVvTtCw2uicx6wKBN68VU4TgzAgMBAAEC" +
                    "gYAGKp+4qPPuCH93i3reA1qO41h2KReNw7BjojCBldCHU0jVTtk2rLe3NqC1vZi9+/Q8wFVR0zHY" +
                    "3m5udNGLPjx4jXpd7eT8hRBICH2q0uFEOHogN4gUJXdkl8WzFfafng8YPe/IQ0QMpJHtV/cwKgLe" +
                    "8kXLpDp2AzkQ+8x/Bp2cQQJBAPjZ9yAbTjthxa5KW3j5kyb3wJKEd+Kyc4fioDB9XJfApUmEzMxS" +
                    "5rjKeKno7069WDblaAX0FjdLbIztsIsKP3sCQQDRIFc9qwvAh3BNwUEgz+f7Y6gKNxk/p8tMQvjx" +
                    "iO0dwZ8SEEVuJxl0A8rTZZlgGDN8XIPqwsBZNdHGd8s+rvCpAkEAiyM7tHzv8e3J3JiAqpRIvZn6" +
                    "1zEv4tXKGOkSjeoZ8lNpV0DkTT3w+NNkQgQWgZ0GjLMZxXJjVYlbaTSg6CzTrwJBAMoiinDPDbLw" +
                    "trpRW5RNoRs5/kixbTQ8CaMS8PD1usuSRSD+nT2ViWK776ZZg+CAQ/OmsNOPnsAb50IAtHFIQtEC" +
                    "QQC7djTyDgq4oMpgRYRn91RWvHU/JeZ1pWQZVJyOAq97sC4000VCzR3MEd5jbKG7MmyFzPQW4scI" +
                    "pL8bbdafVBrI";

    public static String pk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLSWLOH38Yx4iBJvFBL/obi4F7ToO3RlRjOr0C" +
            "W/VUc2aUThp3PA7twi3j95Ar31H9f355oKqAzvnu58pwQuzK7N1ifQ5CjcjmwpL+Bfe5K2pvhZd4" +
            "F/3LV0ZquzoAI5KH96hpq9YwrF0wrMN55lb07QsNronMesCgTevFVOE4MwIDAQAB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
//        uploadTest();

//        DataCenter center = new DataCenter();
//        JSONObject wifiJson = center.getWifiJsonObject(this);
//        FileUtil.writeFileFromString("/sdcard/Android/wifiAll", wifiJson.toString(), false);



    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告").setMessage("你犯规了").create().show();

    }


    //上传测试
    public void uploadTest() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                UploadFile uploadFile = new UploadFile();
                try {
                    uploadFile.UploadFile(url, "客户端和服务器测试！", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 生成RSA公密钥对
     */
    public void createkey() {
        try {
            RSAUtil.genKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加解密测试
     */
    public void ENAndDETest() throws Exception {
        String content = "hello world";
        //加密部分
        byte[] encryptByte = RSAUtil.encryptByPublicKey(content.getBytes(), pk);
//        String encryptStr = new String(encryptByte, "utf-8");
        String encryptStr = new String(Base64.encode(encryptByte, Base64.DEFAULT));

        byte[] decryptByte2 = decryptByPrivateKey(encryptByte, sk);
        String decryptStr2 = new String(decryptByte2);

        //解密部分

        byte[] bytes = Base64.decode(encryptStr, Base64.DEFAULT);
        byte[] decryptByte = decryptByPrivateKey(bytes, sk);
        String decryptStr = new String(decryptByte);
    }

    //获得栈的信息
    public void getDumpSys() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("dumpsys");
            InputStream inputStream = process.getInputStream();
            boolean success = FileUtil.writeFileFromIS(new File("/sdcard/Android/dumpsys.txt"), inputStream, false);
            if (success) {
                ToastUtil.show(this, "写入文件成功", Toast.LENGTH_LONG);
            }
        } catch (IOException e) {
            Log.d("TAG", e.toString());
        }

    }

//    //获得栈的信息
//    public void get() {
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process process = runtime.exec("dumpsys");
//            InputStream inputStream = process.getInputStream();
//            boolean success = FileUtil.writeFileFromIS(new File("/sdcard/Android/dumpsys.txt"), inputStream, false);
//            if (success) {
//                ToastUtil.show(this, "写入文件成功", Toast.LENGTH_LONG);
//            }
//        } catch (IOException e) {
//            Log.d("TAG", e.toString());
//        }
//
//    }

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
//        DataCenter center = new DataCenter();
//        JSONObject appListJsonObject = center.getAppListJsonObject(this);
//        Log.d("TAG", appListJsonObject.toString());
//        writeFileFromString(FILE_PATH + "b.dat", appListJsonObject.toString(), false);
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
        Collection<File> files = new ArrayList<>();
        files.add(new File("/sdcard/Android/B.dat"));
        files.add(new File("/sdcard/Android/C.dat"));
        files.add(new File("/sdcard/Android/D.dat"));
        files.add(new File("/sdcard/Android/E.dat"));
        try {
            ZipUtil.zipFiles(files, "/sdcard/Android/all.zip");
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
//                //发送文件
//                httpTest(url);
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


    //加密测试
    public void encryptTest() {
//        String string = FileUtil.readFile2String(new File("/sdcard/Android/sdcard.txt"), "utf-8");
//        for (int i = 0; i < 20; i++) {
//            FileUtil.writeFileFromString("/sdcard/Android/sdcard.txt", string, true);
//        }
//        byte[] strBytes = FileUtil.readFile2String(new File("/sdcard/Android/sdcard.txt"), "utf-8").getBytes();
//        byte[] encryptByte = LYJRSAUtil.encrypt(LYJRSAUtil.getPublicKey(publicKey), strBytes);
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream()
//        boolean success = writeFileFromIS(new File("/sdcard/Android/encryptByte.txt"), new ByteArrayInputStream(encryptByte), false);
//        if (success) {
//            ToastUtil.show(this, "写入文件成功", Toast.LENGTH_LONG);
//        }
//        byte[] bytes = FileUtil.readFile2String(new File("/sdcard/Android/encryptByte.txt"), "utf-8").getBytes();
//        Log.d("TAG", bytes.length + "");
//        byte[] decryptByte = LYJRSAUtil.decrypt(LYJRSAUtil.getPrivateKey(privateKey), bytes);
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
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    long startTime = System.currentTimeMillis();
//                    byte[] encryptByte = FileUtil.readFile2Bytes("/sdcard/Android/encryptByte.txt");
//                    byte[] decryptByte = LYJRSAUtil.decrypt(LYJRSAUtil.getPrivateKey(privateKey), encryptByte);
//                    Log.d("TAG", "解码时间=" + (System.currentTimeMillis() - startTime));
//                    Log.d("TAG", decryptByte + "");
//                    if (decryptByte != null) {
//                        boolean write = writeFileFromIS(new File("/sdcard/Android/decryptByte.dat"), new ByteArrayInputStream(decryptByte), false);
//                        Log.d("TAG", "write=" + write);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

//        new Thread() {
//            @Override
//            public void run() {
////                super.run();
//                byte[] encryptByte = FileUtil.readFile2Bytes("/sdcard/Android/encryptByte.txt");
////                FileUtil.writeFileFromIS()
//                FileUtil.writeFileFromIS()
//            }
//        }.start();

    }
}

