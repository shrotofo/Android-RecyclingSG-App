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

    // Private access modifiers for encapsulation
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

            }
            Log.d("hello", "im here");
            // Initialize Firebase
            FirebaseApp.initializeApp(this);
            Log.d("Firebase", "Firebase initialized successfully");
            DatabaseReference mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();

// Check if Firebase is initialized
            FirebaseApp firebaseApp = FirebaseApp.getInstance();
            if (firebaseApp != null) {
                Log.d("Firebase", "Firebase initialized successfully");
            } else {
                Log.e("Firebase", "Firebase not initialized");
            }

            String binId = "0001"; // Example bin ID
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(binId).child("dailyPercent");


            Log.d("FirebaseCheck", "Starting to fetch the data for bin ID: " + binId);


            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("0001").child("dailyPercent");
            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Long dailyPercent = task.getResult().getValue(Long.class);
                    Log.d("FirebaseData", "Fetched dailyPercent: " + dailyPercent);
                } else {
                    Log.e("FirebaseData", "Error fetching dailyPercent", task.getException());
                }
            });


            //handler to delay execution and show splash screen
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(com.example.binbolehxfirebase.MainActivity.this, HomePage.class);
                startActivity(intent);
                finish(); // Close the splash screen activity
            }, SPLASH_DISPLAY_LENGTH);

            // onCreate() create totalWeight=0 and totalPercentage = 0 for every month of the year
            // Schedule the task to run at 12 AM every day


        }

    }












