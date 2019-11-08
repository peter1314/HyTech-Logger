package com.peter.wagstaff.hytechlogger.dataentry;

import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;

import org.json.JSONObject;

public class CellDataEntryBuilder extends DataEntryBuilder {

    public CellDataEntryBuilder() {
        dataEntry = new CellDataEntry();
    }

    public boolean addString(String key, String value) {
        dataEntry.setData(key, value);
        return true;
    }

    public boolean addDecimal(String key, String value) {
        if(InputVerification.verifyDecimal(value)) {
            dataEntry.setData(key, value);
            return true;
        }
        return false;
    }

    public boolean addDate(String key, String value) {
        if(InputVerification.verifyDate(value)) {
            dataEntry.setData(key, InputFormating.correctDate(value));
            return true;
        }
        return false;
    }

    public boolean addJSONObject(String key, JSONObject object) {
        dataEntry.setData(key, object);
        return true;
    }

    @Override
    public CellDataEntry buildEntry() {
        return (CellDataEntry) dataEntry;
    }
}
