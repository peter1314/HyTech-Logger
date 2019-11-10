package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

public class StockType extends  ItemType{

    private static StockType instance;

    private static final String MY_NAME = "STOCK";
    private static final String MY_BRANCH = "STOCKS";

    //List of row attributes which are dynamically entered and displayed
    //Attributes can be easily added and removed here to change functionality
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.MATERIAL,
            Attributes.SHAPE,
            Attributes.OWNER,
            Attributes.PURPOSE,
            Attributes.NOTE};

    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.MATERIAL,
            Attributes.SHAPE,
            Attributes.OWNER};

    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.RACK,
            LocationConfigurations.OTHER};

    StockType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }

    public synchronized static StockType getInstance() {
        if(instance == null) {
            instance = new StockType();
        }
        return instance;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
