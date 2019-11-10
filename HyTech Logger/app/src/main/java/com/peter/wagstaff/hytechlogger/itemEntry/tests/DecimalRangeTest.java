package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//AttributeTest for testing if a Double Attribute of an ItemEntry is within a range, exclusive
public class DecimalRangeTest extends AttributeTest {

    //min and max of range
    private final double MIN, MAX;

    /**
     * Declare DecimalRangeTest
     * @param key Key of Attribute to test
     * @param min Min value of range
     * @param max Max value of range
     */
    public DecimalRangeTest(String key, double min, double max) {
        super(key);
        MIN = min;
        MAX = max;
    }

    @Override
    public boolean TestDataEntry(ItemEntry itemEntry) {
        Double value = Double.parseDouble(itemEntry.getData(KEY));

        if(MIN != 0 && value <= MIN) {
            return false;
        } else if(MAX != 0 && value >= MAX) {
            return false;
        }
        return true;
    }
}
