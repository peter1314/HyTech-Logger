package com.peter.wagstaff.hytechlogger.location;

import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import org.json.JSONException;
import org.json.JSONObject;

public class AccumulatorLocation extends Location {

    private int iteration, segment, cell;
    public static final int SEGMENT_COUNT = 4;
    public static final int SEGMENT_SIZE = 18;
    public static final String[] OPTIONS = getStaticOptions();

    public AccumulatorLocation() {
        type = "accumulator";
    }

    public AccumulatorLocation(int iteration) {
        this();
        this.iteration = iteration;
    }

    public AccumulatorLocation(String locationAsJSONString) throws JSONException {
        this();

        JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
        iteration = locationAsJSON.getInt("iteration");
        segment = locationAsJSON.getInt("segment");
        cell = locationAsJSON.getInt("cell");
    }

    public int getIteration() {return iteration;}

    @Override
    public void addSpinnerInput(String input) {
        String[] splitInput = input.split(",");
        segment = InputFormating.intFromString(splitInput[0]);
        cell = InputFormating.intFromString(splitInput[1]);
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SEGMENT_COUNT * SEGMENT_SIZE];
        for(int i = 0; i < SEGMENT_COUNT * SEGMENT_SIZE; i++) {
            optionArray[i] = "Segment " + (i / SEGMENT_SIZE + 1) + ", Cell " + ((i % SEGMENT_SIZE) + 1);
        }
        return optionArray;
    }

    @Override
    public int getCurrentOption() {
        String currentAsOption = "Segment " + segment + ", Cell " + cell;
        for(int i = 0; i < OPTIONS.length; i++) {
            if (currentAsOption.equals(OPTIONS[i])) return i;
        }
        return -1;
    }

    @Override
    public String fancyPrint() {
        return "HT0" + iteration + ": Segment " + segment + ", Cell " + cell;
    }

    @Override
    public JSONObject toDict() {
        JSONObject locationAsJSON = new JSONObject();

        try {
            locationAsJSON.put("type", type);
            locationAsJSON.put("iteration", iteration);
            locationAsJSON.put("segment", segment);
            locationAsJSON.put("cell", cell);
        } catch (JSONException e) {}

        return locationAsJSON;
    }
}
