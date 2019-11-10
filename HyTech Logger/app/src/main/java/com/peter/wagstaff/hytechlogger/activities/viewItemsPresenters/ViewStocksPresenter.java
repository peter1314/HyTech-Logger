package com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.viewItemPresenters.ViewStockPresenter;
import com.peter.wagstaff.hytechlogger.customFragments.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customFragments.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customFragments.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.StockEntry;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;
import com.peter.wagstaff.hytechlogger.location.RackLocation;

public class ViewStocksPresenter extends ViewDatasPresenter {

    private static final LocationConfiguration[] LOCATION_CONFIGS = {
            LocationConfiguration.buildLocationConfig("Rack", new String[]{Location.TYPE_KEY}, new Object[]{RackLocation.TYPE}),
            LocationConfiguration.buildLocationConfig("Other", new String[]{Location.TYPE_KEY}, new Object[]{OtherLocation.TYPE})
    };

    private static final Attribute[] MIN_MAX_TESTS = {

    };

    private static final Attribute[] QUERY_TESTS = {
            StockEntry.MATERIAL,
            StockEntry.SHAPE,
            StockEntry.OWNER
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
        return new StockEntry();
    }

    @Override
    Intent getSelectIntent() {
        return new Intent(ViewStocksPresenter.this, ViewStockPresenter.class);
    }
}
