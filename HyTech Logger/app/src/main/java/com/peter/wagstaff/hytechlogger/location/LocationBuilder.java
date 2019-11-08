package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationBuilder {

    public static Location buildLocation(String locationAsJSONString) {
        try {
            JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
            String type = locationAsJSON.getString("type");

            if(type.equals("cabinet")) {
                return new CabinetLocation(locationAsJSONString);
            } else if(type.equals("accumulator")) {
                return new AccumulatorLocation(locationAsJSONString);
            } else if(type.equals("other")) {
                return new OtherLocation(locationAsJSONString);
            }
            return null;
        } catch (JSONException e) {
            return null;
        }

    }
}
