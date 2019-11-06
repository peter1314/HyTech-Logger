package com.peter.wagstaff.hytechlogger;

import android.content.Context;
import android.widget.Toast;

public class PopupMessage {

    public static void showPopup(Context context, String errorMessage, int textSize) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
