package com.peter.wagstaff.hytechlogger.activities.viewItemPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters.NewCellEntryPresenter;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.CellEntry;

public class ViewCellPresenter extends ViewDataPresenter {

    @Override
    int getContentView() {
        return R.layout.activity_view_data;
    }

    @Override
    String getType() {
        return CellEntry.CODE.DISPLAY;
    }

    @Override
    String getBranch() {
        return CellEntry.BRANCH;
    }

    @Override
    Attribute[] getRowAttributes() {
        return CellEntry.ROW_ATTRIBUTES;
    }

    @Override
    Intent nextIntent() {
        return new Intent(ViewCellPresenter.this, NewCellEntryPresenter.class);
    }
}
