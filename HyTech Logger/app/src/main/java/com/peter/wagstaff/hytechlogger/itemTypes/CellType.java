package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;

//Represents a cell pouch for the accumulator
public class CellType extends  ItemType{

    //Static information associated with the CellType, changing them will change its behavior
    private static final String MY_NAME = "CELL";
    private static final String MY_BRANCH = "CELLS";

    //Optional Attributes of the CellType, add or remove as needed
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.VOLTAGE,
            Attributes.VOLTAGE_DATE,
            Attributes.DISCHARGE_CAP,
            Attributes.INTERNAL_RES,
            Attributes.CAPACITY_DATE,
            Attributes.CHARGE_DATE,
            Attributes.NOTE};

    //Attributes by which this ItemType can be filtered, add or remove as needed
    //Test attributes should also be in row attributes
    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.VOLTAGE,
            Attributes.DISCHARGE_CAP,
            Attributes.INTERNAL_RES};

    //LocationConfigurations used to create and search Locations of this ItemType, add or remove as needed
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.CELL_CABINET,
            LocationConfigurations.HT04,
            LocationConfigurations.HT05,
            LocationConfigurations.OTHER};

    /**
     * Declares CellType
     */
    public CellType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }
}
