package com.peter.wagstaff.hytechlogger.customviews.dialogboxes;

import android.content.Context;
import android.widget.Toast;

public class PopupToast {

    public static void showPopup(Context context, String errorMessage, int textSize) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
