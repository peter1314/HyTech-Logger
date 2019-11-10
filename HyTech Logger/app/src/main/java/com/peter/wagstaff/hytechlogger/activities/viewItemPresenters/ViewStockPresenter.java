package com.peter.wagstaff.hytechlogger.activities.viewItemPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters.NewStockEntryPresenter;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.StockEntry;

public class ViewStockPresenter extends ViewDataPresenter {

    @Override
    int getContentView() {
        return R.layout.activity_view_data;
    }

    @Override
    String getType() {
        return StockEntry.CODE.DISPLAY;
    }

    @Override
    String getBranch() {
        return StockEntry.BRANCH;
    }

    @Override
    Attribute[] getRowAttributes() {
        return StockEntry.ROW_ATTRIBUTES;
    }

    @Override
    Intent nextIntent() {
        return new Intent(ViewStockPresenter.this, NewStockEntryPresenter.class);
    }
}
