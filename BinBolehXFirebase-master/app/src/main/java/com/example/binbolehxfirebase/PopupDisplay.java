package com.example.binbolehxfirebase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
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

public class PopupDisplay {

    private String dailyPercentValue; // Global variable to store dailyPercent value
    ImageView live_bin;
    TextView dailyPercent;

    public static void showPopup(Context context, BinMarker binMarker) {
        // Inflating the custom popup layout for the first popup
        View popupView1 = LayoutInflater.from(context).inflate(R.layout.popup1, null);
        PopupWindow popupWindow1 = new PopupWindow(popupView1, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow1.showAtLocation(popupView1, Gravity.CENTER, 0, 0); // Show the first popup
        // Locate UI elements where data needs to be displayed
        ImageView live_bin = popupView1.findViewById(R.id.live_bin);
        TextView binStatusText = popupView1.findViewById(R.id.textViewQa);
        // Firebase Database interaction

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
        DatabaseReference IDref = mRoot.child("0001");






        // Setting a click listener for the button in popup1 to transition to popup2
        popupView1.findViewById(R.id.button_image).setOnClickListener(view -> {
            DatabaseReference binMarkerRef = FirebaseDatabase.getInstance().getReference().child(binMarker.getIdbin());
            binMarkerRef.child(String.valueOf(binMarker.getIdbin())) // Ensure you are using the correct ID here
                    .child("dailyPercent")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Double new_percentage_value = snapshot.getValue(Double.class);
                            if (new_percentage_value != null) {
                                int cropPercentage = (int) ((1 - new_percentage_value) * live_bin.getHeight());
                                Drawable drawable = live_bin.getDrawable();
                                Bitmap originalBitmap = ((BitmapDrawable) drawable).getBitmap();
                                Bitmap croppedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), cropPercentage);
                                live_bin.setImageBitmap(croppedBitmap);
                                binStatusText.setText("Fill Level: " + new_percentage_value);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle potential errors
                        }
                    });










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

