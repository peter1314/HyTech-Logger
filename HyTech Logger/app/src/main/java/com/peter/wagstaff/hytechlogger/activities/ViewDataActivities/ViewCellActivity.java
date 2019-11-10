package com.peter.wagstaff.hytechlogger.activities.ViewDataActivities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.NewDataEntryActivities.NewCellEntryActivity;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;

public class ViewCellActivity extends ViewDataActivity {

    @Override
    int getContentView() {
        return R.layout.activity_view_data;
    }

    @Override
    String getType() {
        return CellDataEntry.CODE.DISPLAY;
    }

    @Override
    String getBranch() {
        return CellDataEntry.BRANCH;
    }

    @Override
    Attribute[] getRowAttributes() {
        return CellDataEntry.ROW_ATTRIBUTES;
    }

    @Override
    Intent nextIntent() {
        return new Intent(ViewCellActivity.this, NewCellEntryActivity.class);
    }
}
