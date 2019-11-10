package com.peter.wagstaff.hytechlogger.customviews.holders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MyRadioGroup<ButtonType extends RadioButton> extends RadioGroup {

    public MyRadioGroup(Context context) {
        super(context);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) { super(context, attrs); }

    public ButtonType addRadioButton(ButtonType button) {
        addView(button);
        return null;
    }
}
