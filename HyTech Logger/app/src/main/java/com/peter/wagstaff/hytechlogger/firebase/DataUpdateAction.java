package com.peter.wagstaff.hytechlogger.firebase;

import com.google.firebase.database.DataSnapshot;

//Abstract class used to perform actions asynchronously with Firebase DataSnapshots
public abstract class DataUpdateAction {

    /**
     * Undefined action to be performed using a Firebase DataSnapshot
     * @param snapshot Firebase DataSnapshot
     */
    public abstract void doAction(DataSnapshot snapshot);
}
