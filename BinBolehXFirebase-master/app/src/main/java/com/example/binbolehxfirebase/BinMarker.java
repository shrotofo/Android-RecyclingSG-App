package com.example.binbolehxfirebase;

import com.google.android.gms.maps.model.LatLng;

public class BinMarker {
    private LatLng location;
    private String title;

    private double id;

    public BinMarker(LatLng location, String title,double id ) {
        this.location = location;
        this.title = title;
        this.id=id;
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

    public void setId(double id){this.id=id ; }

    public double getId(){ return id;}
}
