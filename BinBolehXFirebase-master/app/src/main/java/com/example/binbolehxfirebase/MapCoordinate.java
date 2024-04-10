package com.example.binbolehxfirebase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;


public class MapCoordinate {

    PopupWindow popupWindow; // Field declaration for a PopupWindow object

    // Method to add a district polygon to the map dynamically
    // Adds a polygon to the map representing a district boundary.
//map refers to the google map obj to which the polygon will be added, list of LatLng coordinates defines the vertices of the polygon

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


    // Converts the svg into a BitmapDescriptor for use as a marker icon.

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        // Retrieving the vector drawable from resources
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // Creating a Bitmap from the svg
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        // Returning a BitmapDescriptor created from the Bitmap
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    // Method to add a custom marker to the map
    // Adds a custom marker to the map at a specified location.
    public static void addCustomMarker(GoogleMap map, LatLng location, String title, Context context) {
        if (map == null || location == null || title == null) {
            return;
        }

        // Creating a BitmapDescriptor for the custom marker icon
        BitmapDescriptor icon = bitmapDescriptorFromVector(context, R.drawable.trace);

        // Creating options for drawing a marker on the map with specified propertie
        MarkerOptions markerOptions = new MarkerOptions()
                .position(location) // Set the position of the marker
                .title(title) // Set the title for the marker's info window
                .icon(icon)
                .zIndex(1); // Set z-index higher than the polygon's z-index

        // Adding the marker to the map
        map.addMarker(markerOptions);
    }

    // Setting a listener to handle marker click events
    // Displays a popup window when a marker is clicked.

    public static void handleMarkerClick(Marker marker, Context context) {
        // Inflating the custom popup layout using LayoutInflater
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup1, null);
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Showing the popup window at the center of the screen
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // Setting a click listener for the button in popup1
        popupView.findViewById(R.id.button_image).setOnClickListener(view -> {
            // Dismissing popup1
            popupWindow.dismiss();

            // Inflating popup2
            View popupView2 = LayoutInflater.from(context).inflate(R.layout.popup2, null);
            PopupWindow popupWindow2 = new PopupWindow(popupView2, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            // Set a click listener for the close button in popup2
            popupView2.findViewById(R.id.close_button).setOnClickListener(view1 -> {
                // Logic to navigate back to the map page
                popupWindow2.dismiss();
            });

            // Show popup2
            popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
        });
    }
}