package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//Class that is used to apply a specific pass/fail test a ItemEntry using one of its Attributes
public abstract class AttributeTest {

    //key of Attribute to test
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
     * @return If itemEntry passed the test
     */
    public abstract boolean testDataEntry(ItemEntry itemEntry);
}
