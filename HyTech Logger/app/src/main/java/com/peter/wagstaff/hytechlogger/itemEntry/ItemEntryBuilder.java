package com.peter.wagstaff.hytechlogger.itemEntry;

import android.text.InputType;
import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import org.json.JSONObject;

//Class used to build ItemEntries
public class ItemEntryBuilder {

    //Private ItemEntry being built
    private ItemEntry itemEntry;

    /**
     * Declares ItemEntryBuilder with ItemType to build
     * @param itemType ItemType of ItemEntry to build
     */
    public ItemEntryBuilder(ItemType itemType) {
        this.itemEntry = new ItemEntry(itemType);
    }

    /**
     * Verifies and sets the value of an Attribute in the ItemEntry
     * @param attribute Attribute to set value of
     * @param value Value to set
     * @return If the value was successfully added
     */
    public boolean setAttribute(Attribute attribute, String value) {
        //Check the InputType of the Attribute to decide how to verify and add the value
        if(attribute.INPUT_TYPE == InputType.TYPE_NUMBER_FLAG_DECIMAL) {
            if (!setDecimal(attribute.KEY, value)) { return false; }
        } else if(attribute.INPUT_TYPE == InputType.TYPE_CLASS_DATETIME) {
            if (!setDate(attribute.KEY, value)) { return false; }
        } else { setString(attribute.KEY, value); }
        return true;
    }

    /**
     * Verifies and sets the value of a String Attribute in the ItemEntry
     * @param key Key of the Attribute
     * @param value Value of the Attribute
     * @return If the String was added
     */
    public boolean setString(String key, String value) {
        itemEntry.setData(key, value);
        return true;
    }

    /**
     * Verifies and sets the value of a Decimal Attribute in the ItemEntry
     * @param key Key of the Attribute
     * @param value Value of the Attribute
     * @return If the decimal String was added
     */
    public boolean setDecimal(String key, String value) {
        if(InputVerification.verifyDecimal(value)) {
            itemEntry.setData(key, value);
            return true;
        }
        return false;
    }

    /**
     * Verifies and sets the value of a date Attribute in the ItemEntry
     * @param key Key of the Attribute
     * @param value Value of the Attribute
     * @return If the date String was added
     */
    public boolean setDate(String key, String value) {
        if(InputVerification.verifyDate(value)) {
            itemEntry.setData(key, InputFormatting.correctDate(value));
            return true;
        }
        return false;
    }

    /**
     * Verifies and sets the value of a JSONObject Attribute in the ItemEntry
     * @param key Key of the Attribute
     * @param object Value of the Attribute
     * @return If the JSONObject was added
     */
    public boolean setJSONObject(String key, JSONObject object) {
        itemEntry.setData(key, object);
        return true;
    }

    /**
     * Gets the ItemEntry that the ItemEntryBuilder has built
     * @return The ItemEntry of the ItemEntryBuilder
     */
    public ItemEntry getEntry() {
        return itemEntry;
    }
}
