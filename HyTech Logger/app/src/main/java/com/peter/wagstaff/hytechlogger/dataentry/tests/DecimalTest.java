package com.peter.wagstaff.hytechlogger.dataentry.tests;

import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;

public class DecimalTest extends DataEntryTest {

    private String key;
    private double min, max;

    public DecimalTest(String key, double min, double max) {
        this.key = key;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean TestDataEntry(DataEntry dataEntry) {
        Double value = Double.parseDouble(dataEntry.getData(key));

        if(min != 0 && value <= min) {
            return false;
        } else if(max != 0 && value >= max) {
            return false;
        }
        return true;
    }
}
