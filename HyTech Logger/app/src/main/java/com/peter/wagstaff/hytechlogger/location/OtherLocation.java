package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;
import org.json.JSONObject;

public class OtherLocation extends Location {

    private String other;
    public static final String[] OPTIONS = getStaticOptions();

    public OtherLocation() {
        type = "other";
    }

    public OtherLocation(String locationAsJSONString) throws JSONException {
        this();

        JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
        other = locationAsJSON.getString("other");
    }

    @Override
    public void addSpinnerInput(String input) {
        other = input;
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    private static String[] getStaticOptions() {
        String[] optionArray  = {"Shop Space", "Lost", "Other"};
        return optionArray;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = other;
        for(int i = 0; i < OPTIONS.length; i++) {
            if(currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return other;
    }

    @Override
    public JSONObject toDict() {
        JSONObject locationAsJSON = new JSONObject();

        try {
            locationAsJSON.put("type", type);
            locationAsJSON.put("other", other);
        } catch (JSONException e) {}

        return locationAsJSON;
    }
}
