package com.example.binbolehxfirebase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polygon;

import android.content.Context;
import android.graphics.Color;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


public class MapCoordinate {

    PopupWindow popupWindow;

    // Method to add a district polygon to the map dynamically
    public static void addDistrictPolygon(GoogleMap map, Context context, List<LatLng> coordinates, String districtName) {
        if (map == null || coordinates == null || coordinates.isEmpty() || districtName == null) {
            return; // Guard clause to ensure we have valid parameters
        }


        PolygonOptions polygonOptions = new PolygonOptions()
                .addAll(coordinates)
                .strokeColor(Color.RED) // Boundary line color
                .fillColor(0x7F00FF00)
                .clickable(true);// Fill color with transparency

        map.addPolygon(polygonOptions).setTag(districtName);


        // Setting a click listener for the polygon
        map.setOnPolygonClickListener(polygon -> {
            // Showing a Toast message for simplicity
            Toast.makeText(context, "Clicked: " + polygon.getTag(), Toast.LENGTH_SHORT).show();

        });
    }

        // Method to add a custom marker to the map
        public static void addCustomMarker(GoogleMap map, LatLng location, String title, Context context) {
            if (map == null || location == null || title == null) {
                return; // Guard clause to ensure valid parameters
            }

            // Create a MarkerOptions object
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location) // Set the position of the marker
                    .title(title) // Set the title for the marker's info window
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.logo1)); // Set the custom icon

            // Add the marker to the map
            map.addMarker(markerOptions);
        }



}
