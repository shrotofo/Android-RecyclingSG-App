package com.example.binbolehxfirebase;




import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//interface for working with google maps api
public class MapPage extends Fragment implements OnMapReadyCallback {

    private DatabaseReference binsRef;

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
        setupMapBounds(googleMap);
        // Draw Districts
        List<DistrictPolygon> districts = getDistricts();
        for (DistrictPolygon district : districts) {
            district.drawOnMap(googleMap, getContext());
        }

        // Draw Markers
        List<BinMarker> markersList = getMarkers();
        // CustomMarker can now handle a list of BinMarker and draw them all.
        new CustomMarker(markersList).drawOnMap(googleMap, getContext());

        // Setup Marker Click Behavior
        // MarkerClickHandler is capable of handling clicks for all markers.
        MarkerClickHandler.setupMarkerClickBehavior(googleMap, getContext());


    }

    private void setupMapBounds(GoogleMap map) {
        LatLngBounds SINGAPORE_BOUNDS = new LatLngBounds(
                new LatLng(1.1304753, 103.6920359), // Southwest corner
                new LatLng(1.4504753, 104.0120359)  // Northeast corner
        );
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(SINGAPORE_BOUNDS, 0));
        map.setLatLngBoundsForCameraTarget(SINGAPORE_BOUNDS);
    }

    private List<DistrictPolygon> getDistricts() {
        List<DistrictPolygon> districts = new ArrayList<>();

        districts.add(new DistrictPolygon(Arrays.asList(
                new LatLng(1.37508, 103.93144),
                new LatLng(1.348348, 103.924595),
                new LatLng(1.331864, 103.951320),
                new LatLng(1.315515, 103.968434),
                new LatLng(1.339253, 103.974411),
                new LatLng(1.361349, 103.960196)

        ),"Tampines"));

        districts.add(new DistrictPolygon( Arrays.asList(
                new LatLng(1.282625, 103.673667),
                new LatLng(1.290816, 103.694507),
                new LatLng(1.274611, 103.685245),
                new LatLng(1.289748, 103.723720),
                new LatLng(1.254310, 103.706085),
                new LatLng(1.226465, 103.677493),
                new LatLng(1.254696, 103.676356)

        ),"Jurong"));

        return districts;
    }



    private List<BinMarker> getMarkers() {
        // Return a list of BinMarkers for Tampines or any specific area
        return Arrays.asList(
                new BinMarker(new LatLng(1.349539, 103.947958), "Tamp Bin 1","0001"),
                new BinMarker(new LatLng(1.362551, 103.938913), "Tamp Bin 2","0002"),
                new BinMarker(new LatLng(1.269881, 103.695953), "Jurong Bin 1","0003"),
                new BinMarker(new LatLng(1.264430, 103.669861), "Jurong Bin 2","0004")
                // Add more markers as needed
        );
    }









}