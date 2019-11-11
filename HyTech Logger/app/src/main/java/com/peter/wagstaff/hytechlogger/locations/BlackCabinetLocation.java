package com.peter.wagstaff.hytechlogger.locations;

import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import org.json.JSONException;

//Represents a location within the black tool cabinet, this is a specific cabinet
public class BlackCabinetLocation extends Location {

    //Value of the special type tag for an CellCabinetLocation
    public static final String TYPE = "black_cabinet";

    //Number of cabinets in the shelf
    public static final int SHELF_COUNT = 7;

    //Array of the 9 possible locations within a cabinet
    public static final String[] OPTIONS = getStaticOptions();

    /**
     * Declares a BlackCabinetLocation
     */
    public BlackCabinetLocation() {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
    }

    /**
     * Create a new BlackCabinetLocation from a Location as a JSON String
     * The JSON String should represent a BlackCabinetLocation
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public BlackCabinetLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) { tags.put("shelf", InputFormatting.intFromString(input)); }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = "Shelf " + tags.get("shelf");
        for(int i = 0; i < OPTIONS.length; i++) {
            if(currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return "Black Cab: Shelf " + tags.get("shelf");
    }

    /**
     * Used to initialize the options of a BlackCabinetLocation, based on the number of shelves
     * @return The options of a BlackCabinetLocation
     */
    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SHELF_COUNT];
        for(int i = 0; i < SHELF_COUNT; i++) {
            optionArray[i] = "Shelf " + (i + 1);
        }
        return optionArray;
    }
}
