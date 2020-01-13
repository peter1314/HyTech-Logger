package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//Class used to filter ItemEntries
public class ItemEntryFilter {

    //List of Attribute tests to filter ItemEntries with
    private List<AttributeTest> tests;

    /**
     * Declare ItemEntryFilter
     */
    public ItemEntryFilter() {
        tests = new ArrayList<>();
    }

    /**
     * Add a test to the ItemEntryFilter
     * @param test AttributeTest to add
     */
    public void addTest(AttributeTest test) {
        tests.add(test);
    }

    /**
     * Remove all tests from the ItemEntryFilter
     */
    public void clearTests() {
        tests.clear();
    }

    /**
     * Filters a list of ItemEntries using its AttributeTests
     * @param unfilteredEntries List of ItemEntries to filter
     * @return A List of ItemEntries that have passed all AttributeTests in the ItemEntryFilter
     */
    public List<ItemEntry> filterDataEntries(List<ItemEntry> unfilteredEntries) {
        List<ItemEntry> filteredEntries = new ArrayList<>();

        //Tests all ItemEntries
        for (ItemEntry currentEntry: unfilteredEntries) {
            boolean passed = true;

            for (AttributeTest currentTest: tests) {
                //Sets the ordering number based on how much it passed the test by, only relevant for some tests
                currentEntry.setOrderingNumber(currentTest.testDataEntry(currentEntry));
                if(currentEntry.getOrderingNumber() < 0) {
                    passed = false;
                }
            }
            //Add ItemEntry to filteredEntries if it passed all tests
            if(passed) { filteredEntries.add(currentEntry); }
        }

        //Sorts entries based on their ordering number
        Collections.sort(filteredEntries, new Comparator<ItemEntry>() {
            @Override
            public int compare(ItemEntry o1, ItemEntry o2) {
                double diff = o1.getOrderingNumber() - o2.getOrderingNumber();
                if(diff > 0) { return (int) Math.ceil(diff);
                } else { return (int) Math.floor(diff);}
            }
        });

        return filteredEntries;
    }
}
