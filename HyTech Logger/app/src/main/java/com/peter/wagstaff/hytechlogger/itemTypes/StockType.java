package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;

//Represents a metal stock
public class StockType extends  ItemType{

    //Static information associated with the StockType, changing them will change its behavior
    private static final String MY_NAME = "STOCK";
    private static final String MY_BRANCH = "STOCKS";

    //Optional Attributes of the StockType, add or remove as needed
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.MATERIAL,
            Attributes.ALLOY,
            Attributes.SHAPE,
            Attributes.OWNER,
            Attributes.PURPOSE,
            Attributes.NOTE};

    //Attributes by which this ItemType can be filtered, add or remove as needed
    //Test attributes should also be in row attributes
    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.MATERIAL,
            Attributes.ALLOY,
            Attributes.SHAPE,
            Attributes.OWNER};

    //LocationConfigurations used to create and search Locations of this ItemType, add or remove as needed
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.RACK,
            LocationConfigurations.OTHER};

    /**
     * Declares StockType
     */
    public StockType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }
}
