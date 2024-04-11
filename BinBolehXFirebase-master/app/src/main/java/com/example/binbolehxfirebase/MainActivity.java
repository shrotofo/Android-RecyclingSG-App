package com.example.binbolehxfirebase;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import com.example.binbolehxfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

//Inheritance used to inherit methods from AppCompatActivity (managing fragments and UI)
public class MainActivity extends AppCompatActivity {
    // Private access modifiers for encapsulation
    private static final String TAG = "MainActivity";
    private static final int SPLASH_DISPLAY_LENGTH = 5000; // Display splash screen
    // Polymorphism allows MainActivity to implement a specific behavior for the onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View decorView = getWindow().getDecorView();
        //int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);

        // Retrieves the ActionBar and hides it for a full-screen experience
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
            //Initialise Firebase
            FirebaseApp.initializeApp(this);
        }
        DatabaseReference mRootDatabaseRef;
        mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();



   //handler to delay execution and show splash screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            finish(); // Close the splash screen activity
        }, SPLASH_DISPLAY_LENGTH);

        // onCreate() create totalWeight=0 and totalPercentage = 0 for every month of the year
        // Schedule the task to run at 12 AM every day
        MyAlarmScheduler.scheduleUpdate(this);

        createBins();
        createMonthNodes();
    }

    // Create four bins with weight = 0, percentage = 0
    public void createBins() {
        // Define the number of bins
        int numBins = 4;
        // Loop through and create child nodes dynamically
        for (int i = 1; i <= numBins; i++) {
            String ID = String.format("%04d", i); // Format the node key with leading zeros
            DatabaseReference binNodeRef = mRootDatabaseRef.child(ID);
            // Create "Weight" and "Percentage" child nodes under each child node
            binNodeRef.child("Weight").setValue(0);
            binNodeRef.child("Percentage").setValue(0);
        }
    }

    public void createMonthNodes() {
        // Create Months Node
        DatabaseReference YearNodeRef = mRootDatabaseRef.child("months");
        // Array of month names
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        // Iterate over the monthsNodeRef array and create nodes for each month
        for (String month : months) {
            // Create node for the month
            DatabaseReference monthNodeRef = YearNodeRef.child(month);
            // Set initial values for weight and percentage
            monthNodeRef.child("totalWeight").setValue(0)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("MainActivity", "totalWeight node for " + month + " created successfully");
                            } else {
                                Log.e("MainActivity", "Failed to totalWeight node for " + month, task.getException());
                            }
                        }
                    });

            monthNodeRef.child("totalPercentage").setValue(0)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("MainActivity", "totalPercentage node for " + month + " created successfully");
                            } else {
                                Log.e("MainActivity", "Failed to create totalPercentage node for " + month, task.getException());
                            }
                        }
                    });
        }
    }


}





