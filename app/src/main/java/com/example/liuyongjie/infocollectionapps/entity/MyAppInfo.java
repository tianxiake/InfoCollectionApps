package com.example.liuyongjie.infocollectionapps.entity;

/**
 * Created by liuyongjie on 2017/3/24.
 * 应用安装列表的实体类
 */

public class MyAppInfo {
    private String packageName;

    public MyAppInfo(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "MyAppInfo{" +
                "packageName='" + packageName + '\'' +
                '}';
    }
}
