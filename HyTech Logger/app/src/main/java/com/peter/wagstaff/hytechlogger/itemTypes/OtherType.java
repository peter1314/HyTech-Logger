package com.peter.wagstaff.hytechlogger.itemTypes;

import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.LocationConfigurations;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

//Represents an undefined item
public class OtherType extends  ItemType{

    //Abstract Singletons are not possible
    //Each concrete subclass of ItemType should have a getInstance() method and be a Singleton
    private static OtherType instance;

    //Static information associated with the OtherType, changing them will change its behavior
    private static final String MY_NAME = "OTHER";
    private static final String MY_BRANCH = "OTHERS";

    //Optional Attributes of the CellType, add or remove as needed
    private static final Attribute[] MY_ROW_ATTRIBUTES = {
            Attributes.NAME,
            Attributes.OWNER,
            Attributes.PURPOSE,
            Attributes.NOTE};

    //Attributes by which this ItemType can be filtered, add or remove as needed
    private static final Attribute[] MY_TEST_ATTRIBUTES = {
            Attributes.NAME,
            Attributes.OWNER,
            Attributes.PURPOSE};

    //LocationConfigurations used to create and search Locations of this ItemType, add or remove as needed
    private static final LocationConfiguration[] MY_LOCATION_CONFIGS = {
            LocationConfigurations.OTHER};

    /**
     * Declares OtherType, should be private as ItemTypes should be Singletons
     */
    private OtherType() {
        super(MY_NAME, MY_BRANCH, MY_ROW_ATTRIBUTES, MY_TEST_ATTRIBUTES, MY_LOCATION_CONFIGS);
    }

    /**
     * Retrieves the single, static instance of OtherType
     * @return The single instance of OtherType
     */
    public synchronized static OtherType getInstance() {
        if(instance == null) {
            instance = new OtherType();
        }
        return instance;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
