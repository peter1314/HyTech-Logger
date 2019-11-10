package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;

public class SelectTypeViewPresenter extends SelectTypePresenter {

    @Override
    Intent nextIntent() {
        return new Intent(SelectTypeViewPresenter.this, ViewItemsPresenter.class);
    }
}
