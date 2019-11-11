package com.peter.wagstaff.hytechlogger.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;
import androidx.annotation.NonNull;

//Class used to communicate with the Firebase database
public class FirebaseExchange {

    //Database reference, but limited to the app's root
    private static final DatabaseReference ROOT_REF = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child(GlobalVariables.APP_ROOT);

    /**
     * Set node in the database to a specified value
     * @param path Path to node
     * @param data Data to put in node
     */
    private static void setData(String path, String data) {
        ROOT_REF.child(path).setValue(data);
    }

    /**
     * Specifiy action to perform on the grabbing of a DataSnapshot of a node
     * @param path Path to node
     * @param action DataUpdateAction to perform on grab
     */
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

    /**
     * Specifiy action to perform on the update of a DataSnapshot of a node
     * @param path Path to node
     * @param action DataUpdateAction to perform on update
     */
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

    /**
     * Adds a ItemEntry to the Logs of a Data
     * Also sets the LAST node to this ItemEntry
     * @param timeStamp Chronologically formatted date, used as node tag
     * @param entry ItemEntry to set as node value
     */
    public static void addDataEntry(String timeStamp, ItemEntry entry) {
        setData(entry.getType().BRANCH + "/" +GlobalVariables.currentItemEntryCode + "/LOGS/LAST", entry.toString());
        setData(entry.getType().BRANCH + "/" + GlobalVariables.currentItemEntryCode + "/LOGS/" + timeStamp, entry.toString());
    }

    /**
     * Static method to generate a ItemEntry from a DataSnapshot
     * @param branch Branch of the database the snapshot is from, determines dynamic type of return
     * @param snapshot DataSnapshot to generate ItemEntry from
     * @return ItemEntry initialized from snapshot
     */
    public static ItemEntry entryFromSnapshot(String branch, DataSnapshot snapshot) {
        if(branch.equals(CellType.getInstance().BRANCH)) {
            return new ItemEntry(CellType.getInstance(), snapshot.getValue().toString());
        } else if(branch.equals(StockType.getInstance().BRANCH)) {
            return new ItemEntry(StockType.getInstance(), snapshot.getValue().toString());
        }
        return null;
    }
}
