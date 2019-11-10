package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import java.util.Set;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

//AttributeTest for testing if a Location Attribute of an ItemEntry is compliant with a Set of location configurations
public class LocationTest extends AttributeTest {

    //Set of valid LocationConfigurations
    private final Set<LocationConfiguration> VALID_CONFIGS;

    /**
     * Declare LocationTest
     * @param key Key of Attribute to test, should be a Location Attribute
     * @param validLocations Set of LocationConfigurations to test against
     */
    public LocationTest(String key, Set<LocationConfiguration> validLocations) {
        super(key);
        this.VALID_CONFIGS = validLocations;
    }

    @Override
    public boolean TestDataEntry(ItemEntry itemEntry) {

        Location location = Location.buildLocation(itemEntry.getData(KEY));

        for(LocationConfiguration validConfig: VALID_CONFIGS) {
            if(validConfig.checkCompliance(location)) { return true; }
        }
        return false;
    }


}
