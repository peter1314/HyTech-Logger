package com.peter.wagstaff.hytechlogger.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.peter.wagstaff.hytechlogger.R;

import java.util.Map;
import java.util.Set;
import androidx.appcompat.widget.AppCompatCheckBox;

public class LocationCheckBox extends AppCompatCheckBox implements  SelfConfiguring {

    private Map config;

    public LocationCheckBox(Context context, String text, Map config) {
        super(context);
        setLayoutParams(getParams(4,0,4,0));
        setTextColor(getResources().getColor(R.color.dark_grey));
        setText(text);
        this.config = config;
    }

    public void setToggle(final Set configs, final ListnerAction action) {
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
