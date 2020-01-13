package com.peter.wagstaff.hytechlogger.locations;

import android.os.Build;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Represents a location using a map of tags and values
public abstract class Location {

    //Map to store tags and values which represent the Location, each tag is a dimension a location exists in
    Map<String, Object> tags;
    //The key for the type tag, which stores what type of Location a Location is
    public static final String TYPE_KEY = "type";

    /**
     * Declare a Location
     */
    public Location() {
        tags = new HashMap();
    }

    /**
     * Create a new Location from a Location as a JSON String
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public Location(String locationAsJSONString) throws JSONException {
        this();
        //Creates a JSONObject which represents the Location
        JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
        //Add each element of the JSONObject as a tag in the String
        Iterator<String> jsonKeys = locationAsJSON.keys();
        while(jsonKeys.hasNext()) {
            String key = jsonKeys.next();
            tags.put(key, locationAsJSON.get(key));
        }
    }


    /**
     * Initializes and existing Location from a Location as a JSON String
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public void initializeFromJSON(String locationAsJSONString) throws JSONException {
        //Creates a JSONObject which represents the Location
        JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
        //Add each element of the JSONObject as a tag in the String
        Iterator<String> jsonKeys = locationAsJSON.keys();
        while(jsonKeys.hasNext()) {
            String key = jsonKeys.next();
            tags.put(key, locationAsJSON.get(key));
        }
    }

    /**
     * @param tag Tag to search for
     * @return If the Location has a tag
     */
    public boolean hasTag(String tag) { return tags.containsKey(tag); }

    /**
     * @param tag Tag to seach for
     * @return The value of the tag, null if the Location does not have such a tag
     */
    public Object getTagValue(String tag) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return tags.getOrDefault(tag, null);
        } else {
            return tags.get(tag);
        }
    }

    /**
     * Gets the value of the special type tag
     * @return The Type of the location
     */
    public String getType() { return tags.get(TYPE_KEY).toString(); }

    /**
     * Sets tags in Location from a string associated with a location spinner
     * @param input text of a selection from a location spinner
     */
    public abstract void addSpinnerInput(String input);

    /**
     * @return The options for a location spinner associated with this Location
     */
    public abstract String[] getOptions();

    /**
     * @return The position of this Locations current option within all options
     */
    public abstract int getCurrentOption();

    /**
     * @return A readable textual representation of this Location
     */
    public abstract String fancyPrint();

    /**
     * Locations are stored in the database as JSONObjects
     * @return A JSONObject representing this Location
     */
    public JSONObject toJSON() {
        JSONObject locationAsJSON = new JSONObject();
        //Add each tag and its value in the location to the JSONObject
        for (String line: tags.keySet()) {
            try {
                locationAsJSON.put(line, tags.get(line));
            } catch (JSONException e) {}
        }
        return locationAsJSON;
    }

    /**
     * Useful for debugging
     * @return A textual representation of the Location
     */
    @Override
    public String toString() {
        String asString = "{";

        for (String line: tags.keySet()) {
            asString += line + "=" + tags.get(line) + ", ";
        }
        if(asString.length() != 1) {
            asString = asString.substring(0, asString.length() - 2);
        }
        return asString + "}";
    }

    /**
     * Creates a location from a JSONObject as a String
     * The dynamic type of the location is determined by the special type tag of the location
     * THIS METHOD MUST BE UPDATED IF A NEW LOCATION CLASS IS ADDED
     * @param locationAsJSONString Location represented as a JSONObject as a String
     * @return A new Location
     */
    public static Location buildLocation(String locationAsJSONString) {
        try {
            JSONObject locationAsJSON = new JSONObject(locationAsJSONString);

            //Stores the value of the special type key of the Location being created
            String type = locationAsJSON.getString(Location.TYPE_KEY);

            //Go through all LocationConfigurations
            for(LocationConfiguration configuration: LocationConfigurations.LOCATION_CONFIGURATIONS) {
                if(type.equals(configuration.ASSOCIATED_LOCATION.getType())) {
                    //Initialize its associated location with the JSONObject
                    configuration.ASSOCIATED_LOCATION.initializeFromJSON(locationAsJSONString);
                    return configuration.ASSOCIATED_LOCATION;
                }
            }
            return null;

        } catch (JSONException e) {
            return null;
        }
    }

    public static Location LocationFromJSON(String locationAsJSONString) {
        try {
            JSONObject locationASJSON = new JSONObject(locationAsJSONString);
            String type = locationASJSON.getString(TYPE_KEY);

            for(LocationConfiguration configuration: LocationConfigurations.LOCATION_CONFIGURATIONS) {
                if(type.equals(configuration.ASSOCIATED_LOCATION.getType())) {
                    configuration.ASSOCIATED_LOCATION.initializeFromJSON(locationAsJSONString);
                }
                return configuration.ASSOCIATED_LOCATION;
            }
            return null;
        } catch (JSONException e) {
            return null;
        }
    }
}
