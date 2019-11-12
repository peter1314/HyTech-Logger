package com.peter.wagstaff.hytechlogger.locations;

import org.json.JSONException;

//Represents a location within a rack, this is a specific stock rack
public class RackLocation extends Location {

    //Value of the special type tag for an RackLocation
    public static final String TYPE = "rack";

    //Array of the possible rack locations
    public static final String[] OPTIONS = getStaticOptions();

    /**
     * Declares a RackLocation
     */
    public RackLocation() {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
    }

    /**
     * Create a new RackLocation from a Location as a JSON String
     * The JSON String should represent a RackLocation
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public RackLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) {
        tags.put("rack", input);
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = tags.get("rack").toString();
        for(int i = 0; i < OPTIONS.length; i++) {
            if(currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return tags.get("rack").toString();
    }

    /**
     * Used to initialize the options of a RackLocation
     * @return The options of a RackLocation
     */
    private static String[] getStaticOptions() {
        String[] optionArray  = {"Rack Top", "Rack Bottom", "Rack Side", "Rack Other"};
        return optionArray;
    }
}
