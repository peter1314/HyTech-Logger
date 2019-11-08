package com.peter.wagstaff.hytechlogger.dataentry.tests;

import java.util.Set;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;

public class LocationTest extends DataEntryTest {

    private String key;
    private Set<String> validLocations;
    public LocationTest(String key, Set<String> validLocations) {
        this.key = key;
        this.validLocations = validLocations;
    }

    @Override
    public boolean TestDataEntry(DataEntry dataEntry) {
        String value = dataEntry.getData(key);

        for(String location: validLocations) {
            if(value.contains(location)) {
                return true;
            }
        }
        return false;
    }
}
