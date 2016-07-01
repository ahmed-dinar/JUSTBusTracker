package com.ahmeddinar.justbustracker.rest.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public class BusLocation {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private String longitude;

}