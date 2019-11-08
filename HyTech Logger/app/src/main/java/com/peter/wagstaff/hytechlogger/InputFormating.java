package com.peter.wagstaff.hytechlogger;

import java.text.SimpleDateFormat;

public class InputFormating {

    public final static SimpleDateFormat READ_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    public final static SimpleDateFormat ORDER_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static String correctDate(String date) {
        date = date.replaceAll("-" , "/");
        date = date.replaceAll("\\s","");

        String[] parts = date.split("/");

        if(parts[0].length() == 1) {
            parts[0] = "0" + parts[0];
        }
        if(parts[1].length() == 1) {
            parts[1] = "0" + parts[1];
        }
        if(parts[2].length() == 2) {
            parts[2] = "20" + parts[2];
        }
        return parts[0] + "/" + parts[1] + "/" + parts[2];
    }

    public static String orderedDate(String date) {
        String[] parts = date.split("/");
        return  parts[2]+parts[0]+parts[1];
    }

    public static String correctLocation(String spinnerString) {
        spinnerString = spinnerString.replaceAll(",", ".");
        spinnerString = spinnerString.replaceAll("\\s","");
        return  spinnerString;
    }

    public static String reformLocation(String location) {

        if(location.contains("Cabinet")) {
            location = "Cabinet: Shelf " + location.charAt(location.length() - 1);
        }
        else if(location.contains("HT04")) {
            location = location.replaceAll("HT04.Segment", "HT04: Seg ");
            location = location.replaceAll(".Cell", ", Cell ");
        }
        else if(location.contains("HT05")) {
            location = location.replaceAll("HT05.Segment", "HT05: Seg ");
            location = location.replaceAll(".Cell", ", Cell ");
        }
        else if(location.contains("Shop")) {
            location = "Shop Space";
        }
        else if(location.contains("Other")) {
            location = location.substring(6);
        }

        return location;
    }

    public static int selectionFromString(String input) {
        String[] inputs = input.split("\\.", 0);

        if(inputs.length == 1) {
            if(inputs[0].equals("ShopSpace")) {
                return 0;
            }
            if(inputs[0].equals("Lost")) {
                return 1;
            }
            if(inputs[0].equals("Other")) {
                return 2;
            }
            return Integer.parseInt(inputs[0].replaceAll("[^\\d.]", "")) - 1;
        } else {
            int seg = Integer.parseInt(inputs[0].replaceAll("[^\\d.]", ""));
            int cell = Integer.parseInt(inputs[1].replaceAll("[^\\d.]", ""));

            return (seg - 1) * 18 + cell - 1;
        }
    }
}
