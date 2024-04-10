package com.example.binbolehxfirebase;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopupDisplay {

    public static void showPopup(Context context, BinMarker binMarker) {
        // Show the first popup
        PopupWindow popupWindow = createAndShowPopup(context, R.layout.popup1, view -> {
            // Logic for what happens when the button in popup1 is clicked
            // Dismiss popup1 and show popup2
            showSecondPopup(context);
        });


    }

    private static void showSecondPopup(Context context) {
        // Show the second popup
        createAndShowPopup(context, R.layout.popup2, view -> {

        });
    }

    private static PopupWindow createAndShowPopup(Context context, int layoutResId, View.OnClickListener onClickListener) {
        // Inflating the custom popup layout
        View popupView = LayoutInflater.from(context).inflate(layoutResId, null);
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Assume the button ID is common or adjust accordingly
        View button = popupView.findViewById(R.id.button_image); // Adjust ID as necessary
        if (button != null) {
            button.setOnClickListener(onClickListener);
        }

        // Show the popup window
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        return popupWindow;
    }
}
