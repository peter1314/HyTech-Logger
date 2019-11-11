package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks;

import android.text.InputType;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;

//Class for storing various Attributes which are used by different ItemTypes
public class Attributes {

    public static final Attribute
            //Generic Attributes which all ItemEntries have
            //They don't need to be added to individual ItemTypes
            CODE = new Attribute("code", "Code", "#"),
            ENTRY_DATE = new Attribute("entry_date", "", "00/00/0000"),
            AUTHOR = new Attribute("author", "By", "Anon"),
            LOCATION = new Attribute("location", "Location", "None"),

            //Attributes currently used by CellType ItemEntries
            VOLTAGE = new Attribute("voltage", "Voltage", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
            VOLTAGE_DATE = new Attribute("voltage_date", "Recorded", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
            DISCHARGE_CAP = new Attribute("discharge_cap", "Discharge Capacity","Discharge Cap", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
            INTERNAL_RES = new Attribute("internal_resistance", "Internal Resistance", "Internal Res", "0", InputType.TYPE_NUMBER_FLAG_DECIMAL),
            CAPACITY_DATE = new Attribute("capacity_date", "Recorded", "00/00/0000", InputType.TYPE_CLASS_DATETIME),
            CHARGE_DATE = new Attribute("charge_date", "Last Charged", "00/00/0000", InputType.TYPE_CLASS_DATETIME),

            //Attributes currently used by StockType ItemEntries
            MATERIAL = new Attribute("material", "Material", "", InputType.TYPE_CLASS_TEXT),
            SHAPE = new Attribute("shape", "Shape", "", InputType.TYPE_CLASS_TEXT),
            OWNER = new Attribute("owner", "Owner", "", InputType.TYPE_CLASS_TEXT),
            PURPOSE = new Attribute("purpose", "Purpose", "", InputType.TYPE_CLASS_TEXT),

            //Currently used by the OtherType
            NAME = new Attribute("name", "Name","", InputType.TYPE_CLASS_TEXT),

            //Currently used by both CellType StockType and OtherType ItemEntries
            NOTE = new Attribute("note", "Note", "", InputType.TYPE_CLASS_TEXT);

            //Declare new Attributes here as needed
}
