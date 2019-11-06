package com.peter.wagstaff.hytechlogger;

import android.content.Context;
import android.widget.Toast;

public class GlobalFunctions {

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

    public static boolean verifyCode(String code) {
        if(code.length() < 4 || code.length() > 8) {
            return  false;
        }
        if(code.length() - code.replaceAll("[^A-Za-z0-9]", "").length() != 0) {
            return  false;
        }

        return true;
    }

    public static boolean verifyDouble(String dub) {

        if(dub == null || dub.length() == 0) {
            return false;
        }

        if(dub.length() - dub.replace(".", "").length() > 1) {
            return  false;
        }
        if(dub.length() - dub.replaceAll("[^\\d.]", "").length() != 0) {
            return false;
        }

        return true;
    }

    public static boolean verifyDate(String date) {

        if(date == null || date.length() == 0) {
            return false;
        }

        date = date.replaceAll("-" , "/");
        date = date.replaceAll("\\s","");

        if(date.length() - date.replace("/", "").length() != 2) {
            return  false;
        }

        String[] parts = date.split("/", 0);

        if(parts[0].length() != 1 && parts[0].length() != 2) {
            return false;
        }
        if(parts[1].length() != 1 && parts[1].length() != 2) {
            return false;
        }
        if(parts[2].length() != 2 && parts[2].length() != 4) {
            return false;
        }

        return true;
    }

    public static boolean checkUserEmailRequirements(String email, Context context) {

        if(email.equals("") || email == null) {
            Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkUserNameRequirements(String name, Context context) {

        if(name.equals("") || name == null) {
            Toast.makeText(context, "Enter Username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.length() > 16) {
            Toast.makeText(context, "Username too long, enter maximum of 16 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!name.replaceAll("\\s+","").matches("[a-zA-Z0-9']*")) {
            Toast.makeText(context, "Username can only contain letters, numbers, and spaces", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkUserPasswordRequirements(String password, Context context) {

        if(password.equals("") || password == null) {
            Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6) {
            Toast.makeText(context, "Password too short, enter minimum 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.matches("[a-zA-Z0-9]*")) {
            Toast.makeText(context, "Password can only contain letters and numbers", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
