package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;

//TableLayout used to display or enter an undefined number of Attributes
public class AttributeTable<InputText extends EditText> extends TableLayout  {

    //Inflater used to inflate views
    LayoutInflater inflater;

    //Reference to the layout which defines the EditText of each row
    int ref;

    /**
     * Declares AttributeTable
     * @param context
     */
    public AttributeTable(Context context) {
        super(context);
    }

    /**
     * Declares AttributeTable
     * @param context
     * @param attrs
     */
    public AttributeTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Declares AttributeTable
     * @param inflater
     * @param ref
     */
    public void setTools(LayoutInflater inflater, int ref) {
        this.inflater = inflater;
        this.ref = ref;
    }

    /**
     * Adds a row representing an Attribute to the Table
     * @param attribute The Attribute to add
     * @return The EditText created
     */
    public InputText addRow(Attribute attribute) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView label = (TextView) inflater.inflate(R.layout.fragment_input_label, null);
        label.setText(attribute.DISPLAY);
        newRow.addView(label);

        InputText inputText = (InputText) inflater.inflate(ref, null);
        inputText.setInputType(attribute.INPUT_TYPE);
        inputText.setText(attribute.DEFAULT);
        newRow.addView(inputText);

        addView(newRow);
        return inputText;
    }

    /**
     * Add a row representing an Attribute with only the Attributes name
     * @param name Name of the Attribute to add
     * @return The EditText created
     */
    public InputText addRow(String name) {
        //create placeholder default Attribute with the specified name
        return addRow(new Attribute("", name, "", InputType.TYPE_CLASS_TEXT));
    }
}
