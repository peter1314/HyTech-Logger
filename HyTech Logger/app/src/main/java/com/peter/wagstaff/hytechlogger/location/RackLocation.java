package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;

public class RackLocation extends Location {

    public static final String TYPE = "rack";
    public static final String[] OPTIONS = getStaticOptions();

    public RackLocation() {
        super();
        tags.put("type", TYPE);
    }

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

    private static String[] getStaticOptions() {
        String[] optionArray  = {"Rack Top", "Rack Bottom", "Rack Side"};
        return optionArray;
    }
}
