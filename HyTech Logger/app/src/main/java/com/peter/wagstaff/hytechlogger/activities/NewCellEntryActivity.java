package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
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
    String getType() { return CellDataEntry.CODE.DISPLAY; }

    @Override
    String getBranch() { return CellDataEntry.BRANCH; }

    @Override
    Attribute[] getRowAttributes() { return CellDataEntry.ROW_ATTRIBUTES; }

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
    void addLocationButtons() {
        cabinetButton = findViewById(R.id.radioButton_0);
        cabinetButton.setOptions(CabinetLocation.OPTIONS);
        locationRadioButtions.add(cabinetButton);

        ht04Button = findViewById(R.id.radioButton_1);
        ht04Button.setOptions(AccumulatorLocation.OPTIONS);
        locationRadioButtions.add(ht04Button);

        ht05Button = findViewById(R.id.radioButton_2);
        ht05Button.setOptions(AccumulatorLocation.OPTIONS);
        locationRadioButtions.add(ht05Button);

        otherButton = findViewById(R.id.radioButton_3);
        otherButton.setOptions(OtherLocation.OPTIONS);
        locationRadioButtions.add(otherButton);
    }

    @Override
    void updateLocationButtons(Location location) {
        if(location.getType().equals("cabinet")) {
            cabinetButton.setChecked(true);
        } else if(location.getType().equals("accumulator")) {
            if(((AccumulatorLocation) location).getIteration() == 4) {
                ht04Button.setChecked(true);
            } else if(((AccumulatorLocation) location).getIteration() == 5) {
                ht05Button.setChecked(true);
            }
        } else if(location.getType().equals("other")) {
            otherButton.setChecked(true);
        }
    }
}
