package com.example.liuyongjie.infocollectionapps.util;

import android.util.*;
import android.util.Base64;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liuyongjie on 2017/4/7.
 */

public class UploadFile {
    //上传成功code
    private static final int SUCCESS_CODE = 450;
    //上传失败code
    private static final int FAIL_CODE = 451;
    //时间不对code
    private static final int SYNC_TIME = 452;
    private static final ILogger log = LoggerFactory.getLogger("HttpUtil");
    private static final String key = "DBTshihuayongwuxiandiyidameinv";
    private static final String app = "dbt";

    //aesKey
    public static final String aesKey = "8vuJNlbCZSts0z8ayuUd+6GEoqPZRSaX";
    //rsaPub
    public static final String rsaPub =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNrWm9Y/PuqmxN2vF9/jsvMh+z+SNOsW9Cb7vv" +
                    "vD2KBFe55HMrmEbGSwbqccnUqMwzsfzEcV9ZNJ5Wbjw5oOdkzC/HS+1143MDZSKgMj1wmyG+PAz6" +
                    "f2tzG/NiKqv5A7MU/HWxFNV/JpciVrXDoDcm8Gkg66Yl1h3OcosI1NIF3QIDAQAB";

    //rsaPri
    public static final String rsaPri =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANieP399qW+BkfbTC5zdfAhm/0e2" +
                    "dCNl/d1AI+wuhVO+qGM/jvjGe4WNaiDrDicFyI7cfTIZKDYLMZqyx0ec0mmOCHtVs23kVtJmA0Vk" +
                    "t4j4wQw0hc3040EibRtU4JVRtPr3LGa3aAfJyl2/2F4J11XYWKXGpx2E3sQBtAdtWmgzAgMBAAEC" +
                    "gYBZco8Z4BP1HXXejCtBbRvohE9pl2fBrj3z/KZYwqT/fb0eroIlLCCZd1mZmjZP2s2EYHu6EjVO" +
                    "i7TCAozTf2hKt38lscBg7xC1jlwlGLZjB0MelK8Uh2f70IhLwlpXFa6TipgHW2ZdkSvcquIeJZIr" +
                    "oieO4+dLSD7Y4k2W8NSewQJBAO+XkU7Vx3JYYnOLsQfIVQU5eVOfuPaZCH52tsWXJbmXOrJlg1ZQ" +
                    "3W/m0OTmNdqyIJ3YOJeuFKkcf3o4YNbbjgMCQQDnc+k+HDEOEHsK86isqocMk993cc9iQXzg2Czy" +
                    "+Q1dJbRgSjpbkZDJKPLTDI8SSkKGn15zRUAIN++4l/Yj9f4RAkBXgnOnaLvFTvT+oPoTDp/HHbvr" +
                    "yD3li/WnQzL0roUv6UWeHSfAYh7PHW4U/OSUsVTqciwRcprHPcpt2KO+NkkDAkEAoKaKwaqtvfZg" +
                    "gUSI0nh2zpshI/YiHTAZbrN915RY20udfKxo/z/1gDrk+Oqhr+9NZ8XcuCPcAxhYUwdfOKy5EQJB" +
                    "AIQXijCEmiiAoTRhsYdVUR7dDXg++FCUgpn37G2SlRCgJhihm4uahXaqR4XZ7661lDP1J0EVwX2f" +
                    "P3ZUaGv2f0I=";

    public void UploadFile(String urlPath, String fileName, UploadListener listener) throws Exception {
        //构造url对象
        URL url = new URL(urlPath);

        long time = System.currentTimeMillis();
        String token = HttpUtil.string2MD5(key + time + app);

        //获取HttpURLConnection对象,并设置配置
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(100000);
        //----设置请求行----
        //请求方式的设置
        conn.setRequestMethod("GET");

        //----设置请求header----
        conn.setRequestProperty("Charset", "utf-8");
        conn.setRequestProperty("Content-type", "application/octet-stream");
        conn.setRequestProperty("v_app", app);
        conn.setRequestProperty("v_time", time + "");
        conn.setRequestProperty("v_token", token);

        //RSA加密AES Key
        byte[] encryptBytes = RSAUtil.decryptByPublicKey(aesKey.getBytes(), rsaPub);
        String encryptStr = android.util.Base64.encodeToString(encryptBytes, Base64.DEFAULT);
        conn.setRequestProperty("key", encryptStr);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        //获取输出流：
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(fileName.getBytes());
        outputStream.flush();

        int code = conn.getResponseCode();
        Log.d("Http", code + "");

    }


    public interface UploadListener {
        void onFailure();

        void onResponse();
    }
}
