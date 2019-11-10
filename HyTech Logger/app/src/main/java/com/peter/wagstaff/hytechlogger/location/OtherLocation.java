package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;

//Represents an unspecified or vague location
public class OtherLocation extends Location {

    //Value of the special type tag for an OtherLocation
    public static final String TYPE = "other";

    //Array of the possible other locations
    public static final String[] OPTIONS = getStaticOptions();

    /**
     * Declares a OtherLocation
     */
    public OtherLocation() {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
    }

    /**
     * Create a new OtherLocation from a Location as a JSON String
     * The JSON String should represent an OtherLocation
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public OtherLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) {
        tags.put("other", input);
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = tags.get("other").toString();
        for(int i = 0; i < OPTIONS.length; i++) {
            if(currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return tags.get("other").toString();
    }

    /**
     * Used to initialize the options of an OtherLocation
     * @return The options of an OtherLocation
     */
    private static String[] getStaticOptions() {
        String[] optionArray  = {"Shop Space", "Lost", "Other"};
        return optionArray;
    }
}
