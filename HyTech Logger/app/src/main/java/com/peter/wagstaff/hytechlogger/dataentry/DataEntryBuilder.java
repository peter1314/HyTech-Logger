package com.peter.wagstaff.hytechlogger.dataentry;

public abstract class DataEntryBuilder {

    static DataEntryBuilder instance;
    DataEntry dataEntry;

    public void clear() {
        dataEntry.clear();
    }

    public DataEntry buildEntry() {
        return dataEntry;
    }
}
