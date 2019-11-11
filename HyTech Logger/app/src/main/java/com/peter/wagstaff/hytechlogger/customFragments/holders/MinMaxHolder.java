package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.MinMaxEditText;
import com.peter.wagstaff.hytechlogger.customFragments.SelfConfiguring;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;

//Used to hold two MinMaxEditTexts and displays a the attribute name
public class MinMaxHolder extends LinearLayout implements SelfConfiguring {

    //ID of the attribute to search for
    private String attributeID;
    //Associated MinMaxEditTexts
    private MinMaxEditText min, max;

    /**
     * Declares MinMaxHolder given a Context and Attribute
     * @param context
     * @param attribute
     */
    public MinMaxHolder(Context context, Attribute attribute) {
        super(context);
        setGravity(Gravity.CENTER);

        //Creates its MinMaxEditTexts
        this.attributeID = attribute.KEY;
        min = new MinMaxEditText(getContext());
        max = new MinMaxEditText(getContext());

        //Create TextView to display the Attribute being tested
        TextView textView = new TextView(getContext());
        textView.setWidth(dpToPixels(130));
        textView.setText("< " + attribute.DISPLAY + " <");
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        addView(min);
        addView(textView);
        addView(max);
    }

    /**
     * Specify the action to be performed when the either MinMaxEditText is updated
     * @param action ListenerAction to perform action on change
     */
    public void setUpdate(ListnerAction action) {
        min.setUpdate(action);
        max.setUpdate(action);
    }

    /**
     * Returns the ID of the Attribute being tested
     * @return
     */
    public String getAttributeID() { return attributeID; }

    /**
     * Retruns the value of the min MinMaxEditText
     * @return
     */
    public double getMin() { return min.getValue(); }

    /**
     * Retruns the value of the max MinMaxEditText
     * @return
     */
    public double getMax() { return max.getValue(); }
}
