package com.example.liuyongjie.infocollectionapps.utils;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.liuyongjie.infocollectionapps.utils.StringUtil.isSpace;

/**
 * Created by liuyongjie on 2017/3/27.
 * 文件相关操作
 */

public class FileUtil {

    private static ILogger log = LoggerFactory.getLogger("FileUtil");

//    /**
//     * 将一个字符串写入都文件
//     *
//     * @param path     文件的存放路径
//     * @param content  字符串
//     * @param isAppend 是否在文件末尾追加
//     * @return
//     */
//    public static boolean writeFileFromString(String path, String content, boolean isAppend) {
//        File file = new File(path);
//        if (!createOrExistsDir(file)) {
//            return false;
//        }
//        if(!createOrExistsFile(file)){
//            return false;
//        }
//
//
//        return false;
//    }

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件存放路径
     * @param content  写入内容
     * @param append   是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        File file = new File(filePath);
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            log.error(Author.liuyongjie, e);
        } finally {
            CloseUtil.closeIO(bw);
        }
        return false;
    }

    /**
     * 判断目录是否存在,不存在则创建
     *
     * @param file File对象
     * @return false表示创建目录失败
     */
    public static boolean createOrExistsDir(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            return true;
        }
        return file.mkdirs();


    }
//
//    /**
//     * 判断文件是否存在,不存在则创建
//     *
//     * @return
//     */
//    public static boolean createOrExistsFile(File file) {
//        if(file == null){
//            return false;
//        }
//        if(file.exists()){
//           return true;
//        }
//
//
//    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            log.error(Author.liuyongjie, e);
        }
        return false;
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String readFile2String(File file, String charsetName) {
        if (file == null) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);// windows系统换行为\r\n，Linux为\n
            }
            return sb.toString();
//            // 要去除最后的换行符
//            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            log.error(Author.liuyongjie, e);
        } finally {
            CloseUtil.closeIO(reader);
        }
        return null;
    }



}
