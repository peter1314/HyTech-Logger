package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.view.inputmethod.EditorInfo;

//Attribute that represents a decimal
public class DecimalAttribute extends Attribute {

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param display Shortened name for display purposes
     */
    public DecimalAttribute(String key, String name, String display) {
        super(key, name, display, "0", EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     */
    public DecimalAttribute(String key, String name) {
        this(key, name, name);
    }
}
