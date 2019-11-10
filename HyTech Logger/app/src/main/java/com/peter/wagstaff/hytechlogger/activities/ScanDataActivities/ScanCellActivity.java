package com.peter.wagstaff.hytechlogger.activities.ScanDataActivities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.NewDataEntryActivities.NewCellEntryActivity;
import com.peter.wagstaff.hytechlogger.activities.ViewDataActivities.ViewCellActivity;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;

public class ScanCellActivity extends ScanDataActivity {

    @Override
    public String getBranch() {
        return CellDataEntry.BRANCH;
    }

    @Override
    public Intent getDataExistsIntent() {
        return new Intent(ScanCellActivity.this, ViewCellActivity.class);
    }

    @Override
    public Intent getDataNewIntent() {
        return new Intent(ScanCellActivity.this, NewCellEntryActivity.class);
    }

}
