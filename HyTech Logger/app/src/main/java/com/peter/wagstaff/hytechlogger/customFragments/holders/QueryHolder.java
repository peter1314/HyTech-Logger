package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.SelfConfiguring;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;

public class QueryHolder extends LinearLayout implements SelfConfiguring {

    private String attributeID;
    private EditText queryText;

    public QueryHolder(Context context, Attribute attribute) {
        super(context);

        this.attributeID = attribute.KEY;

        TextView textView = new TextView(getContext());
        textView.setMinWidth(dpToPixels(80));
        textView.setPadding(dpToPixels(10), 0, dpToPixels(10), 0);
        textView.setText(attribute.DISPLAY + ": ");
        textView.setTextSize(15);

        queryText = new EditText(getContext());
        queryText.setLayoutParams(getStretchParams(0,0,10, 0));
        queryText.setTextSize(15);
        queryText.setSingleLine();

        addView(textView);
        addView(queryText);
    }

    public void setUpdate(ListnerAction action) {
        queryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {action.doAction(null);}
        });
    }

    public String getAttributeID() { return attributeID; }

    public String getQuery() { return queryText.getText().toString(); }
}
