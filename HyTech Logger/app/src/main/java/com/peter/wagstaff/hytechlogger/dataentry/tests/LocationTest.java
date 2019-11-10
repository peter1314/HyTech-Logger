package com.peter.wagstaff.hytechlogger.dataentry.tests;

import java.util.Map;
import java.util.Set;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;

public class LocationTest extends DataEntryTest {

    private String key;
    private Set<Map<String, Object>> validConfigs;

    public LocationTest(String key, Set<Map<String, Object>> validLocations) {
        this.key = key;
        this.validConfigs = validLocations;
    }

    @Override
    public boolean TestDataEntry(DataEntry dataEntry) {

        Location location = LocationBuilder.buildLocation(dataEntry.getData(key));

        for(Map<String, Object> validConfig: validConfigs) {
            if(checkCompliance(location, validConfig)) { return true; }
        }
        return false;
    }

    private boolean checkCompliance(Location location, Map<String, Object> validConfig) {

        for(String requiredTag: validConfig.keySet()) {
            if(!requiredTag.equals("config_name")) {
                //check if the required tag is in the location
                if(!location.hasTag(requiredTag)) { return false; }

                //check if the tag has a required value
                if(validConfig.get(requiredTag) != null) {
                    if(!location.getTagValue(requiredTag).equals(validConfig.get(requiredTag))) { return false; }
                }
            }
        }
        return true;
    }
}
