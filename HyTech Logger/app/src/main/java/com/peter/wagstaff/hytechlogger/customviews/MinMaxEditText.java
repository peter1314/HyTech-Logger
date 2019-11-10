package com.peter.wagstaff.hytechlogger.customviews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import androidx.appcompat.widget.AppCompatEditText;

public class MinMaxEditText extends AppCompatEditText implements SelfConfiguring {

    public MinMaxEditText(Context context) {
        super(context);
        setLayoutParams(getParams(0,0,0,0));
        setWidth(dpToPixels(80));
        setTextSize(15);
        setSingleLine();
    }

    public void setUpdate(final ListnerAction action) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {action.doAction(null);}
        });
    }

    public double getValue() { return InputFormating.decimalFromString(getText().toString()); }
}
