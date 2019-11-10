package com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewStockPresenter;
import com.peter.wagstaff.hytechlogger.customFragments.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;

public class NewStockEntryPresenter extends NewDataEntryPresenter {

    private LocationRadioButton rackButton, otherButton;

    @Override
    ItemType getType() { return StockType.getInstance(); }

    @Override
    Intent nextIntent() {
        return new Intent(NewStockEntryPresenter.this, ViewStockPresenter.class);
    }
}
