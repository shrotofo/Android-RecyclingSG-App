package com.example.binbolehxfirebase;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class itemDecor extends RecyclerView.ItemDecoration {
    private final int space;

    public itemDecor(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = space; // Add space value to the left of each item
        outRect.top = space; // Add space value to the top of each item
        outRect.right = space; // Add space value to the right of each item
        outRect.bottom = space;// Add space value to the bottom of each item
    }
}

