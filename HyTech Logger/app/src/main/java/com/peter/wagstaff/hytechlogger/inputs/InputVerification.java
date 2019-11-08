package com.peter.wagstaff.hytechlogger.inputs;

import android.content.Context;
import android.widget.Toast;

public class InputVerification {

    public static boolean verifyCode(String code) {
        if(code.length() < 4 || code.length() > 8) {
            return  false;
        }
        if(code.length() - code.replaceAll("[^A-Za-z0-9]", "").length() != 0) {
            return  false;
        }

        return true;
    }

    public static boolean verifyDecimal(String dec) {

        if(dec == null || dec.length() == 0) {
            return false;
        }

        if(dec.length() - dec.replace(".", "").length() > 1) {
            return  false;
        }
        if(dec.length() - dec.replaceAll("[^\\d.]", "").length() != 0) {
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
