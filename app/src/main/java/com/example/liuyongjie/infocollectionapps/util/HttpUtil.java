package com.example.liuyongjie.infocollectionapps.util;

import android.util.Log;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    //代办事项（http加密部分）TODO 标记人：刘永杰 标记时间：2017-4-5 预计处理时间2017-4-6

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
//        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), zipFile);
        RequestBody body1 = RequestBody.create(MediaType.parse("application/octet-stream"), "客户端上传文件到服务器测试");
        log.verbose(Author.liuyongjie, Business.dev_test, "请求参数的头 time={},token={}", time, token);
        Request request = new Request.Builder().url(url).addHeader("v_app", app).addHeader("v_time", time + "").addHeader("v_token", token).post(body1).build();
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

    //代码错误不能工作 FIXME 标记人：刘永杰 标记时间：2017-4-5 预计处理时间2017-4-6
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


    public static String upload(String actionUrl, String FileName) throws IOException {
        // 产生随机分隔内容
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        long time = System.currentTimeMillis();
        String token = string2MD5(key + time + app);
        // 定义URL实例
        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        // 设置从主机读取数据超时
        conn.setReadTimeout(10 * 1000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        // 维持长连接
//        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charset", "UTF-8");
        //header
        conn.setRequestProperty("v_app", app);
        conn.setRequestProperty("v_time", time + "");
        conn.setRequestProperty("v_token", token);
        // 设置文件类型
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        conn.connect();
        // 创建一个新的数据输出流，将数据写入指定基础输出流
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        // 发送文件数据
        if (FileName != null) {
            // 构建发送字符串数据
            StringBuilder sb1 = new StringBuilder();
            sb1.append(PREFFIX);
            sb1.append(BOUNDARY);
            sb1.append(LINEND);
            sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + FileName + "\"" + LINEND);
            sb1.append("Content-Type: application/octet-stream;chartset="
                    + CHARSET + LINEND);
            sb1.append(LINEND);
            // 写入到输出流中
            outStream.write(sb1.toString().getBytes());
            // 将文件读入输入流中
            InputStream is = new FileInputStream(FileName);
            byte[] buffer = new byte[1024];
            int len = 0;
            // 写入输出流
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            // 添加换行标志
            outStream.write(LINEND.getBytes());
        }
        // 请求结束标志
        byte[] end_data = (PREFFIX + BOUNDARY + PREFFIX + LINEND).getBytes();
        outStream.write(end_data);
        // 刷新发送数据
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        Log.d("lyj", res + "");
        InputStream in = null;
        // 上传成功返回200
        if (res == 200) {
            in = conn.getInputStream();
            int ch;
            StringBuilder sb2 = new StringBuilder();
            // 保存数据
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
        }
        // 如果数据不为空，则以字符串方式返回数据，否则返回null
        return in == null ? null : in.toString();
    }


//    /**
//     * HTTP上传单个文件
//     * @param actionUrl  请求服务器的路径
//     * @param params     传递的表单内容
//     * @param files      单个文件信息
//     * @return
//     */
//    public int post(String actionUrl, Map<String,String> params, List<FileInfo> fileInfos){
//        Log.i("post-------------", "postfile");
//        HttpURLConnection urlConn = null;
//        BufferedReader br = null;
//        try {
//            //新建url对象
//            URL url = new URL(actionUrl);
//            //通过HttpURLConnection对象,向网络地址发送请求
//            urlConn = (HttpURLConnection)url.openConnection();
//
//            //设置该连接允许读取
//            urlConn.setDoOutput(true);
//            //设置该连接允许写入
//            urlConn.setDoInput(true);
//            //设置不能适用缓存
//            urlConn.setUseCaches(false);
//            //设置连接超时时间
//            urlConn.setConnectTimeout(3000);   //设置连接超时时间
//            //设置读取时间
//            urlConn.setReadTimeout(4000);   //读取超时
//            //设置连接方法post
//            urlConn.setRequestMethod("POST");
//            //设置维持长连接
//            urlConn.setRequestProperty("connection", "Keep-Alive");
//            //设置文件字符集
//            urlConn.setRequestProperty("Charset", CHARSET);
//            //设置文件类型
//            urlConn.setRequestProperty("Content-Type", MUTIPART_FORMDATA+";boundary="+BOUNDARY);
//            /********************************************************************/
//            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
//            //构建表单数据
//            String entryText = bulidFormText(params);
//
//            dos.write(entryText.getBytes());
//            int index = 1;
//            for (FileInfo fileInfo : fileInfos){
//                StringBuffer sb = new StringBuffer("");
//                sb.append(PREFIX+BOUNDARY+LINEND)
//                        .append("Content-Disposition: form-data;" +
//                                " name=\""+fileInfo.getFileTextName()+(++index)+"\";" +
//                                " filename=\""+fileInfo.getImgFile().getAbsolutePath()+"\""+LINEND)
//                        .append("Content-Type:"+CONTENTTYPE+";" +
//                                "charset="+CHARSET+LINEND)
//                        .append(LINEND);
//
//                dos.write(sb.toString().getBytes());
//
//                FileInputStream fis = new FileInputStream(fileInfo.getImgFile());
//
//                byte[] buffer = new byte[10000];
//                int len = 0;
//                while ((len = fis.read(buffer)) != -1) {
//                    dos.write(buffer, 0, len);
//                }
//                dos.write(LINEND.getBytes());
//                fis.close();
//            }
//            //请求的结束标志
//            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
//            dos.write(end_data);
//            dos.flush();
//            dos.close();
//            // 发送请求数据结束
//
//            //接收返回信息
//            int code = urlConn.getResponseCode();
//            if(code!=200){
//                urlConn.disconnect();
//                return code;
//            }else{
//                br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//                String result = "";
//                String line = null;
//                while((line = br.readLine())!=null){
//                    result += line;
//                }
//                Log.i("post-------------", result);
//                if("true".equals(result)){
//                    return 200;
//                }else{
//                    return 500;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("--------上传图片错误--------", e.getMessage());
//            return -1;
//        }finally{
//            try {
//                if(br!=null){
//                    br.close();
//                }
//                if(urlConn!=null){
//                    urlConn.disconnect();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}


