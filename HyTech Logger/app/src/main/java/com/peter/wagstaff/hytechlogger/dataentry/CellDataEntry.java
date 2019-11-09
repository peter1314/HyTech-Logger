package com.peter.wagstaff.hytechlogger.dataentry;

import android.text.InputType;
import org.json.JSONException;

public class CellDataEntry extends DataEntry {

    public static final String BRANCH = "CELLS";

    public static final Attribute
        CODE = new Attribute("code", "Cell", "#"),
        VOLTAGE = new Attribute("voltage", "Voltage", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
        VOLTAGE_DATE = new Attribute("voltage_date", "Recorded", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
        DISCHARGE_CAP = new Attribute("discharge_cap", "Discharge Capacity","Discharge Cap", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
        INTERNAL_RES = new Attribute("internal_resistance", "Internal Resistance", "Internal Res", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
        CAPACITY_DATE = new Attribute("capacity_date", "Recorded", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
        CHARGE_DATE = new Attribute("charge_date", "Last Charged", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
        TEST = new Attribute("test", "Test", "", InputType.TYPE_CLASS_TEXT);

    public static final Attribute[] ROW_ATTRIBUTES = {
            VOLTAGE,
            VOLTAGE_DATE,
            DISCHARGE_CAP,
            INTERNAL_RES,
            //TEST,         //new attributes can be easily added and removed from the data entry
            CAPACITY_DATE,
            CHARGE_DATE};

    public CellDataEntry() {
        super();
    }

    public CellDataEntry(String jsonAsString) throws JSONException {
        super(jsonAsString);
    }

    @Override
    public String getBranch() {
        return BRANCH;
    }

    @Override
    public Attribute[] rowAttributes() {
        return ROW_ATTRIBUTES;
    }
}
