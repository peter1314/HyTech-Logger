package com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks;

import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.DateAttribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.DecimalAttribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.IntegerAttribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.TextAttribute;

//Class for storing various Attributes which are used by different ItemTypes
public class Attributes {

    public static final Attribute
        //Generic Attributes which all ItemEntries have
        // They don't need to be added to individual ItemTypes
        CODE = new TextAttribute("code", "Code"),
        ENTRY_DATE = new DateAttribute("entry_date", ""),
        AUTHOR = new TextAttribute("author", "By"),
        LOCATION = new Attribute("location", "Location", "None"),

        //Other optional Attributes
        VOLTAGE = new DecimalAttribute("voltage", "Voltage"),
        VOLTAGE_DATE = new DateAttribute("voltage_date", "Recorded"),
        DISCHARGE_CAP = new DecimalAttribute("discharge_cap", "Discharge Capacity","Discharge Cap"),
        INTERNAL_RES = new DecimalAttribute("internal_resistance", "Internal Resistance", "Internal Res"),
        CAPACITY_DATE = new DateAttribute("capacity_date", "Recorded"),
        CHARGE_DATE = new DateAttribute("charge_date", "Last Charged"),
        MATERIAL = new TextAttribute("material", "Material"),
        SHAPE = new TextAttribute("shape", "Shape"),
        OWNER = new TextAttribute("owner", "Owner"),
        PURPOSE = new TextAttribute("purpose", "Purpose"),
        NAME = new TextAttribute("name", "Name"),
        COUNT = new IntegerAttribute("count", "Count", 1),
        NOTE = new TextAttribute("note", "Note");

        //Declare new Attributes here as needed
}
