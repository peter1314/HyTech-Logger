package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;

//Presenter for selecting an ItemType and then progressing to viewing items of those types
public class SelectTypeViewActivity extends SelectTypeActivity {

    @Override
    Intent nextIntent() {
        return new Intent(SelectTypeViewActivity.this, ViewItemsActivity.class);
    }
}
