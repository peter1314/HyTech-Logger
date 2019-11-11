package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.MinMaxEditText;
import com.peter.wagstaff.hytechlogger.customFragments.SelfConfiguring;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;

//Used to hold two MinMaxEditTexts and displays a the attribute name
public class MinMaxHolder extends LinearLayout implements SelfConfiguring {

    //ID of the attribute to search for
    public final String ATTRIBUTE_ID;
    //Associated MinMaxEditTexts
    private final MinMaxEditText MIN_TEXT, MAX_TEXT;

    /**
     * Declares MinMaxHolder given a Context and Attribute
     * @param context
     * @param attribute
     */
    public MinMaxHolder(Context context, Attribute attribute) {
        super(context);
        setGravity(Gravity.CENTER);

        //Creates its MinMaxEditTexts
        this.ATTRIBUTE_ID = attribute.KEY;
        MIN_TEXT = new MinMaxEditText(getContext());
        MAX_TEXT = new MinMaxEditText(getContext());

        //Create TextView to display the Attribute being tested
        TextView textView = new TextView(getContext());
        textView.setWidth(dpToPixels(130));
        textView.setText("< " + attribute.DISPLAY + " <");
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        //Sets InputType of the MinMaxEditTexts
        //For some reason this isn't working
        MIN_TEXT.setInputType(attribute.INPUT_TYPE);
        MAX_TEXT.setInputType(attribute.INPUT_TYPE);

        addView(MIN_TEXT);
        addView(textView);
        addView(MAX_TEXT);
    }

    /**
     * Specify the action to be performed when the either MinMaxEditText is updated
     * @param action ListenerAction to perform action on change
     */
    public void setUpdate(ListnerAction action) {
        MIN_TEXT.setUpdate(action);
        MAX_TEXT.setUpdate(action);
    }

    /**
     * Retruns the value of the min MinMaxEditText
     * @return
     */
    public double getMin() { return MIN_TEXT.getValue(); }

    /**
     * Retruns the value of the max MinMaxEditText
     * @return
     */
    public double getMax() { return MAX_TEXT.getValue(); }
}
