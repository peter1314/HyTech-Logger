package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.StockDataEntry;
import com.peter.wagstaff.hytechlogger.location.CabinetLocation;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;
import com.peter.wagstaff.hytechlogger.location.RackLocation;

public class NewStockEntryActivity extends NewDataEntryActivity {

    private LocationRadioButton rackButton, otherButton;

    @Override
    int getContentView() {
        return R.layout.activity_new_stock_entry;
    }

    @Override
    String getType() { return StockDataEntry.CODE.DISPLAY; }

    @Override
    String getBranch() {
        return StockDataEntry.BRANCH;
    }

    @Override
    Attribute[] getRowAttributes() {
        return StockDataEntry.ROW_ATTRIBUTES;
    }

    @Override
    Intent nextIntent() {
        return new Intent(NewStockEntryActivity.this, ViewStockActivity.class);
    }

    @Override
    Location buildLocation() {
        Location stockLocation;

        if(rackButton.isChecked()) {
            stockLocation = new RackLocation();
        } else if(otherButton.isChecked()) {
            stockLocation = new OtherLocation();
        } else {
            return null;
        }

        stockLocation.addSpinnerInput(locationSpinner.getSelectedItem().toString());
        return stockLocation;
    }

    @Override
    void addLocationButtons() {
        rackButton = findViewById(R.id.radioButton_0);
        rackButton.setOptions(CabinetLocation.OPTIONS);
        locationRadioButtions.add(rackButton);

        otherButton = findViewById(R.id.radioButton_3);
        otherButton.setOptions(OtherLocation.OPTIONS);
        locationRadioButtions.add(otherButton);
    }

    @Override
    void updateLocationButtons(Location location) {

    }
}
