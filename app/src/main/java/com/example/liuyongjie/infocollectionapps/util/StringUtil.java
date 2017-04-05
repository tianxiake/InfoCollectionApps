package com.example.liuyongjie.infocollectionapps.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyongjie on 2017/3/23.
 */

public class StringUtil {

    /**
     * mac地址字节数组转变为ASCII码字符
     *
     * @param macBytes mac字节数组
     * @return
     */
    public String macBytes2MacStr(byte[] macBytes) {
        if (macBytes == null) {
            return null;
        }
        try {
            String string = new String(macBytes, "ascii");
            return string;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a.equals(b)) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 字节数组转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 16进制字符串转换为字节数组
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 字符转换为字节
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     */
    public static void printByteArray2HexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }
    }

    /**
     * 字符串转换为byte数组
     *
     * @param content
     * @return
     */
    public static byte[] stringToByteArray(String content) {
        if (content == null) {
            return null;
        }
        byte[] bytes = content.getBytes();
        return bytes;
    }

    /**
     * 字节数组转换为字符串
     *
     * @param byteArray   字节数组
     * @param charsetName 指定编码的方式
     */
    public static String byteArratToString(byte[] byteArray, String charsetName) {
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

    /**
     * byte[]数组转换为有符号型二进制字符串，就是每个字节用8位二进制表示
     *
     * @param bytes
     * @return
     */
    public static String bytes2SignedBinaryStr(byte[] bytes) {
        StringBuilder stringBuild = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String binaryString;
            if (bytes[i] >= 0) {
                binaryString = "0" + Integer.toBinaryString(bytes[i]);
            } else {
                binaryString = "1" + Integer.toBinaryString(bytes[i]);
            }
            stringBuild.append(binaryString);
        }
        return stringBuild.toString();
    }

    /**
     * byte转换为无符号型二进制字符串，每个byte用7位表示
     */
    public static String bytes2UnsignedBinaryStr(byte b) {
        return Integer.toBinaryString(b);
    }

    /**
     * int型ip转换为字符串ip
     *
     * @param ipInt
     * @return
     */
    public static byte[] intToBytes(int ipInt) {
        byte[] ipAddress = new byte[4];
        ipAddress[3] = (byte) ((ipInt >>> 24) & 0xFF);
        ipAddress[2] = (byte) ((ipInt >>> 16) & 0xFF);
        ipAddress[1] = (byte) ((ipInt >>> 8) & 0xFF);
        ipAddress[0] = (byte) (ipInt & 0xFF);
        return ipAddress;
    }

    /**
     * 得到字符串中某个字符的个数
     *
     * @param str 字符串
     * @param c   字符
     * @return
     */
    public static final int getCharnum(String str, char c) {
        int num = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                num++;
            }
        }

        return num;
    }


    /**
     * 返回yyyymm
     *
     * @param aDate
     * @return
     */
    public static final String getYear_Month(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat("yyyyMM");
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * hxw 返回当前年
     *
     * @return
     */
    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * hxw 返回当前月
     *
     * @return
     */
    public static String getMonth() {
        Calendar calendar = Calendar.getInstance();
        String temp = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (temp.length() < 2)
            temp = "0" + temp;
        return temp;
    }


    /**
     * 按长度分割字符串
     *
     * @param content
     * @param len
     * @return
     */
    public static String[] split(String content, int len) {
        if (content == null || content.equals("")) {
            return null;
        }
        int len2 = content.length();
        if (len2 <= len) {
            return new String[]{content};
        } else {
            int i = len2 / len + 1;
            System.out.println("i:" + i);
            String[] strA = new String[i];
            int j = 0;
            int begin = 0;
            int end = 0;
            while (j < i) {
                begin = j * len;
                end = (j + 1) * len;
                if (end > len2)
                    end = len2;
                strA[j] = content.substring(begin, end);
                // System.out.println(strA[j]+"<br/>");
                j = j + 1;
            }
            return strA;
        }
    }

    public static boolean emailFormat(String email) {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }


    /**
     * 验证是不是EMAIL
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean retval = false;
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        retval = matcher.matches();
        return retval;
    }

    /**
     * 验证汉字为true
     *
     * @param s
     * @return
     */
    public static boolean isLetterorDigit(String s) {
        if (s.equals("") || s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                // if (!Character.isLetter(s.charAt(i))){
                return false;
            }
        }
        // Character.isJavaLetter()
        return true;
    }

    /**
     * 判断一个字符串是否都为数字
     *
     * @param strNum
     * @return
     */
    public static boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

    /**
     * 判断一个字符串是否都为数字
     *
     * @param strNum
     * @return
     */
    public static boolean isDigit2(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(strNum);
        return matcher.matches();
    }

    /**
     * 日期转String
     *
     * @param pattern
     * @return
     */
    public static String now(String pattern) {
        return dateToString(new Date(), pattern);
    }

    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
    }

    /**
     * 字符串转化为int值
     *
     * @return 返回-1表示字符串转换失败，字符串不符合条件
     */
    public static int StringToInt(String content) {
        try {
            return Integer.parseInt(content);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
