package com.example.liuyongjie.infocollectionapps.util;

import android.media.ExifInterface;
import android.os.Environment;

import com.example.liuyongjie.infocollectionapps.entity.ImageInfo;
import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyongjie on 2017/3/27.
 */

public class SdcardUtil {

    private static final String IMAGE_PATH = "/sdcard/DCIM";
    private static ILogger log = LoggerFactory.getLogger("SdcardUtil");

    /**
     * 获取图片的信息
     */
    public List<ImageInfo> getImageInfo() {
        try {
            //判断sdcard是否挂载
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                return null;
            }
            //判断DCIM目录是否存在
            File dcimFile = new File(IMAGE_PATH);
            if (!dcimFile.exists()) {
                return null;
            }
            List<ImageInfo> infos = new ArrayList<>();
            getImageInfo(dcimFile, infos);
            return infos;
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
        return null;
    }

    /**
     * @param dirFile  目录文件对象
     * @param listInfo list集合
     */
    private void getImageInfo(File dirFile, List<ImageInfo> listInfo) {
        try {
            if (dirFile == null) {
                return;
            }
            File[] listFiles = dirFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                return;
            }
            //判断是目录还是文件
            for (File childFile : listFiles) {
                //是目录
                if (childFile.isDirectory()) {
                    getImageInfo(childFile, listInfo);
                } else if (childFile.isFile()) {//是文件就处理
                    String childName = childFile.getAbsolutePath();
                    String fileExtension = childName.substring(childName.length() - 4).toUpperCase();
                    log.verbose(Author.liuyongjie, Business.dev_test, "文件名={}文件扩展名={}", childName, fileExtension);
                    if (fileExtension.equals(".JPG")) {
                        //读取jpg图片的内容
                        ExifInterface exifInterface = new ExifInterface(childName);
                        String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                        String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                        //过滤只有宽度和高度的图片
                        String dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                        String iso = exifInterface.getAttribute(ExifInterface.TAG_ISO);
                        String aperture = exifInterface.getAttribute(ExifInterface.TAG_APERTURE);
                        String focalLength = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
                        if (latitude == null && longitude == null && dateTime == null && iso == null && aperture == null && focalLength == null) {
                            continue;
                        }
                        int height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
                        int width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                        String model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
                        ImageInfo info = new ImageInfo(dateTime, latitude, longitude, height, width, model, iso, aperture, focalLength);
                        listInfo.add(info);
                    }
                }
            }
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
    }

    //控制遍历的深度
    private static int searchDepth = 0;


    /**
     * 扫描指定目录下所有的文件
     *
     * @param parentJsonArray 存放在一个JSONArray中
     * @param parentFile      目录
     * @param fileFilter      文件过滤器
     * @param depth           目录扫描的深度，如depth=2,则只会扫描parentFile目录下两层的内容
     */
    public void getAllFiles(JSONArray parentJsonArray, File parentFile, FileFilter fileFilter, int depth) {
        if (parentFile == null || parentFile.isFile()) {
            return;
        }
        File[] listFiles = parentFile.listFiles(fileFilter);
        if (listFiles == null) {
            return;
        } else {
            createJsonArray(parentJsonArray, parentFile);
            log.debug(Author.liuyongjie, Business.dev_test, null, "目录的名字={},目录下包含的文件的数量={}", parentFile.getName(), listFiles.length);
            //遍历文件是目录就创建一个JsonArray
            searchDepth++;//记录深度
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    if (searchDepth > depth) {
                        createJsonArray(parentJsonArray, listFiles[i]);
                        continue;
                    }
                    //是目录
                    JSONArray childJsonArray = new JSONArray();
                    parentJsonArray.put(childJsonArray);
                    log.verbose(Author.liuyongjie, Business.dev_test, "目录的名字={}", listFiles[i].getName());
                    getAllFiles(childJsonArray, listFiles[i], fileFilter, depth);
                } else {
                    //是文件
                    createJsonArray(parentJsonArray, listFiles[i]);
                }
            }
            searchDepth--;
        }
    }

    /**
     * 扫描指定目录下所有的文件
     *
     * @param parentJsonArray 存放在一个JSONArray中
     * @param parentFile      目录
     * @param fileFilter      文件过滤器
     */
    public void getAllFiles(JSONArray parentJsonArray, File parentFile, FileFilter fileFilter) {
        if (parentFile == null || parentFile.isFile()) {
            return;
        }
        File[] listFiles = parentFile.listFiles(fileFilter);
        if (listFiles == null) {
            return;
        } else {
            createJsonArray(parentJsonArray, parentFile);
            log.debug(Author.liuyongjie, Business.dev_test, null, "目录的名字={},目录下包含的文件的数量={}", parentFile.getName(), listFiles.length);
            //遍历文件是目录就创建一个JsonArray
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    //是目录
                    JSONArray childJsonArray = new JSONArray();
                    parentJsonArray.put(childJsonArray);
                    log.verbose(Author.liuyongjie, Business.dev_test, "目录的名字={}", listFiles[i].getName());
                    getAllFiles(childJsonArray, listFiles[i], fileFilter);
                } else {
                    //是文件
                    createJsonArray(parentJsonArray, listFiles[i]);
                }
            }
        }
    }


//    /**
//     * @param parentFile
//     */
//    public void getAllFiles(File parentFile, FileFilter fileFilter) {
//        File[] listFiles = parentFile.listFiles(fileFilter);
//        if (listFiles != null) {
//            //表示是目录
//            JSONArray jsonArray = new JSONArray();
//            createJsonArray(jsonArray, parentFile);
//            //遍历文件是目录就创建一个JsonArray
//            for (File childFile : listFiles) {
//                Log.d("TAG", listFiles.length + "");
//                //是目录
//                if (childFile.isDirectory()) {
////                    createJsonObject(jsonArray,child);
//                    Log.d("TAG", childFile.getName());
//                    getAllFiles(childFile, fileFilter);
//
//                } else {
//                    createJsonArray(jsonArray, childFile);
//                }
//            }
//        }
//    }

//    //获取sdcard中所有文件的内容
//    public void getAllFiles() {
//
//    }

//    public void getAllFiles(File parentFile, FileFilter fileFilter) {
//
//
//    }

    public void createJsonObject(JSONArray jsonArray, File file) {
        if (file == null) {
            return;
        } else {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", file.getName());
                jsonObject.put("time", file.lastModified());
                jsonObject.put("size", file.length());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void createJsonArray(JSONArray jsonArray, File file) {
        if (file == null) {
            return;
        } else {
            try {
                JSONArray childArray = new JSONArray();
                childArray.put(file.getName());
                childArray.put(file.lastModified() / 1000);
                if (file.isDirectory()) {
                    childArray.put(-1);
                } else {
                    childArray.put(file.length());
                }
                jsonArray.put(childArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
