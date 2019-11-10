package com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewCellPresenter;
import com.peter.wagstaff.hytechlogger.customFragments.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;

public class NewCellEntryPresenter extends NewDataEntryPresenter {

    private LocationRadioButton cabinetButton, ht04Button, ht05Button, otherButton;

    @Override
    ItemType getType() {
        return CellType.getInstance();
    }

    @Override
    Intent nextIntent() { return new Intent(NewCellEntryPresenter.this, ViewCellPresenter.class); }
}
