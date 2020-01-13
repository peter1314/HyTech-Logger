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
    public double testDataEntry(ItemEntry itemEntry) {
        Double value = Double.parseDouble(itemEntry.getData(KEY));

        if(MIN != 0 && value <= MIN) {
            return -1;
        } else if(MAX != 0 && value >= MAX) {
            return -1;
        }
        //Instead of just returning 1, returns value - MIN, which will always by greater than 0
        //Allows outcome to be sorted by the value in question
        return value - MIN;
    }
}
