package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.text.InputType;

//Represents an attribute of an Item to be logged
public class Attribute {

    //Strings that defaultValueine the behavior of the Attribute
    public final String KEY, NAME, NICK_NAME, DEFAULT;
    //Defines the InputType of an Attribute, corresponds to the InputType Class, no just an int
    public final int INPUT_TYPE;

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param nickName Shortened name for nickName purposes
     * @param defaultValue Default value of the Attribute
     * @param inputType InputType of the Attribute
     */
    public Attribute(String key, String name, String nickName, String defaultValue, int inputType) {
        KEY = key;
        NAME = name;
        NICK_NAME = nickName;
        DEFAULT = defaultValue;
        INPUT_TYPE = inputType;
    }

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param defaultValue Default value of the Attribute
     * @param inputType InputType of the Attribute
     */
    public Attribute(String key, String name, String defaultValue, int inputType) {
        this(key, name, name, defaultValue, inputType);
    }

    /**
     * Declare Attribute and finalize its fields, defaults input to text
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param defaultValue Default value of the Attribute
     */
    public Attribute(String key, String name, String defaultValue) {
        this(key, name, defaultValue, InputType.TYPE_CLASS_TEXT);
    }

    /**
     * Useful for debugging
     * @return A textual representation of the Location
     */
    @Override
    public String toString() {
        return "ATTRIBUTE: " + KEY + ", " + NAME + ", " + NICK_NAME + ", " + DEFAULT + ", " + INPUT_TYPE;
    }
}
