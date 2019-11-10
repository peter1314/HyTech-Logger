package com.peter.wagstaff.hytechlogger.activities.NewDataEntryActivities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.ViewDataActivities.ViewStockActivity;
import com.peter.wagstaff.hytechlogger.customviews.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.StockDataEntry;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;
import com.peter.wagstaff.hytechlogger.location.RackLocation;

public class NewStockEntryActivity extends NewDataEntryActivity {

    private LocationRadioButton rackButton, otherButton;

    @Override
    int getContentView() {
        return R.layout.activity_new_data_entry;
    }

    @Override
    DataEntry getEntry() { return new StockDataEntry(); }

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
    void setLocationButtons() {
        locationRadioButtions.clear();
        rackButton = new LocationRadioButton(this, "Rack", RackLocation.OPTIONS);
        otherButton = new LocationRadioButton(this, "Other", OtherLocation.OPTIONS);

        locationRadioButtions.add(rackButton);
        locationRadioButtions.add(otherButton);
    }

    @Override
    void updateLocationButtons(Location location) {
        if(location.getType().equals(RackLocation.TYPE)) {
            rackButton.setChecked(true);
        } else if(location.getType().equals(OtherLocation.TYPE)) {
            otherButton.setChecked(true);
        }
    }
}
