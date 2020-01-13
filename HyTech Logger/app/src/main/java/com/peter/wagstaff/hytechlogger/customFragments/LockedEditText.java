package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

//An EditText that cannot be Edited
public class LockedEditText extends AppCompatEditText {

    /**
     * Declares LockedEditText
     * @param context
     */
    public LockedEditText(Context context) {
        super(context);
        initialize();
    }

    /**
     * Declares LockedEditText
     * @param context
     * @param attrs
     */
    public LockedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /**
     * Declares LockedEditText
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public LockedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    /**
     * Initializes the LockedEditText as uneditable, but selectable
     */
    private void initialize() {
        setInputType(InputType.TYPE_NULL);
        setTextIsSelectable(true);
        setKeyListener(null);
        setTextColor(Color.BLACK);
    }
}
