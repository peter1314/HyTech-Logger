package com.peter.wagstaff.hytechlogger.activities.ScanDataActivities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.NewDataEntryActivities.NewStockEntryActivity;
import com.peter.wagstaff.hytechlogger.activities.ViewDataActivities.ViewStockActivity;
import com.peter.wagstaff.hytechlogger.dataentry.StockDataEntry;

public class ScanStockActivity extends ScanDataActivity {

    @Override
    public String getBranch() {
        return StockDataEntry.BRANCH;
    }

    @Override
    public Intent getDataExistsIntent() {
        return new Intent(ScanStockActivity.this, ViewStockActivity.class);
    }

    @Override
    public Intent getDataNewIntent() {
        return new Intent(ScanStockActivity.this, NewStockEntryActivity.class);
    }
}
