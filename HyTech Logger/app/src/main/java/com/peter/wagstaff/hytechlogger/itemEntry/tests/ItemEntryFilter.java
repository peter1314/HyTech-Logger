package com.peter.wagstaff.hytechlogger.itemEntry.tests;

import java.util.ArrayList;
import java.util.List;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

//Class used to filter ItemEntries
public class ItemEntryFilter {

    //list of Attribute tests to filter ItemEntries with
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
                if(!currentTest.testDataEntry(currentEntry)) {
                    passed = false;
                }
            }
            //Add ItemEntry to filteredEntries if it passed all tests
            if(passed) { filteredEntries.add(currentEntry); }
        }
        return filteredEntries;
    }
}
