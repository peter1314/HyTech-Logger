package com.peter.wagstaff.hytechlogger.DialogBoxes;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;

//A version of InputBox that makes the input a ID, and also allows for a custom negative
public abstract class CodeInputDialog extends InputBox {

    public CodeInputDialog(Context context, String title, String message, String errorMessage) {

        super(context, title, message, errorMessage);
        input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    void build() {
        super.build();
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                customNegative();
                dialog.dismiss();
            }
        });
    }

    public void customNegative() {}
}