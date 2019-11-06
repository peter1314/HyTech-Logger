package com.peter.wagstaff.hytechlogger;

public class CellEntry {

    private double voltage, cap, ir;
    private String code, location, author, voltageDate, capDate, chargeDate, entryDate;

    public CellEntry() {

    }

    public void setVoltage(double newVoltage) {
        voltage =  newVoltage;
    }

    public void setVoltageDate(String date) {
        voltageDate = date;
    }

    public void setDischargeCapacity(double newCapacity) {
        cap = newCapacity;
    }

    public void setIR(double newIR) {
        ir = newIR;
    }

    public void setCapacityDate(String date) { capDate = date; }

    public void setChargeDate(String date) { chargeDate = date; }

    public void setEntryDate(String date) { entryDate = date; }

    public void setCode(String newCode) {
        code = newCode;
    }

    public void setLocation(String newLocation) { location = newLocation; }

    public void setAuthor(String newAuthor) {
        author = newAuthor;
    }

    public double getVoltage() {return voltage;}

    public double getDischargeCapacity() {return cap;}

    public double getIR() {return ir;}

    public String getVoltageDate() {return voltageDate;};

    public String getCapacityDate() {return capDate;}

    public String getChargeDate() {return chargeDate;}

    public String getEntryDate() {return entryDate;}

    public String getCode() {return code;}

    public String getLocation() {return location;}

    public String getAuthor() {return author;}

    @Override
    public String toString() {
        return code + "&" + author + "&" + entryDate + "&" + voltage + "&" + voltageDate + "&" + cap + "&" + ir + "&" + capDate + "&" + chargeDate + "&" + location;
    }

    public static CellEntry fromString(String entry) {
        CellEntry gen = new CellEntry();

        String[] data = entry.split("&", 0);

        gen.setCode(data[0]);
        gen.setAuthor(data[1]);
        gen.setEntryDate(data[2]);
        gen.setVoltage(Double.parseDouble(data[3]));
        gen.setVoltageDate(data[4]);
        gen.setDischargeCapacity(Double.parseDouble(data[5]));
        gen.setIR(Double.parseDouble(data[6]));
        gen.setCapacityDate(data[7]);
        gen.setChargeDate(data[8]);
        gen.setLocation(data[9]);

        return gen;
    }
}
