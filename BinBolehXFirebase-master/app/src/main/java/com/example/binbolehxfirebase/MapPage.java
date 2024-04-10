package com.example.binbolehxfirebase;

import static com.example.binbolehxfirebase.MapCoordinate.addCustomMarker;
import static com.example.binbolehxfirebase.MapCoordinate.handleMarkerClick;

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

//interface for working with google maps api
public class MapPage extends Fragment implements OnMapReadyCallback {
    private View popupView;
    // Field declaration for storing a reference to the popup view
    // Encapsulation: private field to encapsulate the view, ensuring access control.

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment from the XML layout file
        View rootView = inflater.inflate(R.layout.mappage, container, false);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Note: Use getChildFragmentManager() inside Fragments bc map is nested inside current mappage so we cast it
        // we cast to gain access to all funcs of SuportMapFragment and match to xml
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this); //exceute onMapReady
        }

        // Return the View for the fragment's UI
        return rootView;
    }

    // Overridden method from OnMapReadyCallback interface
    // Callback method invoked when the map is ready for use, setting up map functionality and markers.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Assign the GoogleMap object to the mMap field
        GoogleMap mMap = googleMap;
        // zooming the specified coordinates
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

        LatLng Tamp1Location = new LatLng(1.349539, 103.947958); // Example coordinate marker
        addCustomMarker(googleMap, Tamp1Location, "Tamp Bin 1", getContext());

        LatLng Tamp2Location = new LatLng(1.362551, 103.938913); // Example coordinate marker
        addCustomMarker(googleMap, Tamp2Location, "Tamp Bin 2", getContext());

        // Add a district polygon to the map
        MapCoordinate.addDistrictPolygon(mMap,  Tampines, "Tampines");






        // Example coordinates for a district
        List<LatLng> Jurong = Arrays.asList(
                new LatLng(1.282625, 103.673667),
                new LatLng(1.290816, 103.694507),
                new LatLng(1.274611, 103.685245),
                new LatLng(1.289748, 103.723720),
                new LatLng(1.254310, 103.706085),
                new LatLng(1.226465, 103.677493),
                new LatLng(1.254696, 103.676356)
                // Add more coordinates as needed
        );

        LatLng Jurong1 = new LatLng(1.269881, 103.695953); // Example coordinates
        addCustomMarker(googleMap, Jurong1, "Jurong Bin 1", getContext());
        LatLng Jurong2 = new LatLng(1.264430, 103.669861); // Example coordinates
        addCustomMarker(googleMap, Jurong2, "Jurong Bin 2", getContext());

        // Set a listener for marker click events.

        // Handles marker click events by displaying a popup window
        mMap.setOnMarkerClickListener(marker -> {


            // Handle the marker click event
            handleMarkerClick(marker, getContext());

            return true;
        });




    }

}