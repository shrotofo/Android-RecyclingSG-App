package com.example.binbolehxfirebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
//mark bins on map
public class CustomMarker implements DrawableOnMap {
    private List<BinMarker> binMarkerList;

    public CustomMarker(List<BinMarker> binMarkerList) {
        this.binMarkerList = binMarkerList;
    }

    @Override
    public void drawOnMap(GoogleMap map, Context context) {
        addCustomMarker(map, binMarkerList, context);
    }

    public static void addCustomMarker(GoogleMap map, List<BinMarker> markers, Context context) {

        for (BinMarker marker : markers) {
            LatLng location = marker.getLocation();
            String title = marker.getTitle();

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
    }

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
}
