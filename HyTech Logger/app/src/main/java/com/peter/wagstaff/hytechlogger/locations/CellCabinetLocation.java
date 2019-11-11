package com.peter.wagstaff.hytechlogger.locations;

import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import org.json.JSONException;

//Represents a location within the cell cabinet, this is a specific cabinet which has 9 shelves
public class CellCabinetLocation extends Location {

    //Value of the special type tag for an CellCabinetLocation
    public static final String TYPE = "cell_cabinet";

    //Number of cabinets in the shelf
    public static final int SHELF_COUNT = 9;

    //Array of the 9 possible locations within a cabinet
    public static final String[] OPTIONS = getStaticOptions();

    /**
     * Declares a CellCabinetLocation
     */
    public CellCabinetLocation() {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
    }

    /**
     * Create a new CellCabinetLocation from a Location as a JSON String
     * The JSON String should represent a CellCabinetLocation
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public CellCabinetLocation(String locationAsJSONString) throws JSONException {
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
        return "Cabinet: Shelf " + tags.get("shelf");
    }

    /**
     * Used to initialize the options of a CellCabinetLocation, based on the number of shelves
     * @return The options of a CellCabinetLocation
     */
    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SHELF_COUNT];
        for(int i = 0; i < SHELF_COUNT; i++) {
            optionArray[i] = "Shelf " + (i + 1);
        }
        return optionArray;
    }
}
