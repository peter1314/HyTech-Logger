package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

public class CellType extends  ItemType{

    private static CellType instance;

    private static final String MY_NAME = "CELL";
    private static final String MY_BRANCH = "CELLS";

    //List of row attributes which are dynamically entered and displayed
    //Attributes can be easily added and removed here to change functionality
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.VOLTAGE,
            Attributes.VOLTAGE_DATE,
            Attributes.DISCHARGE_CAP,
            Attributes.INTERNAL_RES,
            Attributes.CAPACITY_DATE,
            Attributes.CHARGE_DATE};

    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.VOLTAGE,
            Attributes.DISCHARGE_CAP,
            Attributes.INTERNAL_RES};

    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.CELL_CABINET,
            LocationConfigurations.HT04,
            LocationConfigurations.HT05,
            LocationConfigurations.OTHER};

    CellType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }

    public synchronized static CellType getInstance() {
        if(instance == null) {
            instance = new CellType();
        }
        return instance;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
