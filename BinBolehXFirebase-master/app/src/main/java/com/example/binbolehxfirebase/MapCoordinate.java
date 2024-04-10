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