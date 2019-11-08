package com.peter.wagstaff.hytechlogger.location;

import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import org.json.JSONException;
import org.json.JSONObject;

public class CabinetLocation extends Location {

    private int shelf;
    public static final int SHELF_COUNT = 9;
    public static final String[] OPTIONS = getStaticOptions();

    public CabinetLocation() {
        type = "cabinet";
    }

    public CabinetLocation(String locationAsJSONString) throws JSONException {
        this();

        JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
        shelf = locationAsJSON.getInt("shelf");
        System.out.println("SHELF!!!: " + shelf);
    }

    @Override
    public void addSpinnerInput(String input) { shelf = InputFormating.intFromString(input); }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SHELF_COUNT];
        for(int i = 0; i < SHELF_COUNT; i++) {
            optionArray[i] = "Shelf " + (i + 1);
        }
        return optionArray;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = "Shelf " + shelf;
        for(int i = 0; i < OPTIONS.length; i++) {
            if(currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return "Cabinet: Shelf " + shelf;
    }

    @Override
    public JSONObject toDict() {
        JSONObject locationAsJSON = new JSONObject();

        try {
            locationAsJSON.put("type", type);
            locationAsJSON.put("shelf", shelf);
        } catch (JSONException e) {}

        return locationAsJSON;
    }
}
