package com.example.binbolehxfirebase;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ValueEventListener;

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
        }


   //handler to delay execution and show splash screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
            finish(); // Close the splash screen activity
        }, SPLASH_DISPLAY_LENGTH);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Get a reference to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ID");

        // Add a ValueEventListener to listen for changes at the specified location
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            //called when listener fails at server
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
