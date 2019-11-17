package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes;

import android.text.InputType;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

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
     * Create an Attribute from a String representing a JSONObject representing an Attribute
     * @param attributeAsJSONString String representing a JSONObject
     */
    public Attribute(String attributeAsJSONString) {
        //These values must serve as a go between because the finals can only be set once
        //Which is not compatible with the try catch structure
        String key, name, nickName, defualtValue;
        key = name = nickName = defualtValue = "NA";
        int inputType = InputType.TYPE_CLASS_TEXT;

        try {
            JSONObject attributeAsJSON = new JSONObject(attributeAsJSONString);
            key = attributeAsJSON.getString("key");
            name = attributeAsJSON.getString("name");
            nickName = attributeAsJSON.getString("nickname");
            defualtValue = attributeAsJSON.getString("default");
            inputType = attributeAsJSON.getInt("input_type");
        } catch(JSONException e) {}

        KEY = key;
        NAME = name;
        NICK_NAME = nickName;
        DEFAULT = defualtValue;
        INPUT_TYPE = inputType;
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
