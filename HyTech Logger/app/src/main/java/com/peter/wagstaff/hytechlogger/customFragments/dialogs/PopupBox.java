package com.peter.wagstaff.hytechlogger.customFragments.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

//Used to display a popup box that can be dismissed
public class PopupBox {

    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;
    String title, message, negativeLabel;
    Boolean built;
    Context context;

    /**
     * Declares a PopupBox given a Context, title and message
     * @param context
     * @param title
     * @param message
     */
    public PopupBox(Context context, String title, String message) {
        dialogBuilder = new AlertDialog.Builder(context);
        this.title = title;
        this.message = message;
        //Default's the negative button label to "Cancel"
        this.negativeLabel = "Cancel";
        this.context = context;
        built = false;
    }

    /**
     * Builds the PopupBox
     */
    void build() {
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        built = true;
    }

    /**
     * Shows the PopupBox
     */
    public void show() {
        if(!built) {
            build();
            dialog = dialogBuilder.create();
        }
        dialog.show();
    }
}