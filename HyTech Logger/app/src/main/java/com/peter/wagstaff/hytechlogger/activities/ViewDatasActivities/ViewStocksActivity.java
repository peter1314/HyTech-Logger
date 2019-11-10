package com.peter.wagstaff.hytechlogger.activities.ViewDatasActivities;

import android.content.Intent;
import com.peter.wagstaff.hytechlogger.activities.ViewDataActivities.ViewStockActivity;
import com.peter.wagstaff.hytechlogger.customviews.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customviews.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customviews.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.StockDataEntry;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;
import com.peter.wagstaff.hytechlogger.location.RackLocation;
import java.util.Map;

public class ViewStocksActivity extends ViewDatasActivity {

    private static final Map[] LOCATION_CONFIGS = {
            LocationBuilder.buildConfig("Rack", new String[]{Location.TYPE_KEY}, new Object[]{RackLocation.TYPE}),
            LocationBuilder.buildConfig("Other", new String[]{Location.TYPE_KEY}, new Object[]{OtherLocation.TYPE})
    };

    private static final Attribute[] MIN_MAX_TESTS = {

    };

    private static final Attribute[] QUERY_TESTS = {
            StockDataEntry.MATERIAL,
            StockDataEntry.SHAPE,
            StockDataEntry.OWNER
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
        return new StockDataEntry();
    }

    @Override
    Intent getSelectIntent() {
        return new Intent(ViewStocksActivity.this, ViewStockActivity.class);
    }
}
