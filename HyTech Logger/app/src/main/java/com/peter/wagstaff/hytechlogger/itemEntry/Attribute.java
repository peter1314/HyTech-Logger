package com.peter.wagstaff.hytechlogger.itemEntry;

//Represents an attribute of an Item to be logged
public class Attribute {

    //Strings that defaultValueine the behavior of the Attribute
    public final String KEY, NAME, DISPLAY, DEFAULT;
    //Defines the InputType of an Attribute, corresponds to the InputType Class, no just an int
    public final int INPUT_TYPE;

    /**
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param display Shortened name for display purposes
     * @param defaultValue Default value of the Attribute
     * @param inputType InputType of the Attribute
     */
    public Attribute(String key, String name, String display, String defaultValue, int inputType) {
        KEY = key;
        NAME = name;
        DISPLAY = display;
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
     * Declare Attribute and finalize its fields
     * @param key Key of the Attribute
     * @param name Name of the Attribute
     * @param defaultValue Default value of the Attribute
     */
    public Attribute(String key, String name, String defaultValue) {
        this(key, name, defaultValue, 0);
    }

    /**
     * Useful for debugging
     * @return A textual representation of the Location
     */
    @Override
    public String toString() {
        return "ATTRIBUTE: " + KEY + ", " + NAME + ", " + DISPLAY + ", " + DEFAULT + ", " + INPUT_TYPE;
    }
}
