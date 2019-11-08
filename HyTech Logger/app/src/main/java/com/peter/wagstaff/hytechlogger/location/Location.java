package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Location {

    Map<String, Object> tags;

    public Location() {
        tags = new HashMap();
    }

    public Location(String locationAsJSONString) throws JSONException {
        this();

        JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
        Iterator<String> jsonKeys = locationAsJSON.keys();
        while(jsonKeys.hasNext()) {
            String key = jsonKeys.next();
            tags.put(key, locationAsJSON.get(key));
        }
    }

    public boolean hasTag(String tag) { return tags.containsKey(tag); }

    public Object getTagValue(String tag) { return tags.get(tag); }

    public String getType() { return tags.get("type").toString(); }

    public abstract void addSpinnerInput(String input);

    public abstract String[] getOptions();

    public abstract int getCurrentOption();

    public abstract String fancyPrint();

    public JSONObject toDict() {
        JSONObject locationAsJSON = new JSONObject();

        for (String line: tags.keySet()) {
            try {
                locationAsJSON.put(line, tags.get(line));
            } catch (JSONException e) {}
        }

        return locationAsJSON;
    }

    @Override
    public String toString() {
        String asString = "{";

        for (String line: tags.keySet()) {
            asString += line + "=" + tags.get(line) + ", ";
        }
        if(asString.length() != 1) {
            asString = asString.substring(0, asString.length() - 2);
        }
        return asString + "}";
    }
}
