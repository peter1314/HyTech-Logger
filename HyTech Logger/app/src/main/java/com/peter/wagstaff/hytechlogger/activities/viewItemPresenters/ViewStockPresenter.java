package com.peter.wagstaff.hytechlogger.activities.viewItemPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters.NewStockEntryPresenter;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;

public class ViewStockPresenter extends ViewDataPresenter {

    @Override
    ItemType getType() { return StockType.getInstance(); }

    @Override
    Intent nextIntent() {
        return new Intent(ViewStockPresenter.this, NewStockEntryPresenter.class);
    }
}
