package com.peter.wagstaff.hytechlogger;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

public abstract class DataUpdate {

    public static void onInitialUpdate(String path, final DataUpdate d) {
        GlobalVariables.rootRef.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                d.onUpdate(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void onUpdate(String path, final DataUpdate d) {
        GlobalVariables.rootRef.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                d.onUpdate(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public abstract void onUpdate(DataSnapshot snapshot);
}
