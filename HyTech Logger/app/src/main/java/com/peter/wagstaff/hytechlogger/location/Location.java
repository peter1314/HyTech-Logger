package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Location {

    String type;

    public Location() {
        type = "none";
    }

    public Location(String locationAsJSONString) throws JSONException {}

    public String getType() {return type;}

    public abstract void addSpinnerInput(String input);

    public abstract String[] getOptions();

    public abstract int getCurrentOption();

    public abstract String fancyPrint();

    public abstract JSONObject toDict();
}
