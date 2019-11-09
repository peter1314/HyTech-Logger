package com.peter.wagstaff.hytechlogger.activities.rowinjection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import androidx.appcompat.widget.AppCompatEditText;

public class MinMaxEditText extends AppCompatEditText {

    double value;

    public MinMaxEditText(Context context) {
        super(context);
    }

    public MinMaxEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MinMaxEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public double getValue() { return value; }

    public void setUpdate(final ListnerAction action) {
        value = 0;

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                value = InputFormating.decimalFromString(getText().toString());
                action.doAction(null);
                return false;
            }
        });
    }
}
