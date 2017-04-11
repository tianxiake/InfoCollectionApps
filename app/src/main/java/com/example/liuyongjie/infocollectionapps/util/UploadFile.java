package com.example.liuyongjie.infocollectionapps.util;

import android.util.Base64;
import android.util.Log;

import com.example.liuyongjie.infocollectionapps.activity.MainActivity;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
    private static final ILogger log = LoggerFactory.getLogger("UploadFile");
    private static final String key = "DBTshihuayongwuxiandiyidameinv";
    private static final String app = "dbt";

//    //aesKey
//    public static final String aesKey = "8vuJNlbCZSts0z8ayuUd+6GEoqPZRSaX";
//    //rsaPub
//    public static final String rsaPub =
//            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNrWm9Y/PuqmxN2vF9/jsvMh+z+SNOsW9Cb7vv" +
//                    "vD2KBFe55HMrmEbGSwbqccnUqMwzsfzEcV9ZNJ5Wbjw5oOdkzC/HS+1143MDZSKgMj1wmyG+PAz6" +
//                    "f2tzG/NiKqv5A7MU/HWxFNV/JpciVrXDoDcm8Gkg66Yl1h3OcosI1NIF3QIDAQAB";
//
//    //rsaPri
//    public static final String rsaPri =
//            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANieP399qW+BkfbTC5zdfAhm/0e2" +
//                    "dCNl/d1AI+wuhVO+qGM/jvjGe4WNaiDrDicFyI7cfTIZKDYLMZqyx0ec0mmOCHtVs23kVtJmA0Vk" +
//                    "t4j4wQw0hc3040EibRtU4JVRtPr3LGa3aAfJyl2/2F4J11XYWKXGpx2E3sQBtAdtWmgzAgMBAAEC" +
//                    "gYBZco8Z4BP1HXXejCtBbRvohE9pl2fBrj3z/KZYwqT/fb0eroIlLCCZd1mZmjZP2s2EYHu6EjVO" +
//                    "i7TCAozTf2hKt38lscBg7xC1jlwlGLZjB0MelK8Uh2f70IhLwlpXFa6TipgHW2ZdkSvcquIeJZIr" +
//                    "oieO4+dLSD7Y4k2W8NSewQJBAO+XkU7Vx3JYYnOLsQfIVQU5eVOfuPaZCH52tsWXJbmXOrJlg1ZQ" +
//                    "3W/m0OTmNdqyIJ3YOJeuFKkcf3o4YNbbjgMCQQDnc+k+HDEOEHsK86isqocMk993cc9iQXzg2Czy" +
//                    "+Q1dJbRgSjpbkZDJKPLTDI8SSkKGn15zRUAIN++4l/Yj9f4RAkBXgnOnaLvFTvT+oPoTDp/HHbvr" +
//                    "yD3li/WnQzL0roUv6UWeHSfAYh7PHW4U/OSUsVTqciwRcprHPcpt2KO+NkkDAkEAoKaKwaqtvfZg" +
//                    "gUSI0nh2zpshI/YiHTAZbrN915RY20udfKxo/z/1gDrk+Oqhr+9NZ8XcuCPcAxhYUwdfOKy5EQJB" +
//                    "AIQXijCEmiiAoTRhsYdVUR7dDXg++FCUgpn37G2SlRCgJhihm4uahXaqR4XZ7661lDP1J0EVwX2f" +
//                    "P3ZUaGv2f0I=";

    private static final String iv = "abcdefghijklmnop";

    public void UploadFile(String urlPath, String fileName, UploadListener listener) throws Exception {
        //构造url对象
        URL url = new URL(urlPath);

        long time = System.currentTimeMillis();
        String token = HttpUtil.string2MD5(key + time + app);
        //随机生成16位AES公钥
        String aesRawStr = StringUtils.randomString(16);//生成AESKey utf-8
        //加密后字节数组 utf-8编码
        byte[] encodeByte = RSAUtil.encryptByPublicKey(aesRawStr.getBytes(), MainActivity.pk);
        //加密后的字符串密钥 Base64编码
        String aesEncodeStr = new String(Base64.encode(encodeByte, Base64.DEFAULT));
        Log.d("Http", "AESKey明文=" + aesRawStr + ",RSA加密AESKey后字符串=" + aesEncodeStr);
        //获取HttpURLConnection对象,并设置配置
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(100000);
        //----设置请求行----
        //请求方式的设置
        conn.setRequestMethod("POST");

        //----设置请求header----
        conn.setRequestProperty("Charset", "utf-8");
        conn.setRequestProperty("v_app", app);
        conn.setRequestProperty("v_time", time + "");
        conn.setRequestProperty("v_token", token);
        //RSA加密AES Key ,key的值编码方式是Base64
        aesEncodeStr = URLEncoder.encode(aesEncodeStr, "utf-8");
        conn.setRequestProperty("v_key", aesEncodeStr);
        Log.d("Http", "URL Encode aesEncodeStr=" + aesEncodeStr);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        //AES加密传输内容
        byte[] content = AESUtil.encrypt(fileName.getBytes("utf-8"), aesRawStr, iv);

        //解密测试区
        Log.d("Http", "content length=" + content.length);
        String a = Base64.encodeToString(content, Base64.DEFAULT);
        Log.d("Http", "a=" + a);
        String result = Base64.encodeToString(content, Base64.DEFAULT);
        Log.d("Http", "AES加密后内容=" + result);
        byte[] decrypt = LAESUtil.decrypt(content, aesRawStr, iv);
        String rawStr = new String(decrypt);
        Log.d("Http", "AES解密后内容=" + rawStr);


        //获取输出流：
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(content);
        outputStream.flush();

        int code = conn.getResponseCode();
        Log.d("Http", code + "");

    }


    public interface UploadListener {
        void onFailure();

        void onResponse();
    }
}
