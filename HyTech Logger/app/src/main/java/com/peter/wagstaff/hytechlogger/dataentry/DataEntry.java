package com.peter.wagstaff.hytechlogger.dataentry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class DataEntry {

    //Stores pieces of data in map
    private Map<String, Object>  dataMap;

    //creates an empty data entry
    public DataEntry() {
        dataMap = new HashMap();
    }

    //creates a data entry from a string representing a JSON
    public DataEntry(String entryAsJSONString) throws JSONException {
        this();

        JSONObject entryAsJSON = new JSONObject(entryAsJSONString);
        Iterator<String> jsonKeys = entryAsJSON.keys();
        while(jsonKeys.hasNext()) {
            String key = jsonKeys.next();
            dataMap.put(key, entryAsJSON.get(key));
        }
    }

    //returns the contents of the data entry as a JSON object
    public JSONObject toDict() throws JSONException {
        JSONObject entryAsJSON = new JSONObject();

        for (String line: dataMap.keySet()) {
            entryAsJSON.put(line, dataMap.get(line));
        }

        return entryAsJSON;
    }

    @Override
    public String toString() {
        try {
            return toDict().toString();
        } catch (JSONException e) {
            return "";
        }
    }

    //adds or sets data in the entry, returns previous value or null
    public String setData(String key, String value){
        String output = null;
        if(dataMap.containsKey(key)) {
            output = dataMap.get(key).toString();
        }
        dataMap.put(key, value);
        return output;
    }

    public JSONObject setData(String key, JSONObject object) {
        JSONObject output = null;
        if(dataMap.containsKey(key)) {
            output = (JSONObject) dataMap.get(key);
        }
        dataMap.put(key, object);
        return output;
    }

    //returns data in entry or null
    public String getData(String key) {
        if(dataMap.containsKey(key)) {
            return dataMap.get(key).toString();
        }
        return null;
    }

    public JSONObject getJSONObject(String key) {
        if(dataMap.containsKey(key)) {
            return (JSONObject) dataMap.get(key);
        }
        return null;
    }

    //empties map
    public void clear() {
        dataMap.clear();
    }
}
