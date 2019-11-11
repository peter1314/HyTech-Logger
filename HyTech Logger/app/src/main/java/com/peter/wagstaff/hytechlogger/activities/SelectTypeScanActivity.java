package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;

//Presenter for selecting an ItemType and then progressing to scanning items of those types
public class SelectTypeScanActivity extends SelectTypeActivity {

    @Override
    Intent nextIntent() {
        return new Intent(SelectTypeScanActivity.this, ScanItemActivity.class);
    }
}
