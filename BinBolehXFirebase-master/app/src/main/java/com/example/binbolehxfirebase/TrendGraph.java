package com.example.binbolehxfirebase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // Retrieve data from Firebase Realtime Database
        DatabaseReference monthsRef = FirebaseDatabase.getInstance().getReference().child("months");

        monthsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> entries = new ArrayList<>();

                // Iterate through the children (months)
                for (DataSnapshot monthSnapshot : dataSnapshot.getChildren()) {
                    String monthName = monthSnapshot.getKey();
                    int weight = monthSnapshot.child("weight").getValue(Integer.class);

                    // Add the weight as an Entry to the entries list
                    entries.add(new Entry(monthToIndex(monthName), weight));
                }

                // Create a LineDataSet from the entries
                LineDataSet dataSet = new LineDataSet(entries, "Weight over Months");

                // Create a LineData object with the LineDataSet
                LineData lineData = new LineData(dataSet);

                // Get the LineChart view from your layout
                LineChart lineChart = findViewById(R.id.lineChart);

                // Set the data to the LineChart
                lineChart.setData(lineData);

                // Invalidate the chart to refresh it
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }

    // Helper method to convert month name to index
    private int monthToIndex(String monthName) {
        // Implement logic to map month names to indices (e.g., using an array)
        // For simplicity, you can hardcode the mapping in this example
        // Alternatively, you can use a HashMap to map month names to indices
        // Remember that array indices start from 0
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return Arrays.asList(months).indexOf(monthName);
    }
}
