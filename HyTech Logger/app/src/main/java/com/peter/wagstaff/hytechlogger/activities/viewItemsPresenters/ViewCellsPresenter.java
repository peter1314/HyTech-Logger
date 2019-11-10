package com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters;

import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewCellPresenter;
import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import android.content.Intent;

public class ViewCellsPresenter extends ViewDatasPresenter {

    @Override
    ItemType getType() { return CellType.getInstance(); }

    @Override
    Intent getSelectIntent() {
        return new Intent(ViewCellsPresenter.this, ViewCellPresenter.class);
    }
}
