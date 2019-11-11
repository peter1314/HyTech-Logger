package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks;

import com.peter.wagstaff.hytechlogger.locations.AccumulatorLocation;
import com.peter.wagstaff.hytechlogger.locations.BlackCabinetLocation;
import com.peter.wagstaff.hytechlogger.locations.CellCabinetLocation;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;
import com.peter.wagstaff.hytechlogger.locations.OrangeCabinetLocation;
import com.peter.wagstaff.hytechlogger.locations.OtherLocation;
import com.peter.wagstaff.hytechlogger.locations.RackLocation;

//Class for storing various LocationConfigurations which are used by different ItemTypes
public class LocationConfigurations {

    public static final LocationConfiguration
            CELL_CABINET = LocationConfiguration.buildLocationConfig("Cabinet", new CellCabinetLocation()),
            HT04 = LocationConfiguration.buildLocationConfig("HT04", new AccumulatorLocation(4), new String[]{"iteration"}, new Object[]{4}),
            HT05 = LocationConfiguration.buildLocationConfig("HT05", new AccumulatorLocation(5), new String[]{"iteration"}, new Object[]{5}),
            RACK = LocationConfiguration.buildLocationConfig("Rack", new RackLocation()),
            OTHER = LocationConfiguration.buildLocationConfig("Other", new OtherLocation()),
            BLACK_CABINET = LocationConfiguration.buildLocationConfig("Black Cab", new BlackCabinetLocation()),
            ORANGE_CABINET = LocationConfiguration.buildLocationConfig("Orange Cab", new OrangeCabinetLocation());
            //Declare new location configurations here

    //Be sure to also have all LocationConfigurations in this list
    public static final LocationConfiguration[] LOCATION_CONFIGURATIONS = {
            CELL_CABINET,
            HT04,
            HT05,
            RACK,
            OTHER,
            BLACK_CABINET,
            ORANGE_CABINET
    };
}
