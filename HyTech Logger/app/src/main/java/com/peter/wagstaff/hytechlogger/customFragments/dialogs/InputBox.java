package com.peter.wagstaff.hytechlogger.customFragments.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

//A PopupBox that allows for an input and performs an action on that input
public abstract class InputBox extends PopupBox{

    EditText input;
    String positiveLabel, errorMessage;

    /**
     * Declares an InputBox given a Context, title, message, and error message
     * @param context
     * @param title
     * @param message
     * @param errorMessage
     */
    public InputBox(Context context, String title, String message, String errorMessage) {
        super(context, title, message);
        input = new EditText(context);
        //Defaults positive button to "Enter"
        this.positiveLabel = "Enter";
        this.errorMessage = errorMessage;
    }

    /**
     * Build the InputBox
     */
    void build() {
        super.build();
        dialogBuilder.setView(input);
        dialogBuilder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
    }

    /**
     * Show the InputBox
     */
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
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Used to set input filters
     * @param filters Filter array to apply to input box
     */
    public void setFilters(InputFilter[] filters) {
        input.setFilters(filters);
    }

    /**
     * Abstract method which specifies the action performed using the user input
     * @param userInput Input from the user
     * @return If the input was valid
     */
    abstract public boolean onPositiveClicked(String userInput);
}