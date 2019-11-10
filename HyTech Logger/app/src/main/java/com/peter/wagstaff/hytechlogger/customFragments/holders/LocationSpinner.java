package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.peter.wagstaff.hytechlogger.R;

import androidx.appcompat.widget.AppCompatSpinner;

public class LocationSpinner extends AppCompatSpinner {

    public LocationSpinner(Context context) {
        super(context);
    }
    public LocationSpinner(Context context, AttributeSet attrs) { super(context, attrs); }
    public LocationSpinner(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void populate(String[] spinnerArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.fragment_spinner_item, spinnerArray);
        setAdapter(adapter);
    }
}
