package com.example.binbolehxfirebase;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseReference mDatabase;
    private List<MonthData> monthsList = new ArrayList<>();

    private ProgressBar progressPB;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);

        progressPB = view.findViewById(R.id.progressPB);
        progressPB.setVisibility(View.VISIBLE);




        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Assume your months are stored under "months" node in Firebase
        mDatabase.child("month").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                monthsList.clear();

                // Iterate over all months and fetch the data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MonthData monthData = dataSnapshot.getValue(MonthData.class);
                    if (monthData != null) {
                        monthsList.add(monthData);
                    }
                }

                // After fetching the data, sort it by month names if necessary
                // and then pass the sorted list to your SpikeChartView to draw the graph
                Collections.sort(monthsList, new Comparator<MonthData>() {
                    @Override
                    public int compare(MonthData m1, MonthData m2) {
                        // Implement comparison logic for sorting by month names
                        return m1.getName().compareTo(m2.getName());
                    }
                });

                //  method in your SpikeChartView to set the data
                SpikeChartView spikeChartView = view.findViewById(R.id.spikeChartView);
                spikeChartView.setMonths(monthsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });


        // Return the inflated view
        return view;

    }
}

