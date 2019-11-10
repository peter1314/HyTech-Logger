package com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewStockPresenter;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;

public class ViewStocksPresenter extends ViewDatasPresenter {

    @Override
    ItemType getType() {
        return StockType.getInstance();
    }

    @Override
    Intent getSelectIntent() {
        return new Intent(ViewStocksPresenter.this, ViewStockPresenter.class);
    }
}
