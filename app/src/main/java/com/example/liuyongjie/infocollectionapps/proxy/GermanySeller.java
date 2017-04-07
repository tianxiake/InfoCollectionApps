package com.example.liuyongjie.infocollectionapps.proxy;

/**
 * Created by liuyongjie on 2017/4/7.
 */

public class GermanySeller implements AudiCar {
    @Override
    public void sellCar() {
        System.out.println("德国 sell a car");
    }
}
