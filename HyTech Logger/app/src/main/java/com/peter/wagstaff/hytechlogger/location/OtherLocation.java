package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;

public class OtherLocation extends Location {

    public static final String TYPE = "other";
    public static final String[] OPTIONS = getStaticOptions();

    public OtherLocation() {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
    }

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

    private static String[] getStaticOptions() {
        String[] optionArray  = {"Shop Space", "Lost", "Other"};
        return optionArray;
    }
}
