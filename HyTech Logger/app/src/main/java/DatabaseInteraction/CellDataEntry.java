package DatabaseInteraction;

import org.json.JSONException;

public class CellDataEntry extends DataEntry {

    public static final String CODE = "code";
    public static final String ENTRY_DATE = "entry_date";
    public static final String AUTHOR = "author";
    public static final String VOLTAGE = "voltage";
    public static final String VOLTAGE_DATE = "voltage_date";
    public static final String DISCHARGE_CAP = "discharge_cap";
    public static final String INTERNAL_RES = "internal_resistance";
    public static final String CAPACITY_DATE = "capacity_date";
    public static final String CHARGE_DATE = "charge_date";
    public static final String LOCATION = "location";

    public CellDataEntry() {
        super();
    }

    public CellDataEntry(String jsonAsString) throws JSONException {
        super(jsonAsString);
    }

}
