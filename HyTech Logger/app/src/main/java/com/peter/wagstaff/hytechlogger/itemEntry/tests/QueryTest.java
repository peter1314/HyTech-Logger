package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//AttributeTest for testing if an Attribute of an ItemEntry contains a String query
public class QueryTest extends AttributeTest {

    //Query to look for
    private final String QUERY;

    /**
     * Declare QueryTest
     * @param key Key of Attribute to test
     * @param query Query to test against
     */
    public QueryTest(String key, String query) {
        super(key);
        QUERY = query.trim().toLowerCase();
    }

    @Override
    public boolean testDataEntry(ItemEntry itemEntry) {
        if(QUERY.equals("")) return true;
        return itemEntry.getData(KEY).trim().toLowerCase().contains(QUERY);
    }
}
