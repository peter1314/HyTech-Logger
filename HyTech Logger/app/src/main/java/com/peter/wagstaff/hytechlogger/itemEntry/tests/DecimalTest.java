package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//AttributeTest for testing if a Double Attribute of an ItemEntry equals a value
public class DecimalTest extends AttributeTest {

    //Value to test against
    private final double VALUE;

    /**
     * Declare DecimalTest
     * @param key Key of Attribute to test
     * @param value Value to test against
     */
    public DecimalTest(String key, double value) {
        super(key);
        VALUE = value;
    }

    @Override
    public double testDataEntry(ItemEntry itemEntry) {
        Double value = Double.parseDouble(itemEntry.getData(KEY));
        if(value == VALUE) return 1;
        return -1;
    }
}
