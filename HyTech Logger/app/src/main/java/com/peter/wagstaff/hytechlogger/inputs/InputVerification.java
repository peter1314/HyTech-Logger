package com.peter.wagstaff.hytechlogger.inputs;

import android.content.Context;
import android.widget.Toast;

//Class with static methods to verify inputs
public class InputVerification {

    /**
     * Verifies that a String is a valid QR code
     * It is valid if it is alphanumerical and within 4 to 8 characters
     * @param code String of possible QR code
     * @return If the code is valid
     */
    public static boolean verifyCode(String code) {
        if(code.length() < 4 || code.length() > 8) {
            return  false;
        }
        if(code.length() - code.replaceAll("[^A-Za-z0-9]", "").length() != 0) {
            return  false;
        }
        return true;
    }

    /**
     * Verifies that a String contains a valid decimal
     * @param decimal String of possible decimal
     * @return If the String is a valid decimal
     */
    public static boolean verifyDecimal(String decimal) {
        if(decimal == null || decimal.length() == 0) { return false; }
        if(decimal.length() - decimal.replace(".", "").length() > 1) { return  false; }
        if(decimal.length() - decimal.replaceAll("[^\\d.]", "").length() != 0) { return false; }
        return true;
    }

    /**
     * Verifies that a String contains a valid date in MM/DD/YYYY format
     * @param date String of possible date
     * @return If the String is a valid date
     */
    public static boolean verifyDate(String date) {

        if(date == null || date.length() == 0) { return false; }

        //Replaces dashes with slashes and remove whitespace
        date = date.replaceAll("-" , "/");
        date = date.replaceAll("\\s","");

        //Check that there are exactly two slashes
        if(date.length() - date.replace("/", "").length() != 2) {
            return  false;
        }

        //Split date into parts
        String[] parts = date.split("/", 0);

        //Check each segments length, allow months and days to be 1 or 2 digits and years 2 or 4
        if(parts[0].length() != 1 && parts[0].length() != 2) { return false; }
        if(parts[1].length() != 1 && parts[1].length() != 2) { return false; }
        if(parts[2].length() != 2 && parts[2].length() != 4) { return false; }

        return true;
    }

    /**
     * Verifies that a String is a valid email and displays an error Toast if not
     * @param email String of possible email
     * @param context Context to make Toast
     * @return If the String is a valid email
     */
    public static boolean verifyEmail(String email, Context context) {

        //Currently only check if the email is not null, could check for @ later, but not necessary
        if(email.equals("") || email == null) {
            Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Verifies that a String is a valid username and displays an error Toast if not
     * @param name String of possible username
     * @param context Context to make Toast
     * @return If the String is a valid username
     */
    public static boolean verifyUserName(String name, Context context) {

        //Check username is not nothing
        if(name == null || name.equals("")) {
            Toast.makeText(context, "Enter Username", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check username is not too long
        if (name.length() > 10) {
            Toast.makeText(context, "Username too long", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check username is alphanumerical and spaces
        if(!name.replaceAll("\\s+","").matches("[a-zA-Z0-9']*")) {
            Toast.makeText(context, "Username must be alphanumerical", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Verifies that a String is a valid password and displays an error Toast if not
     * @param password String of possible password
     * @param context Context to make Toast
     * @return If the String is a valid password
     */
    public static boolean verifyPassword(String password, Context context) {

        //Check password is not nothing
        if(password == null || password.equals("")) {
            Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check password is not too short
        if(password.length() < 6) {
            Toast.makeText(context, "Password too short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Check password is alphanumerical, possibly change this check
        if(!password.matches("[a-zA-Z0-9]*")) {
            Toast.makeText(context, "Password must be alphanumerical", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
