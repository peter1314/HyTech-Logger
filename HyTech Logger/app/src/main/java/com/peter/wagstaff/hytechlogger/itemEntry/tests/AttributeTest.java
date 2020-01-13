package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//Class that is used to apply a specific pass/fail test a ItemEntry using one of its Attributes
public abstract class AttributeTest {

    //Key of Attribute to test
    final String KEY;

    /**
     * Declare new AttributeTest
     * @param key Key of Attribute to Test
     */
    public AttributeTest(String key) {
        KEY = key;
    }

    /**
     * Test an ItemEntry
     * @param itemEntry ItemEntry to test
     * @return A number that represents the test outcome
     * Greater than 0 means pass, less means fail, can indicate how much it passed or failed by
     * Used as ordering number for entries
     */
    public abstract double testDataEntry(ItemEntry itemEntry);
}
