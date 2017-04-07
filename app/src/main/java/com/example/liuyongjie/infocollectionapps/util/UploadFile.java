package com.example.liuyongjie.infocollectionapps.util;

import android.util.Log;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;

import java.io.IOException;
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

    public void UploadFile(String urlPath, String fileName, UploadListener listener) throws IOException {
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
