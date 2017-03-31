package com.example.liuyongjie.infocollectionapps;

import org.junit.Test;

import java.util.Arrays;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void arrayTest() {
        int[] a = new int[1024];
        System.out.println(a.length);
        System.out.println(Arrays.toString(a));
    }
}