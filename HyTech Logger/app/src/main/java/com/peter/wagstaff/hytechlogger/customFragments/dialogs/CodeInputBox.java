package com.peter.wagstaff.hytechlogger.customFragments.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;

//A version of InputBox that makes the input a valid QR Code used to identify items
//Also allows for a custom negative
public abstract class CodeInputBox extends InputBox {

    /**
     * Declares an CodeInputBox given a Context, title, message, and error message
     * @param context
     * @param title
     * @param message
     * @param errorMessage
     */
    public CodeInputBox(Context context, String title, String message, String errorMessage) {

        super(context, title, message, errorMessage);
        input.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    /**
     * Builds the CodeInputDialogBox
     */
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

    /**
     * Sets action to perform on negative click in addition to dismissing the CodeInputDialogBox
     */
    public void customNegative() {}
}