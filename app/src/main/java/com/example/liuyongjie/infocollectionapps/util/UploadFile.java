package com.example.liuyongjie.infocollectionapps.util;

import android.util.*;
import android.util.Base64;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liuyongjie on 2017/4/7.
 */

public class UploadFile {
    public static String sk =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ9gPddugdnSW7NhHbE309hQ9UEW" +
                    "atZ2QnpSaxfvXkbAAGl2VYB1WLPdtKiZArk3EdG8u9A0F4DWgVv5mgFpEaSzjmpA5sJBZMF8Td8k" +
                    "52VbJCrfU9UF5BfjTQri9wzQhXcPPmtpMEj4u6PrMt6RuOZiJ0epOTMpRZ7KDocqqfnhAgMBAAEC" +
                    "gYEAhGMmm5hxBqZFhBjs2DP+plaBk2JyttkfWYqy6PvuPSjqbrBcT8uqNia5Fb+cUowbIjT7cfSD" +
                    "fYMxi1woAHntq6mnDohR0o6+QwDTpEHIFemjkjRA5ktR7Bljlpvy3rZluJ1TUuC9OqZ+ccM8Rqk+" +
                    "6ng2Cpy6TAoORv8ZMOJked0CQQDRu1wTRLRiIPRGROVm4RCpSZR7DneOi67Pin5aU7TBFG0BruQF" +
                    "+3mXxHWIsAPJUa8YvxGv+eHd+14O+17zC1pvAkEAwokF25T6Ig4y6XAB1ohY6NQhtFN17Xiui8Fh" +
                    "+58h0zNKYN7h/ZIGr7HNyFpurKHUU5XqVbaWo++DUQl6ur9YrwJAJzCRVfBinGt3+aFqFD099cQQ" +
                    "AKaFZJdpRNKmJY66mdGNROE/Lnb9E4TcSXxKWNXwl/kr/uv8bpRH0RjbdyLJ9QJALnvndmzGyFR0" +
                    "PeuRxN2XwSrPUvOOfkwUCTkQXLvNrVXYNAWOIrU9+8WU0ocpYv7YaZCtCgYzQMD+s+J7/Ruz6QJA" +
                    "NEXp3m3T5nlyXjkJZacLpwDrvqsHoI3RCNdOWUqoH5BdTkQJRn92d4yPMtUxMqqGCgaka4HaZVxp" +
                    "HKcdXwy/TA==";
    private static String pk =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChslLbbeXAwgWgLCHgW52M/fHKsvwq1RrxZAR5" +
                    "hUstiGTbVobTZQt+TikyySIlZSq51fCwZCck0kaAKYOsqEEshCbpLI/eculRHEA+xLpw+SmhEObV" +
                    "oIsWZUDP6cfl88Wrgt9Hpx/0JR6BFKP1DVcRKpJjbWagpQIscpL4iCh3dwIDAQAB";
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
        //生成AES公钥
        String aesRawStr = AESUtil.createAESKey();
        byte[] encodeByte = RSAUtil.encryptByPublicKey(aesRawStr.getBytes(), pk);
        String aesEncodeStr = new String(Base64.encode(encodeByte, Base64.DEFAULT));
        log.info(Author.liuyongjie, Business.dev_test, "获取AES钥匙", "aesRawStr={},aesEncodeStr={}", aesRawStr, aesEncodeStr);
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
        conn.setRequestProperty("key", aesEncodeStr);
        FileUtil.writeFileFromString("/sdcard/Android/Key/aesKey", aesRawStr, false);
        byte[] contentByte = AESUtil.encrypt(fileName.getBytes(), aesRawStr, "hell");

        //获取输出流：
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(contentByte);
        outputStream.flush();

        int code = conn.getResponseCode();
        Log.d("Http", code + "");

    }


    public interface UploadListener {
        void onFailure();

        void onResponse();
    }
}
