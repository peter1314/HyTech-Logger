package com.peter.wagstaff.hytechlogger.inputs;

import java.text.SimpleDateFormat;

public class InputFormating {

    //format system time to readable date
    public final static SimpleDateFormat READ_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    //format system time to date that can be chronologically ordered easily
    public final static SimpleDateFormat ORDER_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    //standardize a date to MM/dd/yyyy
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

    //changes a MM/dd/yyyy date to a yyyyMMdd date
    public static String orderedDate(String date) {
        String[] parts = date.split("/");
        return  parts[2]+parts[0]+parts[1];
    }

    public static int intFromString(String input) {
        if(input == null || input.length() == 0) {
            return 0;
        }

        try {
            return Integer.parseInt(input.replaceAll("[\\D]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double decimalFromString(String input) {
        if(input == null || input.length() == 0) {
            return 0;
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
