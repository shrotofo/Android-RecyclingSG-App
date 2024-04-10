package com.example.binbolehxfirebase;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class MarkerClickHandler {
    public static void setupMarkerClickBehavior(GoogleMap map, final Context context) {
        map.setOnMarkerClickListener(marker -> {
            BinMarker binMarker = (BinMarker) marker.getTag();
            PopupDisplay.showPopup(context, binMarker);
            return true; // Indicate we've handled the click even
        });
    }
}
