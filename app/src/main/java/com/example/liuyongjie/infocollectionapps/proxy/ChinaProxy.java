package com.example.liuyongjie.infocollectionapps.proxy;

/**
 * Created by liuyongjie on 2017/4/7.
 */

public class ChinaProxy implements AudiCar {

    private AudiCar mAudiCar;

    public ChinaProxy() {
        mAudiCar = new GermanySeller();
    }

    @Override
    public void sellCar() {
        System.out.println("--中国代理--");
        mAudiCar.sellCar();
    }
}
