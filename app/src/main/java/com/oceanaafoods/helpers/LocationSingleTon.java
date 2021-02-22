package com.oceanaafoods.helpers;

import com.google.android.gms.maps.model.LatLng;

public class LocationSingleTon {
    LatLng latLng;
    private static final LocationSingleTon ourInstance = new LocationSingleTon();

    public static LocationSingleTon instance() {
        return ourInstance;
    }

    private LocationSingleTon() {
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
