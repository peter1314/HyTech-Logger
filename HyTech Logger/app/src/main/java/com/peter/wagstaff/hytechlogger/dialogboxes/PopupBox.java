package com.peter.wagstaff.hytechlogger.dialogboxes;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

//Displays a popup message and can be dismissed
public class PopupBox {

    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;
    String title, message, negativeLabel;
    Boolean built;
    Context context;

    public PopupBox(Context context, String title, String message) {
        dialogBuilder = new AlertDialog.Builder(context);
        this.title = title;
        this.message = message;
        this.negativeLabel = "Cancel";
        this.context = context;
        built = false;
    }

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

    public void show() {
        if(!built) {
            build();
            dialog = dialogBuilder.create();
        }
        dialog.show();
    }
}