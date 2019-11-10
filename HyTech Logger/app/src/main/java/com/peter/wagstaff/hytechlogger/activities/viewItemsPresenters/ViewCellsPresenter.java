package com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters;

import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewCellPresenter;
import com.peter.wagstaff.hytechlogger.customFragments.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customFragments.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customFragments.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.CellEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.location.AccumulatorLocation;
import com.peter.wagstaff.hytechlogger.location.CabinetLocation;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;

import android.content.Intent;

public class ViewCellsPresenter extends ViewDatasPresenter {

    private static final LocationConfiguration[] LOCATION_CONFIGS = {
            LocationConfiguration.buildLocationConfig("Cabinet", new String[]{Location.TYPE_KEY}, new Object[]{CabinetLocation.TYPE}),
            LocationConfiguration.buildLocationConfig("HT04", new String[]{Location.TYPE_KEY, "iteration"}, new Object[]{AccumulatorLocation.TYPE, 4}),
            LocationConfiguration.buildLocationConfig("HT05", new String[]{Location.TYPE_KEY, "iteration"}, new Object[]{AccumulatorLocation.TYPE, 5}),
            LocationConfiguration.buildLocationConfig("Other", new String[]{Location.TYPE_KEY}, new Object[]{OtherLocation.TYPE})
    };

    private static final Attribute[] MIN_MAX_TESTS = {
            CellEntry.VOLTAGE,
            CellEntry.DISCHARGE_CAP,
            CellEntry.INTERNAL_RES
    };

    private static final Attribute[] QUERY_TESTS = {

    };

    @Override
    void setLocationToggleBoxes() {
        locationCheckBoxes.clear();
        for(LocationConfiguration config: LOCATION_CONFIGS) {
            locationCheckBoxes.add(new LocationCheckBox(this, config.NAME, config));
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
    ItemEntry getEntry() {
        return new CellEntry();
    }

    @Override
    Intent getSelectIntent() {
        return new Intent(ViewCellsPresenter.this, ViewCellPresenter.class);
    }
}
