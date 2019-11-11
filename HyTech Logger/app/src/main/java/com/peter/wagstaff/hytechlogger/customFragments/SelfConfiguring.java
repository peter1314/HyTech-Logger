package com.peter.wagstaff.hytechlogger.customFragments;

import android.content.Context;
import android.widget.LinearLayout;

//Interface used to configure View Objects programmaticly
public interface SelfConfiguring{

    //This method does not need to be implemented in a View Object
    //This is because it exploits the existing getContext() method within View
    Context getContext();

    /**
     * Converts regular density independent pixels to regular pixels
     * @param dp Density independent pixels
     * @return Equivalent regular pixels
     */
    default int dpToPixels(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * Creates and returns a standard wrap content layout
     * @return Generated layout
     */
    default LinearLayout.LayoutParams getParams(){
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Creates and returns a wrap content layout with specified margins
     * @param marginLeft Left margin in dp
     * @param marginTop Top margin in dp
     * @param marginRight Right margin in dp
     * @param marginBottom Bottom margin in dp
     * @return Generated layout
     */
    default LinearLayout.LayoutParams getParams(int marginLeft, int marginTop, int marginRight, int marginBottom){
        LinearLayout.LayoutParams params = getParams();
        params.setMargins(dpToPixels(marginLeft), dpToPixels(marginTop), dpToPixels(marginRight), dpToPixels(marginBottom));
        return params;
    }

    /**
     * Creates and returns a layout that's width is match parent and height wrap content
     * @return Generated layout
     */
    default LinearLayout.LayoutParams getStretchParams(){
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Creates and returns a layout that's width is match parent and height wrap content with specified margins
     * @param marginLeft Left margin in dp
     * @param marginTop Top margin in dp
     * @param marginRight Right margin in dp
     * @param marginBottom Bottom margin in dp
     * @return Generated layout
     */
    default LinearLayout.LayoutParams getStretchParams(int marginLeft, int marginTop, int marginRight, int marginBottom){
        LinearLayout.LayoutParams params = getStretchParams();
        params.setMargins(dpToPixels(marginLeft), dpToPixels(marginTop), dpToPixels(marginRight), dpToPixels(marginBottom));
        return params;
    }
}
