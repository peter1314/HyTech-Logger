package com.peter.wagstaff.hytechlogger.customviews;

import android.content.Context;
import com.peter.wagstaff.hytechlogger.R;
import androidx.appcompat.widget.AppCompatRadioButton;

public class LocationRadioButton extends AppCompatRadioButton implements SelfConfiguring {

    private String[] options;

    public LocationRadioButton(Context context, String text, String[] options) {
        super(context);
        setLayoutParams(getParams(4,0,4,0));
        setTextColor(getResources().getColor(R.color.dark_grey));
        setText(text);;
        this.options = options;
    }

    public String[] getOptions() {return options; }
}
