package com.example.binbolehxfirebase;

import com.google.android.gms.maps.model.LatLng;

public class BinMarker {
    private LatLng location;
    private String title;

    private double recyclingLevel;



    public BinMarker(LatLng location, String title) {
        this.location = location;
        this.title = title;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
