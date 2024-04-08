package com.example.binbolehxfirebase;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polygon;

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
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

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

    public static BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static void addCustomMarker(GoogleMap map, LatLng location, String title, Context context) {
            if (map == null || location == null || title == null) {
                return; // Guard clause to ensure valid parameters
            }
        BitmapDescriptor icon = bitmapDescriptorFromVector(context, R.drawable.trace);

        // Create a MarkerOptions object
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location) // Set the position of the marker
                    .title(title)// Set the title for the marker's info window
                     .icon(icon);

            // Add the marker to the map
            map.addMarker(markerOptions);

            // Set a listener for marker click events.
            map.setOnMarkerClickListener(marker -> {
                // Check if the clicked marker is the one we're interested in
                if (marker.getPosition().equals(location)) {
                    // Inflate the custom popup layout using LayoutInflater
                    View popupView = LayoutInflater.from(context).inflate(R.layout.popup1, null);
                    PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    // Find the button in popup1 and set a click listener
                    popupView.findViewById(R.id.button_image).setOnClickListener(view -> {
                        // Dismiss popup1
                        popupWindow.dismiss();

                        // Inflate popup2
                        View popupView2 = LayoutInflater.from(context).inflate(R.layout.popup2, null);
                        PopupWindow popupWindow2 = new PopupWindow(popupView2, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        popupView2.findViewById(R.id.close_button).setOnClickListener(view1 -> {
                            // Logic to navigate back to the map page
                            popupWindow2.dismiss();

                        });
                        // Show popup2
                        popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);


                    });

                    popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);



                }
                return true;
            });
        }





}
