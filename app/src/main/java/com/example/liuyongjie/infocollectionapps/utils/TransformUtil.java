package com.example.liuyongjie.infocollectionapps.utils;

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
}
