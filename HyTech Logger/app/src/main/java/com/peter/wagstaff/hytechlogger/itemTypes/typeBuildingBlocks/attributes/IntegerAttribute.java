package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.view.inputmethod.EditorInfo;

//Attribute that represents an integer
public class IntegerAttribute extends Attribute {

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param nickName Shortened name for nickName purposes
     * @param defaultValue Default value
     */
    public IntegerAttribute(String key, String name, String nickName, int defaultValue) {
        super(key, name, nickName, defaultValue + "", EditorInfo.TYPE_CLASS_NUMBER);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param nickName Shortened name for nickName purposes
     */
    public IntegerAttribute(String key, String name, String nickName) {
        this(key, name, nickName, 0);
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param defaultValue Default value
     */
    public IntegerAttribute(String key, String name, int defaultValue) {
        this(key, name, name, defaultValue);
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
