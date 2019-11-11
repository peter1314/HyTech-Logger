package com.peter.wagstaff.hytechlogger.customFragments;

//Abstract class used to set a listener's action
public abstract class ListnerAction<InputObject> {

    /**
     * Undefined action to be performed using some input
     * @param input Object of this ListnerAction's type
     */
    public abstract void doAction(InputObject input);
}

