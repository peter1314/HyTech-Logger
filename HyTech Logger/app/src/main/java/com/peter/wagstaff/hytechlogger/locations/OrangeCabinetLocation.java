package com.peter.wagstaff.hytechlogger.locations;

import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import org.json.JSONException;

//Represents a location within the orange tool cabinet, this is a specific cabinet
public class OrangeCabinetLocation extends Location {

    //Value of the special type tag for an CellCabinetLocation
    public static final String TYPE = "orange_cabinet";

    //Number of rows of shelves and shelves per row in the cabinet
    public static final int ROW_COUNT = 3;
    public static final int ROW_SIZE = 7;

    //Array of the 9 possible locations within a cabinet
    public static final String[] OPTIONS = getStaticOptions();

    /**
     * Declares a OrangeCabinetLocation
     */
    public OrangeCabinetLocation() {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
    }

    /**
     * Create a new OrangeCabinetLocation from a Location as a JSON String
     * The JSON String should represent a OrangeCabinetLocation
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public OrangeCabinetLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) {
        String[] splitInput = input.split(",");
        tags.put("row", InputFormatting.intFromString(splitInput[0]));
        tags.put("shelf", InputFormatting.intFromString(splitInput[1]));
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = "Row " + tags.get("row") + ", Shelf " + tags.get("shelf");
        for(int i = 0; i < OPTIONS.length; i++) {
            if (currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return "Orange Cab" + ": R" + tags.get("row") + ", S" + tags.get("shelf");
    }

    /**
     * Used to initialize the options of a OrangeCabinetLocation, based on the number of shelves
     * @return The options of a OrangeCabinetLocation
     */
    private static String[] getStaticOptions() {
        String[] optionArray  = new String[ROW_COUNT * ROW_SIZE];
        for(int i = 0; i < ROW_COUNT * ROW_SIZE; i++) {
            optionArray[i] = "Row " + (i / ROW_SIZE + 1) + ", Shelf " + ((i % ROW_SIZE) + 1);
        }
        return optionArray;
    }
}
