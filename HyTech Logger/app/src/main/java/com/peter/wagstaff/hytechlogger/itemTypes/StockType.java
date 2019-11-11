package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

//Represents a metal stock
public class StockType extends  ItemType{

    //Abstract Singletons are not possible
    //Each concrete subclass of ItemType should have a getInstance() method and be a Singleton
    private static StockType instance;

    //Static information associated with the StockType, changing them will change its behavior
    private static final String MY_NAME = "STOCK";
    private static final String MY_BRANCH = "STOCKS";

    //Optional Attributes of the CellType, add or remove as needed
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.MATERIAL,
            Attributes.SHAPE,
            Attributes.OWNER,
            Attributes.PURPOSE,
            Attributes.NOTE};

    //Attributes by which this ItemType can be filtered, add or remove as needed
    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.MATERIAL,
            Attributes.SHAPE,
            Attributes.OWNER};

    //LocationConfigurations used to create and search Locations of this ItemType, add or remove as needed
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.RACK,
            LocationConfigurations.OTHER};

    /**
     * Declares StockType, should be private as ItemTypes should be Singletons
     */
    private StockType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }

    /**
     * Retrieves the single, static instance of StockType
     * @return The single instance of StockType
     */
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
