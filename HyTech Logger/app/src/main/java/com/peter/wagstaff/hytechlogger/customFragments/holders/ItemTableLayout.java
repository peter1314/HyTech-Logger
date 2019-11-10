package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import androidx.core.content.ContextCompat;

public class ItemTableLayout extends TableLayout  {

    private ListnerAction<ItemEntry> action;

    public ItemTableLayout(Context context) {
        super(context);
    }

    public ItemTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListnerAction(ListnerAction<ItemEntry> action) {
        this.action = action;
    }

    public void addRow(final ItemEntry entry) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        Button typeButton = new Button(getContext());
        typeButton.setText(entry.getType().NAME + " " + entry.getData(Attributes.CODE.KEY));
        typeButton.setWidth(getWidth());
        typeButton.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
        typeButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pure_white));

        typeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.doAction(entry);
            }
        });

        newRow.addView(typeButton);
        addView(newRow);
    }
}
