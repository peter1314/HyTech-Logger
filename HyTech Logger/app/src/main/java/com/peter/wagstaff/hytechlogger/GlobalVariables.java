package com.peter.wagstaff.hytechlogger;

//class for storing global variables, use only if necessary
public class GlobalVariables {
    //stores the code of the currently entry, useful for when passing from one activity to another
    public static String currentEntryCode;

    //stores if the user just logged out, used to send them to login screen after logout
    public static boolean justLoggedOut = false;
}
