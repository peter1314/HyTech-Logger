package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.text.InputType;

//Attribute that represents a text
public class TextAttribute extends Attribute {

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param display Shortened name for display purposes
     */
    public TextAttribute(String key, String name, String display) {
        super(key, name, display, "", InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     */
    public TextAttribute(String key, String name) {
        this(key, name, name);
    }
}
