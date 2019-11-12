package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;

//Represents an undefined item
public class ToolType extends  ItemType{

    //Static information associated with the ToolType, changing them will change its behavior
    private static final String MY_NAME = "TOOL";
    private static final String MY_BRANCH = "TOOLS";

    //Optional Attributes of the StockType, add or remove as needed
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.NAME,
            Attributes.NOTE,
            Attributes.PURPOSE,
            Attributes.COUNT};

    //Attributes by which this ItemType can be filtered, add or remove as needed
    //Test attributes should also be in row attributes
    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.NAME,
            Attributes.NOTE,
            Attributes.COUNT,
            Attributes.PURPOSE};

    //LocationConfigurations used to create and search Locations of this ItemType, add or remove as needed
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.ORANGE_CABINET,
            LocationConfigurations.BLACK_CABINET,
            LocationConfigurations.OTHER};

    /**
     * Declares ToolType
     */
    public ToolType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }
}
