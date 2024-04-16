package com.example.binbolehxfirebase;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.example.binbolehxfirebase.models.DistrictLocationsModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class BarGraph {
    private BarChart barChart;
    private DatabaseReference mDatabase;
    private Context context;

    public BarGraph(Context context, BarChart barChart) {
        this.context = context;
        this.barChart = barChart;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("binDetails");
    }

    public void fetchAndUpdateGraphData() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DistrictLocationsModel> graphDataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DistrictLocationsModel itemValue = dataSnapshot.getValue(DistrictLocationsModel.class);
                    if (itemValue != null) {
                        graphDataList.add(itemValue);
                    }
                }
                showBarChart(graphDataList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBarChart(ArrayList<DistrictLocationsModel> graphDataList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Bin Percentage";

        for (int i = 0; i < graphDataList.size(); i++) {
            entries.add(new BarEntry(i, graphDataList.get(i).getDailyPercent())); // i is explicitly set as the x-value
        }


        BarDataSet barDataSet = new BarDataSet(entries, title);
        barDataSet.setColor(ContextCompat.getColor(context, R.color.green));


        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(false);




        BarData data = new BarData(barDataSet);
        barChart.getDescription().setEnabled(false);
        barChart.setData(data);
        barChart.invalidate();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e instanceof BarEntry) {
                    BarEntry barEntry = (BarEntry) e;
                    int index = (int) barEntry.getX();
                    if (index >= 0 && index < graphDataList.size()) {
                        DistrictLocationsModel item = graphDataList.get(index);
                        Toast.makeText(context, "District: " + item.getDistrict(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected() {
                // Code for no selection made
            }
        });

    }






    }






