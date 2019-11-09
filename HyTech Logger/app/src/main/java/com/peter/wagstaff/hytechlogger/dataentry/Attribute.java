package com.peter.wagstaff.hytechlogger.dataentry;

public class Attribute {

    public final String ID, NAME, DISPLAY, DEFAULT;
    public final int INPUT_TYPE;

    public Attribute(String id, String name, String def) {
        ID = id;
        NAME = name;
        DISPLAY = NAME;
        DEFAULT = def;
        INPUT_TYPE = -1;

    }

    public Attribute(String id, String name, String def, int inputType) {
        ID = id;
        NAME = name;
        DISPLAY = name;
        DEFAULT = def;
        INPUT_TYPE = inputType;
    }

    public Attribute(String id, String name, String display, String def, int inputType) {
        ID = id;
        NAME = name;
        DISPLAY = display;
        DEFAULT = def;
        INPUT_TYPE = inputType;
    }

    @Override
    public String toString() { return ID; }
}
