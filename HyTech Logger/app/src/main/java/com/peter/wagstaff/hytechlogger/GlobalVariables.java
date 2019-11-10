package com.peter.wagstaff.hytechlogger;

import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;

//class for storing global variables, use only if necessary
public class GlobalVariables {
    //stores the code of the currently entry, useful for when passing from one activity to another
    public static String currentEntryCode;

    public static ItemType currentType;

    public static final ItemType[] ACTIVE_ITEM_TYPES = {
            CellType.getInstance(),
            StockType.getInstance()
    } ;

    //stores if the user just logged out, used to send them to login screen after logout
    public static boolean justLoggedOut = false;
}
