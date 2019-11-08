package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, Object> buildConfig(String[] tags, Object[] values) {
        if(tags.length != values.length) return null;

        Map<String, Object> validConfig = new HashMap<>();
        for(int i = 0; i < tags.length; i++) {
            validConfig.put(tags[i], values[i]);
        }

        return  validConfig;
    }
}
