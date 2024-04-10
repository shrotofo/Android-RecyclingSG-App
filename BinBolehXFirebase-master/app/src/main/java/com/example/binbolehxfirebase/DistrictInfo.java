package com.example.binbolehxfirebase;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class DistrictInfo {
    String name;
    List<LatLng> coordinates;

    // Constructor
    public DistrictInfo(String name, List<LatLng> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    // You might add setters or other properties as needed.
}

