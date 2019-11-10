package com.peter.wagstaff.hytechlogger.customviews.holders;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.customviews.ListnerAction;
import com.peter.wagstaff.hytechlogger.customviews.MinMaxEditText;
import com.peter.wagstaff.hytechlogger.customviews.SelfConfiguring;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;

public class MinMaxHolder extends LinearLayout implements SelfConfiguring {

    private String attributeID;
    private MinMaxEditText min, max;

    public MinMaxHolder(Context context, Attribute attribute) {
        super(context);
        setGravity(Gravity.CENTER);

        this.attributeID = attribute.ID;
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
