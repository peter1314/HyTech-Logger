package com.peter.wagstaff.hytechlogger.itemEntry;

import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Represents an entry on an item, these entries are stores in the Firebase database under the LOGS of the Item
public class ItemEntry {

    //Type of the ItemEntry
    public final ItemType TYPE;

    //Number used to order entries in a search
    private double orderingNumber = 0;

    //Stores the ItemEntry's data in a Map
    private Map<String, Object>  dataMap;

    /**
     * Declares ItemEntry and finalizes its type
     * @param type The ItemType of the ItemEntry
     */
    public ItemEntry(ItemType type) {
        TYPE = type;
        dataMap = new HashMap();
    }

    /**
     * Create an ItemEntry from a String representing a JSONObject representing an ItemEntry
     * @param type The ItemType of the ItemEntry
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

    /**
     * Gets the ItemType of the ItemEntry
     * @return The ItemType of the ItemEntry
     */
    public ItemType getType() {
        return TYPE;
    }

    /**
     * ItemEntries are stored in the database as JSONObjects
     * @return A JSONObject representing this ItemEntry
     */
    public JSONObject toJSON() {
        JSONObject entryAsJSON = new JSONObject();
        //Puts the ItemEntry's data into JSONObject
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
        return toJSON().toString();
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

    /**
     * Sets the ordering number
     * @param newOrderingNumber
     */
    public void setOrderingNumber(Double newOrderingNumber) {
        orderingNumber = newOrderingNumber;
    }

    /**
     * @return The ordering number
     */
    public double getOrderingNumber() {
        return  orderingNumber;
    }
}
