package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import java.util.HashSet;
import java.util.Set;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.locations.Location;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;

//AttributeTest for testing if a Location Attribute of an ItemEntry is compliant with a Set of location configurations
public class LocationTest extends AttributeTest {

    //Set of valid LocationConfigurations
    private final Set<LocationConfiguration> VALID_CONFIGS;

    /**
     * Declare LocationTest with a single of LocationConfiguration
     * @param key Key of Attribute to test, should be a Location Attribute
     * @param validLocationConfig LocationConfiguration to test against
     */
    public LocationTest(String key, LocationConfiguration validLocationConfig) {
        super(key);
        VALID_CONFIGS = new HashSet<>();
        VALID_CONFIGS.add(validLocationConfig);
    }

    /**
     * Declare LocationTest with a set of LocationConfigurations
     * @param key Key of Attribute to test, should be a Location Attribute
     * @param validLocationConfigs Set of LocationConfigurations to test against
     */
    public LocationTest(String key, Set<LocationConfiguration> validLocationConfigs) {
        super(key);
        VALID_CONFIGS = validLocationConfigs;
    }

    /**
     * Tests a Location
     * @param location Location to test
     * @return If the Location is complaint with at least one of the LocationTest's LocationConfigurations
     */
    public boolean testLocation(Location location) {
        for(LocationConfiguration validConfig: VALID_CONFIGS) {
            if(validConfig.checkCompliance(location)) { return true; }
        }
        return false;
    }

    @Override
    public double testDataEntry(ItemEntry itemEntry) {
        if(testLocation(Location.buildLocation(itemEntry.getData(KEY)))) return 1;
        return -1;

    }
}
