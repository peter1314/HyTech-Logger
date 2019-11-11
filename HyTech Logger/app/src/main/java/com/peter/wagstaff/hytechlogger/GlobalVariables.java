package com.peter.wagstaff.hytechlogger;

import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.itemTypes.OtherType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;

//Class for storing global variables
public class GlobalVariables {

    //Tree of the database the application operates with
    //DO NOT CHANGE OR RISK CONTAMINATING OTHER'S DATA
    public static final String APP_ROOT = "WUNDERMAN_DEMO";

    //Stores the code of the currently ItemEntry
    public static String currentItemEntryCode;

    //Stores the ItemType the application is currently operating with
    public static ItemType currentItemType;

    //Stores the ItemTypes the application currently has available
    //ADD AND REMOVE ITEMTYPES HERE
    public static final ItemType[] ACTIVE_ITEM_TYPES = {
            CellType.getInstance(),
            StockType.getInstance(),
            OtherType.getInstance()
    } ;

    //Stores if the user just logged out
    //Used to send them to login screen after logout before Firebase removes authentication
    public static boolean justLoggedOut = false;
}
