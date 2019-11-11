package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import androidx.core.content.ContextCompat;

//TableLayout used to display and select an undefined number of Buttons
//Each button is associated with an Object of Type
public class ButtonTable<Type> extends TableLayout  {

    //Listener action to specify the behavior of each Button
    private ListnerAction<Type> action;

    /**
     * Declare ButtonTable
     * @param context
     */
    public ButtonTable(Context context) {
        super(context);
    }

    /**
     * Declare ButtonTable
     * @param context
     * @param attrs
     */
    public ButtonTable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the action that will occur when a Button is pressed
     * @param action
     */
    public void setListnerAction(ListnerAction<Type> action) {
        this.action = action;
    }

    /**
     * Adds a row with a button storing an Object of type
     * @param label Text to be displayed on the Button
     * @param buttonData Object of Type associated with the Button
     */
    public void addRow(String label, final Type buttonData) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        Button newButton = new Button(getContext());
        newButton.setText(label);
        newButton.setWidth(Resources.getSystem().getDisplayMetrics().widthPixels);
        newButton.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
        newButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pure_white));

        newButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                action.doAction(buttonData);
            }
        });

        newRow.addView(newButton);
        addView(newRow);
    }
}
