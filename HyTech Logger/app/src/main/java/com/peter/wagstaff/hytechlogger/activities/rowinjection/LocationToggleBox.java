package com.peter.wagstaff.hytechlogger.activities.rowinjection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import java.util.Map;
import java.util.Set;
import androidx.appcompat.widget.AppCompatCheckBox;

public class LocationToggleBox extends AppCompatCheckBox {
    public LocationToggleBox(Context context) {
        super(context);
    }

    public LocationToggleBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationToggleBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setToggle(final Set configs, final Map config, final ListnerAction action) {
        setChecked(true);
        configs.add(config);
        this.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isChecked()) {
                    configs.add(config);
                } else {
                    configs.remove(config);
                }
                action.doAction(null);
            }
        });
    }
}
