package com.peter.wagstaff.hytechlogger.itemEntry;

import android.text.InputType;

//Represents an entry on a cell
public class CellEntry extends ItemEntry {

    //The branch of the Firebase database where CellEntries are stored
    public static final String BRANCH = "CELLS";

    //Attributes of CellEntries
    //New Attributes can be decalred here, but must be added to ROW_ATTRIBUTES to make a difference
    public static final Attribute
        CODE = new Attribute("code", "Cell", "#"), //Redeclared to reflect type
        VOLTAGE = new Attribute("voltage", "Voltage", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
        VOLTAGE_DATE = new Attribute("voltage_date", "Recorded", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
        DISCHARGE_CAP = new Attribute("discharge_cap", "Discharge Capacity","Discharge Cap", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
        INTERNAL_RES = new Attribute("internal_resistance", "Internal Resistance", "Internal Res", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
        CAPACITY_DATE = new Attribute("capacity_date", "Recorded", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
        CHARGE_DATE = new Attribute("charge_date", "Last Charged", "00/00/0000", InputType.TYPE_CLASS_DATETIME);

    //List of row attributes which are dynamically entered and displayed
    //Attributes can be easily added and removed here to change functionality
    public static final Attribute[] ROW_ATTRIBUTES = {
            VOLTAGE,
            VOLTAGE_DATE,
            DISCHARGE_CAP,
            INTERNAL_RES,
            CAPACITY_DATE,
            CHARGE_DATE};

    /**
     * Declare CellEntry
     */
    public CellEntry() {
        super();
    }

    /**
     * Create an CellEntry from a String representing a JSONObject representing an CellEntry
     * @param entryAsJSONString String representing a JSONObject
     */
    public CellEntry(String entryAsJSONString) {
        super(entryAsJSONString);
    }

    @Override
    public String getType() { return CODE.DISPLAY; }

    @Override
    public String getBranch() {
        return BRANCH;
    }

    @Override
    public Attribute[] getRowAttributes() {
        return ROW_ATTRIBUTES;
    }
}
