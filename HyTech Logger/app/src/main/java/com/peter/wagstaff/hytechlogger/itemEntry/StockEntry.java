package com.peter.wagstaff.hytechlogger.itemEntry;

import android.text.InputType;

//Represents an entry on a metal stock
public class StockEntry extends ItemEntry {

    //The branch of the Firebase database where StockEntries are stored
    public static final String BRANCH = "STOCK";

    //Attributes of StockEntries
    //New Attributes can be decalred here, but must be added to ROW_ATTRIBUTES to make a difference
    public static final Attribute
        CODE = new Attribute("code", "Stock", "#"), //Redeclared to reflect type
        MATERIAL = new Attribute("material", "Material", "", InputType.TYPE_CLASS_TEXT),
        SHAPE = new Attribute("shape", "Shape", "", InputType.TYPE_CLASS_TEXT),
        OWNER = new Attribute("owner", "Owner", "", InputType.TYPE_CLASS_TEXT),
        PURPOSE = new Attribute("purpose", "Purpose", "", InputType.TYPE_CLASS_TEXT),
        NOTE = new Attribute("note", "Note", "", InputType.TYPE_CLASS_TEXT);

    //List of row attributes which are dynamically entered and displayed
    //Attributes can be easily added and removed here to change functionality
    public static final Attribute[] ROW_ATTRIBUTES = {
            MATERIAL,
            SHAPE,
            OWNER,
            PURPOSE,
            NOTE};

    /**
     * Declare StockEntry
     */
    public StockEntry() {
        super();
    }

    /**
     * Create an StockEntry from a String representing a JSONObject representing an StockEntry
     * @param entryAsJSONString String representing a JSONObject
     */
    public StockEntry(String entryAsJSONString) {
        super(entryAsJSONString);
    }

    @Override
    public String getType() { return CODE.DISPLAY; }

    @Override
    public String getBranch() { return BRANCH; }

    @Override
    public Attribute[] getRowAttributes() {
        return ROW_ATTRIBUTES;
    }
}
