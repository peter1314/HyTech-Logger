package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//Represents a type of item that can be logged in the application
public class ItemType {

    //Used to dynamically retrieve ItemType's information
    public final String NAME;
    public final String BRANCH;
    public final Attribute[] ROW_ATTRIBUTES;
    public final Attribute[] TEST_ATTRIBUTES;
    public final LocationConfiguration[] LOCATION_CONFIGS;

    //Static information associated with the ItemType, changing them will change the ItemType
    //These do nothing because the ItemType is abstract, but demonstrates the general format
    private static final String MY_NAME = "ITEM";
    private static final String MY_BRNACH = "ITEMS";
    private static final Attribute[] MY_ROW_ATTRIBUTES = {};
    private static final Attribute[] MY_TEST_ATTRIBUTES = {};
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {};

    /**
     * Declares ItemType and finalizes its information
     * @param myName Name of the ItemType
     * @param myBranch Branch of the Firebase database the ItemType is stored under
     * @param myRowAttributes Optional Attributes of an ItemType
     * @param myTestAttributes Attributes by which this ItemType can be filtered
     * @param myLocationConfigs LocationConfigurations used to create and search Locations of this ItemType
     */
    ItemType(String myName, String myBranch, Attribute[] myRowAttributes, Attribute[] myTestAttributes, LocationConfiguration[] myLocationConfigs) {
        NAME = myName;
        BRANCH = myBranch;
        ROW_ATTRIBUTES = myRowAttributes;
        TEST_ATTRIBUTES = myTestAttributes;
        LOCATION_CONFIGS = myLocationConfigs;
    }

    public ItemType(String itemTypeAsJSONString) {
        String name = "NA";
        String branch = "NA";
        List<Attribute> rowAttributes = new LinkedList<>();
        List<Attribute> testAttributes = new LinkedList<>();
        List<LocationConfiguration> locationConfigs = new LinkedList<>();

        try {
            JSONObject itemTypeAsJSON = new JSONObject(itemTypeAsJSONString);
            name = itemTypeAsJSON.getString("name");
            branch = itemTypeAsJSON.getString("branch");
            JSONObject rowAttributesJSON = itemTypeAsJSON.getJSONObject("row_attributes");
            JSONObject testAttributesJSON = itemTypeAsJSON.getJSONObject("test_attributes");
            JSONObject locationConfigsJSON = itemTypeAsJSON.getJSONObject("location_configs");

            Iterator<String> jsonKeys = rowAttributesJSON.keys();
            while(jsonKeys.hasNext()) {
                rowAttributes.add(new Attribute(rowAttributesJSON.getString(jsonKeys.next())));
            }

            jsonKeys = testAttributesJSON.keys();
            while(jsonKeys.hasNext()) {
                testAttributes.add(new Attribute(rowAttributesJSON.getString(jsonKeys.next())));
            }

            jsonKeys = locationConfigsJSON.keys();
            while(jsonKeys.hasNext()) {
                locationConfigs.add(new LocationConfiguration(locationConfigsJSON.getString(jsonKeys.next())));
            }
        } catch (JSONException e) {}

        NAME = name;
        BRANCH = branch;
        ROW_ATTRIBUTES = rowAttributes.toArray(new Attribute[rowAttributes.size()]);
        TEST_ATTRIBUTES = testAttributes.toArray(new Attribute[testAttributes.size()]);
        LOCATION_CONFIGS = locationConfigs.toArray(new LocationConfiguration[locationConfigs.size()]);
    }

    public JSONObject toJSON() {
        JSONObject itemTypeAsJSON = new JSONObject();
        try {
            itemTypeAsJSON.put("name", NAME);
            itemTypeAsJSON.put("branch", BRANCH);
            itemTypeAsJSON.put("row_attributes", Attribute.collectionToJSON(ROW_ATTRIBUTES));
            itemTypeAsJSON.put("test_attributes", Attribute.collectionToJSON(TEST_ATTRIBUTES));
            itemTypeAsJSON.put("location_configs", LocationConfiguration.collectionToJSON(LOCATION_CONFIGS));
        } catch (JSONException e) {}
        return itemTypeAsJSON;
    }

    /**
     * Returns the name of the ItemType, useful for debugging
     * @return The name of ItemType
     */
    @Override
    public String toString() {
        return NAME;
    }
}
