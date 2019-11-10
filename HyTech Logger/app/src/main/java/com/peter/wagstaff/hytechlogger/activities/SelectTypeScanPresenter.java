package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;

public class SelectTypeScanPresenter extends SelectTypePresenter {

    @Override
    Intent nextIntent() {
        return new Intent(SelectTypeScanPresenter.this, ScanItemPresenter.class);
    }
}
