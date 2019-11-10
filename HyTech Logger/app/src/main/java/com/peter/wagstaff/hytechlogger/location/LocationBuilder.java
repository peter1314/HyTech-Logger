package com.peter.wagstaff.hytechlogger.location;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LocationBuilder {

    public static Location buildLocation(String locationAsJSONString) {
        try {
            JSONObject locationAsJSON = new JSONObject(locationAsJSONString);
            String type = locationAsJSON.getString(Location.TYPE_KEY);

            if(type.equals(CabinetLocation.TYPE)) {
                return new CabinetLocation(locationAsJSONString);
            } else if(type.equals(AccumulatorLocation.TYPE)) {
                return new AccumulatorLocation(locationAsJSONString);
            } else if(type.equals(OtherLocation.TYPE)) {
                return new OtherLocation(locationAsJSONString);
            } else if(type.equals(RackLocation.TYPE)) {
                return new RackLocation(locationAsJSONString);
            }
            return null;
        } catch (JSONException e) {
            return null;
        }

    }

    public static Map<String, Object> buildConfig(String name, String[] tags, Object[] values) {
        if(tags.length != values.length) return null;

        Map<String, Object> validConfig = new HashMap<>();
        validConfig.put("config_name", name);
        for(int i = 0; i < tags.length; i++) {
            validConfig.put(tags[i], values[i]);
        }

        return  validConfig;
    }
}
