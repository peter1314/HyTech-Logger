package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import com.peter.wagstaff.hytechlogger.R;
import androidx.appcompat.widget.AppCompatSpinner;

//Spinner to display options associated with a Location
public class LocationSpinner extends AppCompatSpinner {

    /**
     * Declare LocationSpinner
     * @param context
     */
    public LocationSpinner(Context context) {
        super(context);
    }

    /**
     * Declare LocationSpinner
     * @param context
     */
    public LocationSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Declare LocationSpinner
     * @param context
     */
    public LocationSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Populate the LocationSpinner given an array of Strings
     * Maybe this should be changed to receive a Location itself
     * @param spinnerArray Array of Strings, should represent a Locations options
     */
    public void populate(String[] spinnerArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), R.layout.fragment_spinner_item, spinnerArray);
        setAdapter(adapter);
    }
}
