package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.StockDataEntry;

public class ViewStockActivity extends ViewDataActivity {

    @Override
    int getContentView() {
        return R.layout.activity_view_data;
    }

    @Override
    String getType() {
        return StockDataEntry.CODE.DISPLAY;
    }

    @Override
    String getBranch() {
        return StockDataEntry.BRANCH;
    }

    @Override
    Attribute[] getRowAttributes() {
        return StockDataEntry.ROW_ATTRIBUTES;
    }

    @Override
    Intent nextIntent() {
        return new Intent(ViewStockActivity.this, NewStockEntryActivity.class);
    }
}
