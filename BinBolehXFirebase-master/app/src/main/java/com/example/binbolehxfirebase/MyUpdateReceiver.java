package com.example.binbolehxfirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
public class MyUpdateReceiver extends BroadcastReceiver {

    DatabaseReference mRootDatabaseRef;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the update received from Firebase Realtime Database
        // This could involve updating UI, performing calculations, etc.
        mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();

        // Daily sum of all bins computed
        // Attach a ValueEventListener to the parent node
        mRootDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the current month
                Calendar calendar = Calendar.getInstance();
                int currentMonth = calendar.get(Calendar.MONTH); // January is 0, February is 1, ..., December is 11

                // Array of month names
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                String currentMonthName = months[currentMonth];

                // Reference to the current month node
                DatabaseReference monthNodeRef = FirebaseDatabase.getInstance().getReference().child("month").child(currentMonthName);

                mRootDatabaseRef.child("binDetails").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        double totalWeight = 0;
                        double totalPercentage = 0;

                        for (DataSnapshot binSnapshot : dataSnapshot.getChildren()) {
                            double binWeight = binSnapshot.child("weight").getValue(Integer.class);
                            double binPercentage = binSnapshot.child("dailyPercent").getValue(Integer.class);
                            totalWeight += binWeight;
                            totalPercentage += binPercentage;
                        }


                        // Update total weight and total percentage in the database
                        monthNodeRef.child("totalWeight").setValue(totalWeight);
                        monthNodeRef.child("totalPercentage").setValue(totalPercentage);

                        //reset values to 0 daily
                        mRootDatabaseRef.child("binDetails").child("weight").setValue(0);
                        mRootDatabaseRef.child("binDetails").child("dailyPercent").setValue(0);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
