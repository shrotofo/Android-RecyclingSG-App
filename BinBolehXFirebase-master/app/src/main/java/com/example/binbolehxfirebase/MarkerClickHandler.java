package com.example.binbolehxfirebase;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class MarkerClickHandler {
    public static void setupMarkerClickBehavior(GoogleMap map, final Context context) {
        map.setOnMarkerClickListener(marker -> {
            BinMarker binMarker = (BinMarker) marker.getTag();
            // Depending on the marker or its tag, decide which popup to show.
            // This is where delegation comes in - delegate to a specific method/class for showing the popup.
            PopupDisplay.showPopup(context, binMarker);
            return true; // Indicate we've handled the click event
        });
    }
}
