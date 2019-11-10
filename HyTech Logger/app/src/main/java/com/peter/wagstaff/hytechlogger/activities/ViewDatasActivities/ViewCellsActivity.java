package com.peter.wagstaff.hytechlogger.activities.ViewDatasActivities;

import com.peter.wagstaff.hytechlogger.activities.ViewDataActivities.ViewCellActivity;
import com.peter.wagstaff.hytechlogger.customviews.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customviews.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customviews.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.location.AccumulatorLocation;
import com.peter.wagstaff.hytechlogger.location.CabinetLocation;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;

import android.content.Intent;
import java.util.Map;

public class ViewCellsActivity extends ViewDatasActivity {

    private static final Map[] LOCATION_CONFIGS = {
            LocationBuilder.buildConfig("Cabinet", new String[]{Location.TYPE_KEY}, new Object[]{CabinetLocation.TYPE}),
            LocationBuilder.buildConfig("HT04", new String[]{Location.TYPE_KEY, "iteration"}, new Object[]{AccumulatorLocation.TYPE, 4}),
            LocationBuilder.buildConfig("HT05", new String[]{Location.TYPE_KEY, "iteration"}, new Object[]{AccumulatorLocation.TYPE, 5}),
            LocationBuilder.buildConfig("Other", new String[]{Location.TYPE_KEY}, new Object[]{OtherLocation.TYPE})
    };

    private static final Attribute[] MIN_MAX_TESTS = {
            CellDataEntry.VOLTAGE,
            CellDataEntry.DISCHARGE_CAP,
            CellDataEntry.INTERNAL_RES
    };

    private static final Attribute[] QUERY_TESTS = {

    };

    @Override
    void setLocationToggleBoxes() {
        locationCheckBoxes.clear();
        for(Map<String, Object> config: LOCATION_CONFIGS) {
            locationCheckBoxes.add(new LocationCheckBox(this, config.get("config_name").toString(), config));
        }
    }

    @Override
    void setMinMaxHolders() {
        minMaxCriteria.clear();
        for(Attribute attribute: MIN_MAX_TESTS) {
            minMaxCriteria.add(new MinMaxHolder(this, attribute));
        }
    }

    @Override
    void setQueryHolders() {
        queryCriteria.clear();
        for(Attribute attribute: QUERY_TESTS) {
            queryCriteria.add(new QueryHolder(this, attribute));
        }
    }

    @Override
    DataEntry getEntry() {
        return new CellDataEntry();
    }

    @Override
    Intent getSelectIntent() {
        return new Intent(ViewCellsActivity.this, ViewCellActivity.class);
    }
}
