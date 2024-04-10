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
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//interface for working with google maps api
public class MapPage extends Fragment implements OnMapReadyCallback {

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
        List< DistrictPolygon > districts = createDistricts();
        // Iterate over districts to draw polygons
        for (DistrictPolygon district : districts) {
            new DistrictPolygon(district.getCoordinates(), district.getDistrictName()).drawOnMap(googleMap, getContext());
        }

        // Create a list to hold all BinMarker instances
        List<BinMarker> MarkersList = new ArrayList<>();










        // Adding Tampines BinMarker instances
        drawableObjects.add(new CustomMarker(new BinMarker(new LatLng(1.349539, 103.947958), "Tamp Bin 1")));
        drawableObjects.add(new CustomMarker(new BinMarker(new LatLng(1.362551, 103.938913), "Tamp Bin 2")));


        // Add a district polygon to the map
        drawableObjects.add(new DistrictPolygon(TampinesPolygon, "Tampines"));








        // Adding Jurong BinMarker instances
        drawableObjects.add(new CustomMarker(new BinMarker(new LatLng(1.269881, 103.695953), "Jurong Bin 1")));
        drawableObjects.add(new CustomMarker(new BinMarker(new LatLng(1.264430, 103.669861), "Jurong Bin 2")));

        drawableObjects.add(new DistrictPolygon(Jurong, "Jurong"));
        // Draw each object on the map
        for (DrawableOnMap obj : drawableObjects) {
            obj.drawOnMap(googleMap, getContext());
        }
        // Set a listener for marker click events.

        // Handles marker click events by displaying a popup window
        mMap.setOnMarkerClickListener(marker -> {


            // Handle the marker click event
            handleMarkerClick(marker, getContext());

            return true;
        });




    }

    private void setupMapBounds(GoogleMap map) {
        LatLngBounds SINGAPORE_BOUNDS = new LatLngBounds(
                new LatLng(1.1304753, 103.6920359), // Southwest corner
                new LatLng(1.4504753, 104.0120359)  // Northeast corner
        );
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(SINGAPORE_BOUNDS, 0));
        map.setLatLngBoundsForCameraTarget(SINGAPORE_BOUNDS);
    }

    private List<DistrictPolygon> createDistricts() {
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



    private List<BinMarker> get() {
        // Return a list of BinMarkers for Tampines or any specific area
        return Arrays.asList(
                new BinMarker(new LatLng(1.349539, 103.947958), "Tamp Bin 1"),
                new BinMarker(new LatLng(1.362551, 103.938913), "Tamp Bin 2")
                // Add more markers as needed
        );
    }

    private List<LatLng> getTampinesPolygon() {
        // Return a list of LatLng for Tampines polygon or any specific area
        return Arrays.asList(
                new LatLng(1.37508, 103.93144),
                new LatLng(1.348348, 103.924595),
                // Add more coordinates as needed
        );
    }

}