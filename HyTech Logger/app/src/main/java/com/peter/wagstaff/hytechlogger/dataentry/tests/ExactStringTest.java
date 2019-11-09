package com.peter.wagstaff.hytechlogger.dataentry.tests;

import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;

public class ExactStringTest extends DataEntryTest {

    private String key;
    private String value;

    public ExactStringTest(String key, String value) {
        this.key = key;
        this.value = value.toLowerCase();
    }

    @Override
    public boolean TestDataEntry(DataEntry dataEntry) {
        String entryValue = dataEntry.getData(key).toLowerCase();
        return value.equals(entryValue);
    }
}
