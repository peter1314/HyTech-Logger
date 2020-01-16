package com.peter.wagstaff.hytechlogger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

public abstract class CodeInputDialog {

    EditText input;
    AlertDialog dialog;
    String errorMessage;
    Context context;

    public CodeInputDialog(Context context, String title, String message, String errorMessage) {

        input = new EditText(context);
        input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setView(input);

        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                customNegative();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        this.errorMessage = errorMessage;
        this.context = context;
    }

    public void customNegative() {

    }

    abstract public boolean onPositiveClicked(String userInput);

    public void show() {
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onPositiveClicked(input.getText().toString())) {
                    dialog.dismiss();
                }
                else {
                    if(!errorMessage.equals("")) {
                        PopupMessage.showPopup(context, errorMessage, 24);
                    }
                }
            }
        });
    }
}