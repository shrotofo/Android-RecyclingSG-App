package com.example.binbolehxfirebase;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    // Method invoked when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage); // Set the layout for the activity
        View decorView = getWindow().getDecorView(); // Get the activity's decor view
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN; // Set system UI visibility options
        decorView.setSystemUiVisibility(uiOptions); // Apply system UI visibility options

        ActionBar actionBar = getSupportActionBar(); // Get the ActionBar
        if (actionBar != null) {
            actionBar.hide(); // Hide the ActionBar if present
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation); // Get the bottom navigation view

        // Set listener for bottom navigation item selection
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            int itemId = item.getItemId(); // Get the selected item's ID
            // Determine the selected fragment based on the selected item
            if (itemId == R.id.navigation_home) {
                selectedFragment = new HomeFragment(); // Instantiate HomeFragment
            } else if (itemId == R.id.navigation_map) {
                selectedFragment = new MapPage(); // Instantiate MapPage
            } else if (itemId == R.id.navigation_settings) {
                selectedFragment = new SettingPage(); // Instantiate SettingPage
            } else if (itemId == R.id.navigation_aichatbot) {
                selectedFragment = new AIChatBot(); // Instantiate AIChatBot
            } else {
                return false; // Return false if the item is not recognized
            }

            // Replace the fragment container with the selected fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true; // Indicate that the item selection has been handled
        });

        // Select the default fragment (home) when activity is first created
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.navigation_home);
        }
    }
}