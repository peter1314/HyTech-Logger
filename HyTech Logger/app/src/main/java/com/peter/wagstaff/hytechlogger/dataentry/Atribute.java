package com.peter.wagstaff.hytechlogger.dataentry;

public class Atribute {

    private String id, display, def;

    public Atribute(String id, String display, String def) {
        this.id = id;
        this.display = display;
        this.def = def;
    }

    public String id() { return id; }

    public String display() { return display; }

    public String def() { return def; }
}
