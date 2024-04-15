package com.example.binbolehxfirebase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

public class MarkerClickHandler {
    public static void setupMarkerClickBehavior(GoogleMap map, final Context context) {
        map.setOnMarkerClickListener(marker -> {
            BinMarker binMarker = (BinMarker) marker.getTag();
            if (binMarker != null) {
                PopupDisplay.showPopup(context, binMarker);
                ImageView live_bin = ((Activity) context).findViewById(R.id.live_bin);

                //TextView text = ((Activity) context).findViewById(R.id.text);
                //PopupDisplay.setLiveBin(live_bin);
                //PopupDisplay.setText(text);
                Log.d(binMarker.getIdbin(), ":BinMarker tag found on marker.");
            } else {
                // Optionally handle the case where the tag is null or not a BinMarker
                Log.d("MarkerClickHandler", "No BinMarker tag found on marker.");
            }
            return true; // Indicate we've handled the click event
        });
    }
}
