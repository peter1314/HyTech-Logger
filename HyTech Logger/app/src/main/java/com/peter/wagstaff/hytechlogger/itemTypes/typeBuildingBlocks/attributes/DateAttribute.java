package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.text.InputType;

//Attribute that represents a date
public class DateAttribute extends Attribute {

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param display Shortened name for display purposes
     */
    public DateAttribute(String key, String name, String display) {
        super(key, name, display, "00/00/0000", InputType.TYPE_CLASS_DATETIME);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     */
    public DateAttribute(String key, String name) {
        this(key, name, name);
    }
}
