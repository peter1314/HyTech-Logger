package com.peter.wagstaff.hytechlogger.activities.scanItemPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters.NewCellEntryPresenter;
import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewCellPresenter;
import com.peter.wagstaff.hytechlogger.itemTypes.CellType;

public class ScanCellPresenter extends ScanDataPresenter {

    @Override
    public String getBranch() {
        return CellType.getInstance().BRANCH;
    }

    @Override
    public Intent getDataExistsIntent() {
        return new Intent(ScanCellPresenter.this, ViewCellPresenter.class);
    }

    @Override
    public Intent getDataNewIntent() {
        return new Intent(ScanCellPresenter.this, NewCellEntryPresenter.class);
    }

}
