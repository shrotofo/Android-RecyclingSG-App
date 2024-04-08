package com.example.binbolehxfirebase;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapPage extends Fragment implements OnMapReadyCallback {
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // Inflate the layout for this fragment from the XML layout file
    View rootView = inflater.inflate(R.layout.mappage, container, false);

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    // Note: Use getChildFragmentManager() instead of getSupportFragmentManager() inside Fragments
    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
            .findFragmentById(R.id.map);
    if (mapFragment != null) {
        mapFragment.getMapAsync(this);
    }

    // Return the View for the fragment's UI
    return rootView;
}

// Overridden method from OnMapReadyCallback interface
@Override
public void onMapReady(GoogleMap googleMap) {
    // Assign the GoogleMap object to the mMap field
    GoogleMap mMap = googleMap;

    // Move the camera to the specified coordinates (Latitude and Longitude) with a zoom level
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 5));
}
}
