package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import android.view.View;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import java.util.Set;
import androidx.appcompat.widget.AppCompatCheckBox;

public class LocationCheckBox extends AppCompatCheckBox implements  SelfConfiguring {

    private final LocationConfiguration CONFIG;

    public LocationCheckBox(Context context, String text, LocationConfiguration config) {
        super(context);
        setLayoutParams(getParams(4,0,4,0));
        setTextColor(getResources().getColor(R.color.dark_grey));
        setText(text);
        this.CONFIG = config;
    }

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
