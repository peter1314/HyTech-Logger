package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;

//Represents a type of item that can be logged in the application
public abstract class ItemType {

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

    /**
     * Returns the name of the ItemType, useful for debugging
     * @return The name of ItemType
     */
    @Override
    public String toString() {
        return NAME;
    }
}
