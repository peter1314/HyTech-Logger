package com.peter.wagstaff.hytechlogger.dataentry;

import android.text.InputType;
import android.widget.Toast;

import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import org.json.JSONObject;

public class DataEntryBuilder<Type extends DataEntry> {

    DataEntry dataEntry;

    public DataEntryBuilder() {
        dataEntry = new CellDataEntry();
    }

    public boolean addAttribute(Attribute attribute, String value) {
        if(attribute.INPUT_TYPE == InputType.TYPE_NUMBER_FLAG_DECIMAL) {
            if (!addDecimal(attribute.ID, value)) {
                return false;
            }
        } else if(attribute.INPUT_TYPE == InputType.TYPE_CLASS_DATETIME) {
            if (!addDate(attribute.ID, value)) {
                return false;
            }
        } else {
            addString(attribute.ID, value);
        }
        return true;
    }

    public boolean addString(String key, String value) {
        dataEntry.setData(key, value);
        return true;
    }

    public boolean addDecimal(String key, String value) {
        if(InputVerification.verifyDecimal(value)) {
            dataEntry.setData(key, value);
            return true;
        }
        return false;
    }

    public boolean addDate(String key, String value) {
        if(InputVerification.verifyDate(value)) {
            dataEntry.setData(key, InputFormating.correctDate(value));
            return true;
        }
        return false;
    }

    public boolean addJSONObject(String key, JSONObject object) {
        dataEntry.setData(key, object);
        return true;
    }

    public Type buildEntry() {
        return (Type) dataEntry;
    }
}
