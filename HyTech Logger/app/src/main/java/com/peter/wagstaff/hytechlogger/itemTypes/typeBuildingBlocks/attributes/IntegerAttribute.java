package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.view.inputmethod.EditorInfo;

//Attribute that represents an integer
public class IntegerAttribute extends Attribute {

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param display Shortened name for display purposes
     * @param defualt Default value
     */
    public IntegerAttribute(String key, String name, String display, int defualt) {
        super(key, name, display, defualt + "", EditorInfo.TYPE_CLASS_NUMBER);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param display Shortened name for display purposes
     */
    public IntegerAttribute(String key, String name, String display) {
        this(key, name, display, 0);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param defualt Default value
     */
    public IntegerAttribute(String key, String name, int defualt) {
        this(key, name, name, defualt);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     */
    public IntegerAttribute(String key, String name) {
        this(key, name, name);
    }
}
