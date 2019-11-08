package com.peter.wagstaff.hytechlogger.location;

import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import org.json.JSONException;

public class CabinetLocation extends Location {

    public static final int SHELF_COUNT = 9;
    public static final String[] OPTIONS = getStaticOptions();

    public CabinetLocation() {
        super();
        tags.put("type", "cabinet");
    }

    public CabinetLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) { tags.put("shelf", InputFormating.intFromString(input)); }

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

    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SHELF_COUNT];
        for(int i = 0; i < SHELF_COUNT; i++) {
            optionArray[i] = "Shelf " + (i + 1);
        }
        return optionArray;
    }
}
