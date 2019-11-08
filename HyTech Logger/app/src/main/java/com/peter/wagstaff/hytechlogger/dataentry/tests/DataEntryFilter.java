package com.peter.wagstaff.hytechlogger.dataentry.tests;

import java.util.ArrayList;
import java.util.List;

import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;

public class DataEntryFilter {

    private List<DataEntryTest> tests;

    public DataEntryFilter() {
        tests = new ArrayList<>();
    }

    public void addTest(DataEntryTest test) {
        tests.add(test);
    }

    public void clearTests() {
        tests.clear();
    }

    public List<DataEntry> filterDataEntries(List<DataEntry> unfiltered) {
        List<DataEntry> filteredEntries = new ArrayList<>();
        for (DataEntry currentEntry: unfiltered) {
            boolean passed = true;

            for (DataEntryTest currentTest: tests) {
                if(!currentTest.TestDataEntry(currentEntry)) {
                    passed = false;
                }
            }

            if(passed) {
                filteredEntries.add(currentEntry);
            }
        }
        return filteredEntries;
    }
}
