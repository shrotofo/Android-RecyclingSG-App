package com.example.binbolehxfirebase;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;
//to draw the district coordinates on map
public class DistrictPolygon implements DrawableOnMap {
    private List<LatLng> coordinates;
    private String districtName;

    public DistrictPolygon(List<LatLng> coordinates, String districtName) {
        this.coordinates = coordinates;
        this.districtName = districtName;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public String getDistrictName() {
        return districtName;
    }
    @Override
    public void drawOnMap(GoogleMap map, Context context) {
        addDistrictPolygon(map, coordinates, districtName);
    }
    public static void addDistrictPolygon(GoogleMap map, List<LatLng> coordinates, String districtName) {
        // Checking if the map and other parameters are valid
        if (map == null || coordinates == null || coordinates.isEmpty() || districtName == null) {
            return; // Exit method if parameters are not valid
        }

        // Creating PolygonOptions with specified coordinates and styling
        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(coordinates) // Adding coordinates to the polygon
                .strokeColor(Color.RED) // / Setting the boundary line colour
                .fillColor(0x7F00FF00) // Setting the fill colour with transparency
                .clickable(true); //Making the polygon clickable

        // Adding the polygon to the map and tagging it with the district name
        map.addPolygon(polygonOptions).setTag(districtName);
    }

}
