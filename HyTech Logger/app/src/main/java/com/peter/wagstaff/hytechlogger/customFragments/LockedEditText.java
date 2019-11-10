package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class LockedEditText extends AppCompatEditText {
    public LockedEditText(Context context) {
        super(context);
        setEnabled(false);
        setTextColor(Color.BLACK);
    }

    public LockedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LockedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        setEnabled(false);
        setTextColor(Color.BLACK);
    }
}
