package com.peter.wagstaff.hytechlogger.firebase;

import com.google.firebase.database.DataSnapshot;

public abstract class DataUpdateAction {

    public abstract void doAction(DataSnapshot snapshot);
}
