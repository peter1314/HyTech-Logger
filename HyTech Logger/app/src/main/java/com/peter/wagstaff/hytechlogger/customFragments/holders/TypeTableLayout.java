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
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import androidx.core.content.ContextCompat;

public class TypeTableLayout extends TableLayout  {

    private ListnerAction<ItemType> action;

    public TypeTableLayout(Context context) {
        super(context);
    }

    public TypeTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListnerAction(ListnerAction<ItemType> action) {
        this.action = action;
    }

    public void addRow(final ItemType type) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        Button itemButton = new Button(getContext());
        itemButton.setText(type.NAME);
        itemButton.setWidth(Resources.getSystem().getDisplayMetrics().widthPixels);
        itemButton.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
        itemButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pure_white));

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.doAction(type);
            }
        });

        newRow.addView(itemButton);
        addView(newRow);
    }
}
