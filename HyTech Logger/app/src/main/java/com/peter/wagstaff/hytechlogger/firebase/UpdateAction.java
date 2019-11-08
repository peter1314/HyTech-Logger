package com.peter.wagstaff.hytechlogger.firebase;

import com.google.firebase.database.DataSnapshot;

public abstract class UpdateAction {

    public abstract void onUpdate(DataSnapshot snapshot);
}
