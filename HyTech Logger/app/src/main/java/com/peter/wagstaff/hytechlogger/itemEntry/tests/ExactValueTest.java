package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//AttributeTest for testing if an Attribute of an ItemEntry exactly matches a String
public class ExactValueTest extends AttributeTest {

    //value to test against
    private String value;

    /**
     * Declare ExactValueTest
     * @param key Key of Attribute to test
     * @param value Value to test against
     */
    public ExactValueTest(String key, String value) {
        super(key);
        this.value = value.trim().toLowerCase();
    }

    @Override
    public boolean TestDataEntry(ItemEntry itemEntry) {
        String entryValue = itemEntry.getData(KEY).trim().toLowerCase();
        return value.equals(entryValue);
    }
}
