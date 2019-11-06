package com.peter.wagstaff.hytechlogger;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalVariables {

    public static final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    public static String currentCellCode;
    public static boolean justLoggedOut = false;

}
