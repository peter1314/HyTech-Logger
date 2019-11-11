package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks;

import com.peter.wagstaff.hytechlogger.location.AccumulatorLocation;
import com.peter.wagstaff.hytechlogger.location.CabinetLocation;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;
import com.peter.wagstaff.hytechlogger.location.RackLocation;

//Class for storing various LocationConfigurations which are used by different ItemTypes
public class LocationConfigurations {

    public static final LocationConfiguration
            //LocationConfigurations currently used by CellType ItemEntries
            CELL_CABINET = LocationConfiguration.buildLocationConfig("Cabinet", new CabinetLocation(), new String[]{Location.TYPE_KEY}, new Object[]{CabinetLocation.TYPE}),
            HT04 = LocationConfiguration.buildLocationConfig("HT04", new AccumulatorLocation(4), new String[]{Location.TYPE_KEY, "iteration"}, new Object[]{AccumulatorLocation.TYPE, 4}),
            HT05 = LocationConfiguration.buildLocationConfig("HT05", new AccumulatorLocation(5), new String[]{Location.TYPE_KEY, "iteration"}, new Object[]{AccumulatorLocation.TYPE, 5}),

            //LocationConfigurations currently used by StockType ItemEntries
            RACK = LocationConfiguration.buildLocationConfig("Rack", new RackLocation(), new String[]{Location.TYPE_KEY}, new Object[]{RackLocation.TYPE}),

            //Currently used by both CellType and StockType ItemEntries
            OTHER = LocationConfiguration.buildLocationConfig("Other", new OtherLocation(), new String[]{Location.TYPE_KEY}, new Object[]{OtherLocation.TYPE});
}
