package com.peter.wagstaff.hytechlogger.activities.viewItemPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters.NewCellEntryPresenter;
import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;

public class ViewCellPresenter extends ViewDataPresenter {

    @Override
    ItemType getType() { return CellType.getInstance(); }

    @Override
    Intent nextIntent() {
        return new Intent(ViewCellPresenter.this, NewCellEntryPresenter.class);
    }
}
