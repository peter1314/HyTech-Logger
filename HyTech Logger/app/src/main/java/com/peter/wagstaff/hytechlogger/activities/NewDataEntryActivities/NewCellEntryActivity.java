package com.peter.wagstaff.hytechlogger.activities.NewDataEntryActivities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.ViewDataActivities.ViewCellActivity;
import com.peter.wagstaff.hytechlogger.customviews.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.location.AccumulatorLocation;
import com.peter.wagstaff.hytechlogger.location.CabinetLocation;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;

public class NewCellEntryActivity extends NewDataEntryActivity {

    private LocationRadioButton cabinetButton, ht04Button, ht05Button, otherButton;

    @Override
    int getContentView() {
        return R.layout.activity_new_data_entry;
    }

    @Override
    DataEntry getEntry() { return new CellDataEntry(); }

    @Override
    Intent nextIntent() { return new Intent(NewCellEntryActivity.this, ViewCellActivity.class); }

    @Override
    Location buildLocation() {
        Location cellLocation;

        if(cabinetButton.isChecked()) {
            cellLocation = new CabinetLocation();
        } else if(ht04Button.isChecked()) {
            cellLocation = new AccumulatorLocation(4);
        } else if(ht05Button.isChecked()) {
            cellLocation = new AccumulatorLocation(5);
        } else if(otherButton.isChecked()) {
            cellLocation = new OtherLocation();
        } else {
            return null;
        }

        cellLocation.addSpinnerInput(locationSpinner.getSelectedItem().toString());
        return cellLocation;
    }

    @Override
    void setLocationButtons() {
        locationRadioButtions.clear();
        cabinetButton = new LocationRadioButton(this, "Cabinet", CabinetLocation.OPTIONS);
        ht04Button = new LocationRadioButton(this, "HT04", AccumulatorLocation.OPTIONS);
        ht05Button = new LocationRadioButton(this, "HT05", AccumulatorLocation.OPTIONS);
        otherButton = new LocationRadioButton(this, "Other", OtherLocation.OPTIONS);

        locationRadioButtions.add(cabinetButton);
        locationRadioButtions.add(ht04Button);
        locationRadioButtions.add(ht05Button);
        locationRadioButtions.add(otherButton);
    }

    @Override
    void updateLocationButtons(Location location) {
        if(location.getType().equals(CabinetLocation.TYPE)) {
            cabinetButton.setChecked(true);
        } else if(location.getType().equals(AccumulatorLocation.TYPE)) {
            if(((AccumulatorLocation) location).getIteration() == 4) {
                ht04Button.setChecked(true);
            } else if(((AccumulatorLocation) location).getIteration() == 5) {
                ht05Button.setChecked(true);
            }
        } else if(location.getType().equals(OtherLocation.TYPE)) {
            otherButton.setChecked(true);
        }
    }
}
