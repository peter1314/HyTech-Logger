package com.peter.wagstaff.hytechlogger.location;

import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import org.json.JSONException;

public class AccumulatorLocation extends Location {

    public static final int SEGMENT_COUNT = 4;
    public static final int SEGMENT_SIZE = 18;
    public static final String[] OPTIONS = getStaticOptions();

    public AccumulatorLocation() {
        super();
        tags.put("type", "accumulator");
    }

    public AccumulatorLocation(int iteration) {
        this();
        tags.put("iteration", iteration);
    }

    public AccumulatorLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) {
        String[] splitInput = input.split(",");
        tags.put("segment", InputFormating.intFromString(splitInput[0]));
        tags.put("cell", InputFormating.intFromString(splitInput[1]));
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = "Segment " + tags.get("segment") + ", Cell " + tags.get("cell");
        for(int i = 0; i < OPTIONS.length; i++) {
            if (currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return "HT0" + tags.get("iteration") + ": Segment " + tags.get("segment") + ", Cell " + tags.get("cell");
    }

    public int getIteration() { return (int) tags.get("iteration"); }

    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SEGMENT_COUNT * SEGMENT_SIZE];
        for(int i = 0; i < SEGMENT_COUNT * SEGMENT_SIZE; i++) {
            optionArray[i] = "Segment " + (i / SEGMENT_SIZE + 1) + ", Cell " + ((i % SEGMENT_SIZE) + 1);
        }
        return optionArray;
    }
}
