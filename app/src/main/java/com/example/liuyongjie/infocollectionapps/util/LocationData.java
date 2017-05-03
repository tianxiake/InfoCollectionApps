package com.example.liuyongjie.infocollectionapps.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;


/**
 * Created by liuyongjie on 2017/5/3.
 */

public class LocationData implements LocationListener {
    private final static ILogger log = LoggerFactory.getLogger("LocationData");

    public LocationData(Context context) {
        start(context);
    }

    public void start(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0.1f, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        log.verbose(Author.lyj, Business.dev_test, "onLocationChanged");
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            log.verbose(Author.lyj, Business.dev_test, "latitude={},longitude={}", latitude, longitude);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        log.verbose(Author.lyj, Business.dev_test, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        log.verbose(Author.lyj, Business.dev_test, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        log.verbose(Author.lyj, Business.dev_test, "onProviderDisabled");
    }
}
