package com.peter.wagstaff.hytechlogger.customFragments.dialogs;

import android.content.Context;
import android.text.InputType;

//A version of InputBox that makes the input a password field
public abstract class PasswordInputBox extends InputBox{

    public PasswordInputBox(Context context, String title, String message, String errorMessage) {

        super(context, title, message, errorMessage);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}