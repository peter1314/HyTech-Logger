package com.peter.wagstaff.hytechlogger.dataentry;

import android.text.InputType;
import org.json.JSONException;

public class StockDataEntry extends DataEntry {

    public static final String BRANCH = "STOCK";

    public static final Attribute
        CODE = new Attribute("code", "Cell", "#"),
        MATERIAL = new Attribute("material", "Material", "", InputType.TYPE_CLASS_TEXT),
        SHAPE = new Attribute("shape", "Shape", "", InputType.TYPE_CLASS_TEXT),
        OWNER = new Attribute("owner", "Owner", "", InputType.TYPE_CLASS_TEXT),
        PURPOSE = new Attribute("purpose", "Purpose", "", InputType.TYPE_CLASS_TEXT);

    public static final Attribute[] ROW_ATTRIBUTES = {
            MATERIAL,
            SHAPE,
            OWNER,
            PURPOSE};

    public StockDataEntry() {
        super();
    }

    public StockDataEntry(String jsonAsString) throws JSONException {
        super(jsonAsString);
    }

    @Override
    public String getBranch() { return BRANCH; }

    @Override
    public Attribute[] rowAttributes() {
        return ROW_ATTRIBUTES;
    }
}
