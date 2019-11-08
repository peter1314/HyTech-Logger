package com.peter.wagstaff.hytechlogger;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GlobalVariables {

    public static final DatabaseReference ROOT_REF = FirebaseDatabase.getInstance().getReference();
    public static final String BRANCH = "CELLS2";
    public static String currentCellCode;
    public static boolean justLoggedOut = false;

}
