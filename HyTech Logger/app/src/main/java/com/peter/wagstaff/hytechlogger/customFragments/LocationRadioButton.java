package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import androidx.appcompat.widget.AppCompatRadioButton;

public class LocationRadioButton extends AppCompatRadioButton implements SelfConfiguring {

    public final LocationConfiguration ASSOCIATED_CONFIG;

    public LocationRadioButton(Context context, LocationConfiguration associtatedConfig) {
        super(context);
        setLayoutParams(getParams(4,0,4,0));
        setTextColor(getResources().getColor(R.color.dark_grey));
        ASSOCIATED_CONFIG = associtatedConfig;
        setText(ASSOCIATED_CONFIG.NAME);;
    }

    public String[] getOptions() {return ASSOCIATED_CONFIG.ASSOCIATED_LOCATION.getOptions(); }
}
