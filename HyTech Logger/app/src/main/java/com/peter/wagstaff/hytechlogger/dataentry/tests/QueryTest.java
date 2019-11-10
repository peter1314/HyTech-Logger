package com.peter.wagstaff.hytechlogger.dataentry.tests;

import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;

public class QueryTest extends DataEntryTest {

    private String key;
    private String query;

    public QueryTest(String key, String query) {
        this.key = key;
        this.query = query.trim().toLowerCase();
    }

    @Override
    public boolean TestDataEntry(DataEntry dataEntry) {
        if(query.equals("")) return true;

        return dataEntry.getData(key).trim().toLowerCase().contains(query);
    }
}
