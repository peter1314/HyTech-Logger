package com.peter.wagstaff.hytechlogger.customviews;

import android.content.Context;
import android.widget.LinearLayout;

public interface SelfConfiguring{

    Context getContext();

    default int dpToPixels(int dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    default LinearLayout.LayoutParams getParams(){
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    default LinearLayout.LayoutParams getParams(int marginLeft, int marginTop, int marginRight, int marginBottom){
        LinearLayout.LayoutParams params = getParams();
        params.setMargins(dpToPixels(marginLeft), dpToPixels(marginTop), dpToPixels(marginRight), dpToPixels(marginBottom));
        return params;
    }

    default LinearLayout.LayoutParams getStretchParams(){
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    default LinearLayout.LayoutParams getStretchParams(int marginLeft, int marginTop, int marginRight, int marginBottom){
        LinearLayout.LayoutParams params = getStretchParams();
        params.setMargins(dpToPixels(marginLeft), dpToPixels(marginTop), dpToPixels(marginRight), dpToPixels(marginBottom));
        return params;
    }
}
