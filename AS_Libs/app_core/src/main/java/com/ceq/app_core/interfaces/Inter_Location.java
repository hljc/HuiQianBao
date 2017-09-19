package com.ceq.app_core.interfaces;

/**
 * Created by Administrator on 2017/1/14.
 */

public interface Inter_Location {
    String getCity();

    String getCountry();

    String getLocationDescribe();

    String getAddrStr();

    double getLongitude();

    double getAltitude();

    double getLatitude();
    float getRadius();
}