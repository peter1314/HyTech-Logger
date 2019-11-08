package com.peter.wagstaff.hytechlogger.customviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class LockedEditText extends AppCompatEditText {
    public LockedEditText(Context context) {
        super(context);
        initialize();
    }

    public LockedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initialize() {
        setEnabled(false);
        setTextColor(Color.BLACK);
    }
}
