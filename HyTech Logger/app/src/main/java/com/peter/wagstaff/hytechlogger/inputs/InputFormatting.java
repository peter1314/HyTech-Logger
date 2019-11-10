package com.peter.wagstaff.hytechlogger.inputs;

import java.text.SimpleDateFormat;

//Class with static methods to format String inputs
public class InputFormatting {

    //Used to format a system time as a readable date
    public final static SimpleDateFormat READ_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    //Used to format a system time as a date that can be chronologically ordered easily
    public final static SimpleDateFormat ORDER_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * Standardize a date input to MM/DD/YYYY
     * @param date A String representing a valid date
     * @return A String representing a date as MM/DD/YYYY
     */
    public static String correctDate(String date) {
        //Change and dashes to slashes
        date = date.replaceAll("-" , "/");
        //Remove all whitespace
        date = date.replaceAll("\\s","");

        //Split the date into its components
        String[] parts = date.split("/");

        //Make sure month is 2 digits
        if(parts[0].length() == 1) {
            parts[0] = "0" + parts[0];
        }
        //Make sure date is 2 digits
        if(parts[1].length() == 1) {
            parts[1] = "0" + parts[1];
        }
        //Make sure year is 4 digits
        if(parts[2].length() == 2) {
            parts[2] = "20" + parts[2];
        }

        //Return reconstituted date
        return parts[0] + "/" + parts[1] + "/" + parts[2];
    }

    /**
     * Change a MM/DD/YYYY date to a YYYYMMDD date
     * @param date String representing a MM/DD/YYYY date
     * @return return a String representing a YYYYMMDD date
     */
    public static String orderedDate(String date) {
        String[] parts = date.split("/");
        return  parts[2]+parts[0]+parts[1];
    }

    /**
     * Robustly creates a int from a String, defualts to 0
     * @param input A String possibly containing an int
     * @return The int that was in the String or 0
     */
    public static int intFromString(String input) {
        if(input == null || input.length() == 0) { return 0; }

        try {
            return Integer.parseInt(input.replaceAll("[\\D]", ""));
        } catch (NumberFormatException e) { return 0; }
    }

    /**
     * Robustly creates a double from a String, defualts to 0
     * @param input A String possibly containing a double
     * @return The double that was in the String or 0
     */
    public static double decimalFromString(String input) {
        if(input == null || input.length() == 0) { return 0; }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) { return 0; }
    }
}
