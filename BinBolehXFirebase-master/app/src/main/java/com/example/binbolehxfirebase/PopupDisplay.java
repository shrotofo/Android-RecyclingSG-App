package com.example.binbolehxfirebase;

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
