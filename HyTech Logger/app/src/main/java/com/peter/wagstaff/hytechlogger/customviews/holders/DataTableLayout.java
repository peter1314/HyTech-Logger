package com.peter.wagstaff.hytechlogger.customviews.holders;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customviews.ListnerAction;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import androidx.core.content.ContextCompat;

public class DataTableLayout extends TableLayout  {

    private ListnerAction<DataEntry> action;

    public DataTableLayout(Context context) {
        super(context);
    }

    public DataTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setListnerAction(ListnerAction<DataEntry> action) {
        this.action = action;
    }

    public void addRow(final DataEntry entry) {
        TableRow newRow = new TableRow(getContext());
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        Button dataButton = new Button(getContext());
        dataButton.setText(entry.getData(CellDataEntry.CODE.toString()));
        dataButton.setWidth(getWidth());
        dataButton.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
        dataButton.setTextColor(ContextCompat.getColor(getContext(), R.color.pure_white));

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.doAction(entry);
            }
        });

        newRow.addView(dataButton);
        addView(newRow);
    }
}
