package com.peter.wagstaff.hytechlogger.activities.rowinjection;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

public class LocationRadioButton extends AppCompatRadioButton {

    private String[] options;

    public LocationRadioButton(Context context) {
        super(context);
    }

    public LocationRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOptions(String[] options) { this.options = options; }

    public String[] getOptions() {return options; }

}
