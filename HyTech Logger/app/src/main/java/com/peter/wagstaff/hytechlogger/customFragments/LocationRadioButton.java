package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;
import androidx.appcompat.widget.AppCompatRadioButton;

//A RadioButton that has a LocationConfiguration associated with it
public class LocationRadioButton extends AppCompatRadioButton implements SelfConfiguring {

    //Associated Location Configuration
    public final LocationConfiguration CONFIG;

    /**
     * Declares a LocationRadioButton given a Context and LocationConfiguration
     * @param context
     * @param associtatedConfig
     */
    public LocationRadioButton(Context context, LocationConfiguration associtatedConfig) {
        super(context);
        CONFIG = associtatedConfig;
        setLayoutParams(getParams(4,0,4,0));
        setTextColor(getResources().getColor(R.color.dark_grey));
        setText(CONFIG.NAME);;
    }

    /**
     * @return The options this LocationRadioButton should provide a LocationSpinner
     */
    public String[] getOptions() {return CONFIG.ASSOCIATED_LOCATION.getOptions(); }
}
