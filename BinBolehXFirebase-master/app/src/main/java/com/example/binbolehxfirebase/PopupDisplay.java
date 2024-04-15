package com.example.binbolehxfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopupDisplay {

    public static void showPopup(Context context, BinMarker binMarker) {
        // Inflating the custom popup layout for the first popup
        View popupView1 = LayoutInflater.from(context).inflate(R.layout.popup1, null);
        PopupWindow popupWindow1 = new PopupWindow(popupView1, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow1.showAtLocation(popupView1, Gravity.CENTER, 0, 0); // Show the first popup
        // Locate UI elements where data needs to be displayed
        ImageView live_bin = popupView1.findViewById(R.id.live_bin);
        TextView binStatusText = popupView1.findViewById(R.id.textViewQa);
        // Firebase Database interaction

        DatabaseReference binMarkerRef = FirebaseDatabase.getInstance().getReference().child(binMarker.getIdbin());

        // Accessing the 'dailyPercent' field
        DatabaseReference dailyPercentRef = binMarkerRef.child("dailyPercent");
        binMarkerRef.child("dailyPercent").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Integer dailyPercent = task.getResult().getValue(Integer.class);
                if (dailyPercent != null) {
                    Log.d("Firebase", "Retrieved dailyPercent: " + dailyPercent);
                    String percentText = "Fill Level: " + dailyPercent + "%";
                    // UI update on main thread
                    ((Activity) context).runOnUiThread(() -> binStatusText.setText(percentText));
                } else {
                    Log.d("Firebase", "dailyPercent value is null");
                }
            } else {
                Log.e("Firebase", "Error getting dailyPercent", task.getException());
            }
        });

        // ... remaining code ...


        // Accessing the 'weight' field


        // Setting a click listener for the button in popup1 to transition to popup2
        popupView1.findViewById(R.id.button_image).setOnClickListener(view -> {
            popupWindow1.dismiss(); // Dismiss popup1 upon clicking the button

            // Inflating popup2 after dismissing popup1
            View popupView2 = LayoutInflater.from(context).inflate(R.layout.popup2, null);
            PopupWindow popupWindow2 = new PopupWindow(popupView2, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // Set a click listener for the close button in popup2
            popupView2.findViewById(R.id.close_button).setOnClickListener(view1 -> {
                // Logic to close popup2
                popupWindow2.dismiss();
            });

            // Show popup2
            popupWindow2.showAtLocation(popupView2, Gravity.CENTER, 0, 0);
        });


    }

}





