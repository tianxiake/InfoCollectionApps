package com.example.liuyongjie.infocollectionapps;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void arrayTest() {
    }

    @Test
    public void codeTest() {
        String content = "中国";
        byte[] bytes = content.getBytes();
        System.out.println(bytes.length);

        String str = "\n";
        byte[] bytes1 = str.getBytes();
        System.out.println(bytes1.length);

        try {
            byte[] bytes2 = new byte[]{-127, -127, -127};
            String string = new String(bytes2, "utf-8");
            byte[] bytes3 = string.getBytes();
            System.out.println(Arrays.toString(bytes3));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            String contents = "helfaf ff  fsf \n";
            byte[] b = contents.getBytes("utf-8");
            System.out.println(Arrays.toString(b));
            String string = new String(b);
            byte[] des = string.getBytes("utf-8");
            System.out.println(Arrays.toString(des));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}