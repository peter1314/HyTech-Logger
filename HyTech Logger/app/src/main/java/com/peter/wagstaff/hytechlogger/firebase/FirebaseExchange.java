package com.peter.wagstaff.hytechlogger.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.StockDataEntry;
import org.json.JSONException;
import androidx.annotation.NonNull;

public class FirebaseExchange {

    public static final DatabaseReference ROOT_REF = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
    public static final String TREE = "HYTECH_MAIN";

    public static void setData(String path, String data) {
        ROOT_REF.child(path).setValue(data);
    }

    public static void onGrab(String path, final DataUpdateAction action) {
        ROOT_REF.child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.doAction(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public static void onUpdate(String path, final DataUpdateAction action) {
        ROOT_REF.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                action.doAction(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public static void addDataEntry(String timeStamp, DataEntry entry) {
        setData(TREE + "/" + entry.getBranch() + "/" + GlobalVariables.currentEntryCode + "/LOGS/LAST", entry.toString());
        setData(TREE + "/" + entry.getBranch() + "/" + GlobalVariables.currentEntryCode + "/LOGS/" + timeStamp, entry.toString());
    }

    public static DataEntry entryFromSnapshot(String branch, DataSnapshot snapshot) {
        try {
            if(branch.equals(CellDataEntry.BRANCH)) {
                return new CellDataEntry(snapshot.getValue().toString());
            } else if(branch.equals(StockDataEntry.BRANCH)) {
                return new StockDataEntry(snapshot.getValue().toString());
            }
        } catch (JSONException e) {}
        return null;
    }
}
