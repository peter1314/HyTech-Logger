package com.peter.wagstaff.hytechlogger.location;

import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import org.json.JSONException;

//Represents a location within an accumulator
public class AccumulatorLocation extends Location {

    //Value of the special type tag for an AccumulatorLocation
    public static final String TYPE = "accumulator";

    //Number of segments and cells per segment
    public static final int SEGMENT_COUNT = 4;
    public static final int SEGMENT_SIZE = 18;

    //Array of the 72 possible locations within an accumulator
    public static final String[] OPTIONS = getStaticOptions();

    /**
     * Declare an AccumulatorLocation with an iteration
     * @param iteration The iteration of the accumulator
     */
    public AccumulatorLocation(int iteration) {
        super();
        tags.put(Location.TYPE_KEY, TYPE);
        tags.put("iteration", iteration);
    }

    /**
     * Create a new AccumulatorLocation from a Location as a JSON String
     * The JSON String should represent an AccumulatorLocation
     * @param locationAsJSONString A Location represented by a JSON String
     * @throws JSONException
     */
    public AccumulatorLocation(String locationAsJSONString) throws JSONException {
        super(locationAsJSONString);
    }

    @Override
    public void addSpinnerInput(String input) {
        String[] splitInput = input.split(",");
        tags.put("segment", InputFormatting.intFromString(splitInput[0]));
        tags.put("cell", InputFormatting.intFromString(splitInput[1]));
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
        return "HT0" + tags.get("iteration") + ": Seg " + tags.get("segment") + ", Cell " + tags.get("cell");
    }

    /**
     * Get the iteration of the AccumulatorLocation, which is its year
     * @return The iteration of the AccumulatorLocation
     */
    public int getIteration() { return (int) tags.get("iteration"); }

    /**
     * Used to initialize the options of an AccumulatorLocation, based on the segment count and size
     * @return The options of an AccumulatorLocation
     */
    private static String[] getStaticOptions() {
        String[] optionArray  = new String[SEGMENT_COUNT * SEGMENT_SIZE];
        for(int i = 0; i < SEGMENT_COUNT * SEGMENT_SIZE; i++) {
            optionArray[i] = "Segment " + (i / SEGMENT_SIZE + 1) + ", Cell " + ((i % SEGMENT_SIZE) + 1);
        }
        return optionArray;
    }
}
