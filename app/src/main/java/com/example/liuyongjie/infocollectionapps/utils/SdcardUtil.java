package com.example.liuyongjie.infocollectionapps.utils;

import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

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
     *
     * @return
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
     * @param dirFile    目录文件对象
     * @param imageInfos list集合
     */
    private void getImageInfo(File dirFile, List<ImageInfo> imageInfos) {
        try {
            if (dirFile == null) {
                return;
            }
            File[] listFiles = dirFile.listFiles();
            if (listFiles.length == 0) {
                return;
            }
            //判断是目录还是文件
            for (File childFile : listFiles) {
                //是目录
                if (childFile.isDirectory()) {
                    getImageInfo(childFile, imageInfos);
                } else if (childFile.isFile()) {//是文件就处理
                    String childName = childFile.getAbsolutePath();
                    String fileExtension = childName.substring(childName.length() - 4).toUpperCase();
                    log.verbose(Author.liuyongjie, Business.dev_test, "文件名={}文件扩展名={}", childName, fileExtension);
                    if (fileExtension.equals(".JPG")) {
                        //读取jpg图片的内容
                        ExifInterface exifInterface = new ExifInterface(childName);
                        String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                        String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                        if (latitude == null && longitude == null) {
                            continue;
                        }
                        String dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                        String iso = exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS);
                        String aperture = exifInterface.getAttribute(ExifInterface.TAG_APERTURE_VALUE);
                        String focalLength = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
                        Log.d("位置", "latitude=" + latitude + "longitude" + longitude);
                        int height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0);
                        int width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0);
                        String model = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
                        ImageInfo info = new ImageInfo(dateTime, latitude, longitude, height, width, model, iso, aperture, focalLength);
                        imageInfos.add(info);
                    } else if (fileExtension.equals(".PNG")) {
                        //读取png图片的内容
                    } else {
                        //其他格式的图片信息
                    }

                }
            }
        } catch (Exception e) {
            log.error(Author.liuyongjie, e);
        }
    }

    //控制遍历的深度
    private static int searchLength = 0;
//private JSONArray jsonArray ;

    /**
     * @param parentFile
     */
    public void getAllFiles(JSONArray parentJsonArray, File parentFile, FileFilter fileFilter) {
        if (parentFile == null && parentFile.isFile()) {
            return;
        }
        File[] listFiles = parentFile.listFiles(fileFilter);
        if (listFiles == null || listFiles.length == 0) {
            return;
        } else {
            JSONArray childJsonArray = new JSONArray();
            createJsonArray(childJsonArray, parentFile);
            parentJsonArray.put(childJsonArray);
//            searchLength++;
//            if (searchLength > Integer.MAX_VALUE) {
//                return;
//            }
//            JSONArray array = new JSONArray();
            //遍历文件是目录就创建一个JsonArray
            for (File childFile : listFiles) {
                Log.d("TAG", listFiles.length + "");
                //是目录
                if (childFile.isDirectory()) {
//                    createJsonObject(jsonArray,child);
                    Log.d("TAG", childFile.getName());
                    getAllFiles(childJsonArray, childFile, fileFilter);

                } else {
                    //是文件添加进去
                    String fileName = childFile.getName();
                    if (fileName.contains(".jpg") || fileName.contains(".png")) {
                        return;
                    }
                    createJsonArray(parentJsonArray, childFile);
                }
            }
        }


    }

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

    public void createJsonArray(JSONArray jsonArray, File file) {
        if (file == null) {
            return;
        } else {
            try {
                JSONArray childArray = new JSONArray();
                childArray.put(file.getName());
                childArray.put(file.lastModified() / 1000);
                childArray.put(file.length());
                jsonArray.put(childArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
