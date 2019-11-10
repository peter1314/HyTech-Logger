package com.peter.wagstaff.hytechlogger.activities.scanItemPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters.NewStockEntryPresenter;
import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewStockPresenter;
import com.peter.wagstaff.hytechlogger.itemEntry.StockEntry;

public class ScanStockPresenter extends ScanDataPresenter {

    @Override
    public String getBranch() {
        return StockEntry.BRANCH;
    }

    @Override
    public Intent getDataExistsIntent() {
        return new Intent(ScanStockPresenter.this, ViewStockPresenter.class);
    }

    @Override
    public Intent getDataNewIntent() {
        return new Intent(ScanStockPresenter.this, NewStockEntryPresenter.class);
    }
}
