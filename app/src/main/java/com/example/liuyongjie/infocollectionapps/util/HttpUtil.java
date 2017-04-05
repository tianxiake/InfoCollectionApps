package com.example.liuyongjie.infocollectionapps.util;

import android.util.Log;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liuyongjie on 2017/3/28.
 */

public class HttpUtil {
    //上传成功code
    private static final int SUCCESS_CODE = 450;
    //上传失败code
    private static final int FAIL_CODE = 451;
    //时间不对code
    private static final int SYNC_TIME = 452;
    private static final ILogger log = LoggerFactory.getLogger("HttpUtil");
    private static final String key = "DBTshihuayongwuxiandiyidameinv";
    private static final String app = "dbt";
//    public boolean httpPostFile(File file, String urlStr) {
//        try {
//            URL url = new URL(urlStr);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //设置请求方式
//            conn.setRequestMethod("post");
//            //设置可接受的流的走向
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            //设置是否缓存
//            conn.setUseCaches(false);
//            //设置消息头
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("Charset", "utf-8");
//            conn.connect();
//            OutputStream outputStream = conn.getOutputStream();
//            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] buffer = new byte[2048];
//            int length = 0;
//            while ((length = fileInputStream.read(buffer)) != -1) {
//                dataOutputStream.write(buffer, 0, length);
//            }
//            dataOutputStream.flush();
//
////            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////            StringBuilder stringBuilder = new StringBuilder();
////            String result = "";
////            while ((result = reader.readLine()) != null) {
////                stringBuilder.append(result);
////            }
//            int code = conn.getResponseCode();
//            Log.d("TAG", code + "");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            CloseUtil.closeIO();
//        }}
////        return false;
////    }

    public void httpGetRequest() throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        final Request request = new Request.Builder().get().url("https://www.baidu.com/s?f=8&rsv_bp=1&rsv_idx=1&word=%E4%B8%AD%E5%9B%BD&tn=93093789_hao_pg").build();
        Call call = httpClient.newCall(request);
        Log.d("TAG", "call=" + call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", "Response call=" + call);
                int code = response.code();
                String responseString = response.body().string();
                Log.d("TAG", "code=" + code + ",responseString=" + responseString);
            }
        });
        Response response = httpClient.newCall(request).execute();
//        String responseString = response.body().string();
//        int code = response.code();
//        Log.d("TAG","code="+code+"response="+responseString);
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * Http上传文件
     *
     * @param url 请求的资源路径
     */
    public void httpPostFile(String url, File zipFile) {
        OkHttpClient httpClient = new OkHttpClient();
//        httpClient.retryOnConnectionFailure();
//        File file = new File("/sdcard/Android/sensor.txt");
        long time = System.currentTimeMillis();
        String token = string2MD5(key + time + app);
        //构建请求体
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), zipFile);
//        RequestBody body1 = RequestBody.create(MediaType.parse("application/octet-stream"), "客户端上传文件到服务器测试");
        log.verbose(Author.liuyongjie, Business.dev_test, "请求参数的头 time={},token={}", time, token);
        Request request = new Request.Builder().url(url).addHeader("v_app", app).addHeader("v_time", time + "").addHeader("v_token", token).post(body).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                long id = Thread.currentThread().getId();
                log.verbose(Author.liuyongjie, Business.dev_test, "onFailure() Thread id={}", id);
                log.error(Author.liuyongjie, e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                long id = Thread.currentThread().getId();
                log.verbose(Author.liuyongjie, Business.dev_test, "onResponse() Thread id={}", id);
                if (response != null) {
                    int code = response.code();
                    log.verbose(Author.liuyongjie, Business.dev_test, "Response code={}", code);
                    switch (code) {
                        case SUCCESS_CODE:
                            break;
                        case FAIL_CODE:

                            break;
                        case SYNC_TIME:
                            //对时
                            break;
                    }
                }

            }
        });

    }

    public void getAsyncHttp() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url("https://www.baidu.com/s?f=8&rsv_bp=1&rsv_idx=1&word=heelw&tn=98633779_hao_pg");
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
////                if (null != response.cacheResponse()) {
////                    String str = response.cacheResponse().toString();
////                    Log.i("wangshu", "cache---" + str);
////                } else {
//                String body = response.body().string();
//                String str = response.networkResponse().toString();
//                Log.d("TAG", "body=" + body);
//                Log.i("TAG", "network---" + str);
////                }
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
////                    }
////                });
            }
        });
    }


}
