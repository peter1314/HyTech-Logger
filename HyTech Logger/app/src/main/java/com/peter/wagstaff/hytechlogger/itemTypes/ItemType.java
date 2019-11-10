package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

public abstract class ItemType {

    private static ItemType instance;

    public final String NAME;
    public final String BRANCH;
    public final Attribute[] ROW_ATTRIBUTES;
    public final Attribute[] TEST_ATTRIBUTES;
    public final LocationConfiguration[] LOCATION_CONFIGS;

    private static final String MY_NAME = "ITEM";
    private static final String MY_BRNACH = "ITEMS";
    private static final Attribute[] MY_ROW_ATTRIBUTES = {};
    private static final Attribute[] MY_TEST_ATTRIBUTES = {};
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {};

    ItemType(String myName, String myBranch, Attribute[] myRowAttributes, Attribute[] myTestAttributes, LocationConfiguration[] myLocationConfigs) {
        NAME = myName;
        BRANCH = myBranch;
        ROW_ATTRIBUTES = myRowAttributes;
        TEST_ATTRIBUTES = myTestAttributes;
        LOCATION_CONFIGS = myLocationConfigs;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
