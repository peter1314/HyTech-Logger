package com.peter.wagstaff.hytechlogger.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import java.util.concurrent.CountDownLatch;
import androidx.annotation.NonNull;

public class FirebaseExchange {

    public static final DatabaseReference ROOT_REF = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
    public static final String BRANCH = "CELLS2";

    public static void setData(String path, String data) {
        ROOT_REF.child(path).setValue(data);
    }

    public static String getData(String path) {
        final CountDownLatch done = new CountDownLatch(1);
        final String data[] = {null};

        onGrab(path, new UpdateAction() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    data[0] = (String) snapshot.getValue();
                    done.countDown();
                }
            }
        });

        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {}
        return data[0];
    }

    public static void onGrab(String path, final UpdateAction action) {
        ROOT_REF.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.onUpdate(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public static void onUpdate(String path, final UpdateAction action) {
        ROOT_REF.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.onUpdate(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public static void addDataEntry(String timeStamp, DataEntry entry) {
        setData(BRANCH + "/" + GlobalVariables.currentCellCode + "/LOGS/LAST", entry.toString());
        setData(BRANCH + "/" + GlobalVariables.currentCellCode + "/LOGS/" + timeStamp, entry.toString());
    }
}
