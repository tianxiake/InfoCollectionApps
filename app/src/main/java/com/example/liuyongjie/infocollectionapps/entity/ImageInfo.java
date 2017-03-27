package com.example.liuyongjie.infocollectionapps.entity;

/**
 * Created by liuyongjie on 2017/3/27.
 * 图片信息实体类
 */

public class ImageInfo {
    //照片的拍摄时间
    private String dataTime;
    //照片拍摄的纬度
    private String latitude;
    //照片拍摄的经度
    private String longitude;
    //照片的长度
    private int height;
    //照片的宽度
    private int width;
    //设备型号
    private String model;
    //感光元件的感光速度
    private String iso;
    //光圈值
    private String aperture;
    //焦距
    private String focalLength;

    public ImageInfo(String dataTime, String latitude, String longitude, int height, int width, String model, String iso, String aperture, String focalLength) {
        this.dataTime = dataTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
        this.width = width;
        this.model = model;
        this.iso = iso;
        this.aperture = aperture;
        this.focalLength = focalLength;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDataTime() {

        return dataTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getModel() {
        return model;
    }

    public String getIso() {
        return iso;
    }

    public String getAperture() {
        return aperture;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "dataTime='" + dataTime + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", model='" + model + '\'' +
                ", iso='" + iso + '\'' +
                ", aperture='" + aperture + '\'' +
                ", focalLength='" + focalLength + '\'' +
                '}';
    }
}
