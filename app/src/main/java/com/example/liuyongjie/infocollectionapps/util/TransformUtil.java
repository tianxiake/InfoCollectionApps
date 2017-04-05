package com.example.liuyongjie.infocollectionapps.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuyongjie on 2017/3/24.
 * 转换相关的工具类
 */

public class TransformUtil {

    // ip的int值转变为字符串ip
    public static String intToIp(int i) {
        return ((i & 0xFF) + "."
                + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF));
    }

    //字符串转换为byte数组
    public static byte[] stringToByteArray(String content) {
        if (content == null) {
            return null;
        }
        return content.getBytes();
    }

    //byte数组转换为字符串
    public static String byteArrayToString(byte[] byteArray, String charsetName) {
        if (byteArray == null) {
            return null;
        }

        if (charsetName == null) {
            charsetName = "utf-8";
        }
        try {
            String content = new String(byteArray, charsetName);
            return content;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }




}
