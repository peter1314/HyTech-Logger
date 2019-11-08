package com.peter.wagstaff.hytechlogger.dialogboxes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

//A version of popup box that allows for an input and performs an action on that input
public abstract class InputBox extends PopupBox{

    EditText input;
    String positiveLabel, errorMessage;

    public InputBox(Context context, String title, String message, String errorMessage) {
        super(context, title, message);
        input = new EditText(context);
        this.positiveLabel = "Enter";
        this.errorMessage = errorMessage;
    }

    void build() {
        super.build();
        dialogBuilder.setView(input);
        dialogBuilder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
    }

    public void show() {
        super.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onPositiveClicked(input.getText().toString())) {
                    dialog.dismiss();
                }
                else {
                    if(!errorMessage.equals("")) {
                        PopupToast.showPopup(context, errorMessage, 24);
                    }
                }
            }
        });
    }

    abstract public boolean onPositiveClicked(String userInput);
}