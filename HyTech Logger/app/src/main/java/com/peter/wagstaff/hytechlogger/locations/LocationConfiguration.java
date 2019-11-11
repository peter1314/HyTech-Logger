package com.peter.wagstaff.hytechlogger.locations;

import java.util.HashMap;

//Represents a location configuration, which a Location can be complaint with
public class LocationConfiguration extends HashMap<String, Object> {

    //Name of the LocationConfiguration
    public final String NAME;
    //Location associated with this Configuration
    //Each LocationConfiguration is associated with on type of Location but can test any Location
    public final Location ASSOCIATED_LOCATION;

    /**
     * Declare LocationConfiguration
     * @param name Name of LocationConfiguration
     */
    private LocationConfiguration(String name, Location associatedLocation) {
        super();
        NAME = name;
        ASSOCIATED_LOCATION = associatedLocation;
    }

    /**
     * Checks if a Location is complaint with this LocationConfiguration
     * @param location Location to check
     * @return If the location is complaint
     */
    public boolean checkCompliance(Location location) {
        for(String requiredTag: keySet()) {
            //check if the required tag is in the location
            if(!location.hasTag(requiredTag)) { return false; }

            //check if the tag has a required value
            if(get(requiredTag) != null) {
                if(!location.getTagValue(requiredTag).equals(get(requiredTag))) { return false; }
            }
        }
        return true;
    }

    /**
     * Creates a location config, which consists of tags and values
     * Location configs are used to test and filter locations
     * @param name Name of the config
     * @param associatedLocation Location object associated with this LocationConfiguration
     * @param tags Array of tags
     * @param values Array of corresponding values, each tag must have a value
     * @return A Map of tags and values which represents a location config
     */
    public static LocationConfiguration buildLocationConfig(String name, Location associatedLocation, String[] tags, Object[] values) {
        //Check that tags are paired with values
        if(tags.length != values.length) return null;

        //Tags and values are linked in a map
        LocationConfiguration validConfig = new LocationConfiguration(name, associatedLocation);

        //Add special type tag
        validConfig.put(Location.TYPE_KEY, associatedLocation.getType());

        //Add all other tags and values
        for(int i = 0; i < tags.length; i++) {
            validConfig.put(tags[i], values[i]);
        }
        return  validConfig;
    }

    /**
     * Creates a location config with no tags other than the type tag
     * @param name Name of the config
     * @param associatedLocation Location object associated with this LocationConfiguration
     * @return A Map of tags and values which represents a location config
     */
    public static LocationConfiguration buildLocationConfig(String name, Location associatedLocation) {
        return buildLocationConfig(name, associatedLocation, new String[]{}, new Object[]{});
    }
}
