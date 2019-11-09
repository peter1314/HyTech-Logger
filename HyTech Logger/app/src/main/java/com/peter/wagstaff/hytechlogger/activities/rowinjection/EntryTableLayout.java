package com.peter.wagstaff.hytechlogger.activities.rowinjection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.R;

public class EntryTableLayout<InputText extends EditText> extends TableLayout  {

    LayoutInflater inflater;
    int ref;

    public EntryTableLayout(Context context) {
        super(context);
    }

    public EntryTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTools(LayoutInflater inflater, int ref) {
        this.inflater = inflater;
        this.ref = ref;
    }

    public InputText addRow(String name) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView label = (TextView) inflater.inflate(R.layout.input_label, null);
        label.setText(name);
        newRow.addView(label);

        InputText inputText = (InputText) inflater.inflate(ref, null);
        inputText.setText("");
        newRow.addView(inputText);

        addView(newRow);
        return inputText;
    }

    public InputText addRow(String name, String value, int type) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView label = (TextView) inflater.inflate(R.layout.input_label, null);
        label.setText(name);
        newRow.addView(label);

        InputText inputText = (InputText) inflater.inflate(ref, null);
        inputText.setInputType(type);
        inputText.setText(value);
        newRow.addView(inputText);

        addView(newRow);
        return inputText;
    }
}
