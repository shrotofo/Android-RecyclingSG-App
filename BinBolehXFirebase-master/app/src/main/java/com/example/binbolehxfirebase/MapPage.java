package com.example.binbolehxfirebase;

import static com.example.binbolehxfirebase.MapCoordinate.addCustomMarker;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;


public class MapPage extends Fragment implements OnMapReadyCallback {
    private View popupView;

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

        mMap = googleMap;

        // Example coordinates for a district
        List<LatLng> Tampines = Arrays.asList(
                new LatLng(1.37508, 103.93144),
                new LatLng(1.348348, 103.924595),
                new LatLng(1.331864, 103.951320),
                new LatLng(1.315515, 103.968434),
                new LatLng(1.339253, 103.974411),
                new LatLng(1.361349, 103.960196)
                // Add more coordinates as needed
        );

        // Add a district polygon to the map
        MapCoordinate.addDistrictPolygon(mMap, getContext(), Tampines, "Tampines");

        LatLng someLocation = new LatLng(1.448348, 103.934595); // Example coordinates
        addCustomMarker(googleMap, someLocation, "Tamp Bin 1", getContext());


    }
}