package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.MinMaxEditText;
import com.peter.wagstaff.hytechlogger.customFragments.SelfConfiguring;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;

public class MinMaxHolder extends LinearLayout implements SelfConfiguring {

    private String attributeID;
    private MinMaxEditText min, max;

    public MinMaxHolder(Context context, Attribute attribute) {
        super(context);
        setGravity(Gravity.CENTER);

        this.attributeID = attribute.KEY;
        min = new MinMaxEditText(getContext());
        max = new MinMaxEditText(getContext());

        TextView textView = new TextView(getContext());
        textView.setWidth(dpToPixels(130));
        textView.setText("< " + attribute.DISPLAY + " <");
        textView.setTextSize(15);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        addView(min);
        addView(textView);
        addView(max);
    }

    public void setUpdate(ListnerAction action) {
        min.setUpdate(action);
        max.setUpdate(action);
    }

    public String getAttributeID() { return attributeID; }

    public double getMin() { return min.getValue(); }

    public double getMax() { return max.getValue(); }
}
