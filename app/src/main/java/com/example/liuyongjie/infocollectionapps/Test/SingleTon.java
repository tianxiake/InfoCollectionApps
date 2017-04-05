package com.example.liuyongjie.infocollectionapps.Test;

/**
 * Created by liuyongjie on 2017/4/5.
 * 线程安全的单例模式
 */

public class SingleTon {

    private static SingleTon singleTon;

    private SingleTon() {
    }

    public SingleTon getSingleTonInstance() {
        if (singleTon == null) {
            synchronized (this) {
                if (singleTon == null) {
                    singleTon = new SingleTon();
                }
            }
        }
        return singleTon;
    }

}
