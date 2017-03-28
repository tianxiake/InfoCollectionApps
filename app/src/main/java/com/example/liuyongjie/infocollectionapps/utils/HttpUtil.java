package com.example.liuyongjie.infocollectionapps.utils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liuyongjie on 2017/3/28.
 */

public class HttpUtil {

    public boolean httpPostFile(File file, String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式
            conn.setRequestMethod("post");
            //设置可接受的流的走向
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置是否缓存
            conn.setUseCaches(false);
            //设置消息头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset", "utf-8");
            conn.connect();
            OutputStream outputStream = conn.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[2048];
            int length = 0;
            while ((length = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, length);
            }
            dataOutputStream.flush();

//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            StringBuilder stringBuilder = new StringBuilder();
//            String result = "";
//            while ((result = reader.readLine()) != null) {
//                stringBuilder.append(result);
//            }
            int code = conn.getResponseCode();
            Log.d("TAG", code + "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeIO();
        }
        return false;
    }


}
