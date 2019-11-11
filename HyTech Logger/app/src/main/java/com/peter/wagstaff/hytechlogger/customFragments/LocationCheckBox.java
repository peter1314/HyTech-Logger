package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import android.view.View;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import java.util.Set;
import androidx.appcompat.widget.AppCompatCheckBox;

//A CheckBox that has a LocationConfiguration associated with it
public class LocationCheckBox extends AppCompatCheckBox implements  SelfConfiguring {

    //Associated Location Configuration
    private final LocationConfiguration CONFIG;

    /**
     * Declares a LocationCheckBox given a Context and LocationConfiguration
     * @param context
     * @param config
     */
    public LocationCheckBox(Context context, LocationConfiguration config) {
        super(context);
        this.CONFIG = config;
        //Stylizes the LocationCheckBox
        setLayoutParams(getParams(4,0,4,0));
        setTextColor(getResources().getColor(R.color.dark_grey));
        setText(CONFIG.NAME);
    }

    /**
     * Specifies a set of LocationConfigurations to remove or add the LocationCheckBox's LocationConfiguration
     * Also specify an action that is performed with the LocationCheckBox is changed
     * @param configs Set of LocationConfigurations
     * @param action ListenerAction to perform action on change
     */
    public void setToggle(final Set configs, final ListnerAction action) {
        setChecked(true);
        configs.add(CONFIG);
        this.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isChecked()) {
                    configs.add(CONFIG);
                } else {
                    configs.remove(CONFIG);
                }
                action.doAction(null);
            }
        });
    }
}
