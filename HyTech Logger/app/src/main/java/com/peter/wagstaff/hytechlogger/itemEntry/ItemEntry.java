package com.peter.wagstaff.hytechlogger.itemEntry;

import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Represents an entry on a Item, these items are stores in the Firebase database under the LOGS of the Item
public class ItemEntry {

    public final ItemType TYPE;

    //Stores the ItemEntry's data in a Map
    private Map<String, Object>  dataMap;

    /**
     * Declare ItemEntry
     */
    public ItemEntry(ItemType type) {
        TYPE = type;
        dataMap = new HashMap();
    }

    /**
     * Create an ItemEntry from a String representing a JSONObject representing an ItemEntry
     * @param entryAsJSONString String representing a JSONObject
     */
    public ItemEntry(ItemType type, String entryAsJSONString) {
        this(type);

        try {
            JSONObject entryAsJSON = new JSONObject(entryAsJSONString);

            //Add all items in the entry to the data map
            Iterator<String> jsonKeys = entryAsJSON.keys();
            while(jsonKeys.hasNext()) {
                String key = jsonKeys.next();
                dataMap.put(key, entryAsJSON.get(key));
            }
        } catch(JSONException e) {}
    }

    public ItemType getType() {
        return TYPE;
    }
/*
    *//**
     * Returns the type of the ItemEntry
     * @return the type of the ItemEntry
     *//*
    public String getType() { return Type.getInstance().CODE.DISPLAY; }

    *//**
     * Gets the branch of the Firebase database ItemEntries of this type are stored in
     * @return The branch of the ItemEntry
     *//*
    public String getBranch() {
        return Type.getInstance().BRANCH;
    }

    *//**
     * Returns the row attributes of the ItemEntry, dynamically entered and displayed
     * @return List of Attributes to be dynamically handled
     *//*
    public Attribute[] getRowAttributes() {
        return Type.getInstance().ROW_ATTRIBUTES;
    }*/

    //returns the contents of the data entry as a JSON object

    /**
     * ItemEntries are stored in the database as JSONObjects
     * @return A JSONObject representing this ItemEntry
     */
    public JSONObject toDict() {
        JSONObject entryAsJSON = new JSONObject();

        for (String line: dataMap.keySet()) {
            try {
                entryAsJSON.put(line, dataMap.get(line));
            } catch (JSONException e) {}
        }
        return entryAsJSON;
    }

    /**
     * Useful for debugging
     * @return A textual representation of the ItemEntry
     */
    @Override
    public String toString() {
        return toDict().toString();
    }

    /**
     * Set String data in the ItemEntry's data map
     * @param key Key of the data
     * @param value Value of the data as String
     * @return The old value of the data or null
     */
    public String setData(String key, String value){
        String output = null;
        if(dataMap.containsKey(key)) { output = dataMap.get(key).toString(); }
        dataMap.put(key, value);
        return output;
    }

    /**
     * Set JSONObject data in the ItemEntry's data map
     * @param key Key of the data
     * @param object Value of the data as JSONObject
     * @return The old value of the data or null
     */
    public JSONObject setData(String key, JSONObject object) {
        JSONObject output = null;
        if(dataMap.containsKey(key)) {
            output = (JSONObject) dataMap.get(key);
        }
        dataMap.put(key, object);
        return output;
    }

    /**
     * Gets String data in the ItemEntry
     * @param key Key of the data
     * @return Value of the data as a String or an empty String
     */
    public String getData(String key) {
        if(dataMap.containsKey(key)) {
            return dataMap.get(key).toString();
        }
        return "";
    }

    /**
     * Gets JSONObject data in the ItemEntry
     * @param key Key of the data, data should be a JSONObject
     * @return Value of the data as a JSONObject or an empty JSONObject
     */
    public JSONObject getJSONObject(String key) {
        if(dataMap.containsKey(key)) {
            return (JSONObject) dataMap.get(key);
        }
        return new JSONObject();
    }

    /**
     * Empties the data map of the ItemEntry
     */
    public void clear() {
        dataMap.clear();
    }
}
