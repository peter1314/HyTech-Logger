package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import androidx.appcompat.widget.AppCompatEditText;

//A stylized EditText used for min and max tests
public class MinMaxEditText extends AppCompatEditText implements SelfConfiguring {

    /**
     * Declares a MinMaxEditText and stylizes it
     * @param context
     */
    public MinMaxEditText(Context context) {
        super(context);
        setLayoutParams(getParams());
        setWidth(dpToPixels(80));
        setTextSize(15);
        setSingleLine();
    }

    /**
     * Specify an action that is performed with the MinMaxEditText is changed
     * @param action ListenerAction to perform action on change
     */
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

    /**
     * Gets the value entered into the MinMaxEditText
     * @return The text of the MinMaxEditText as a double
     */
    public double getValue() { return InputFormatting.decimalFromString(getText().toString()); }
}
