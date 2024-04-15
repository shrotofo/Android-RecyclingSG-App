package com.example.binbolehxfirebase;

import com.google.android.gms.maps.model.LatLng;

public class BinMarker {
    private LatLng location;
    private String title;

    private String idbin;

    public BinMarker(LatLng location, String title,String idbin ) {
        this.location = location;
        this.title = title;
        this.idbin=idbin;
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

    public void setId1(double id1){this.idbin=idbin; }

    public String getIdbin(){ return idbin;}
}
