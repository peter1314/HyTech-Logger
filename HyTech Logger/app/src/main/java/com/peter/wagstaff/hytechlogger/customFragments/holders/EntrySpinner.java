package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.AppCompatSpinner;

//Spinner to display ItemEntries associated with an item by date
public class EntrySpinner extends AppCompatSpinner {

    /**
     * Declare EntrySpinner
     * @param context
     */
    public EntrySpinner(Context context) {
        super(context);
    }

    /**
     * Declare EntrySpinner
     * @param context
     * @param attrs
     */
    public EntrySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Declare EntrySpinner
     * @param context
     * @param attrs
     * @param defStyle
     */
    public EntrySpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Populates the spinner given a snapshot of an items "LOGS" child in the Firebase database
     * @param snapshot DatabaseSnapshot of the "LOGS" child of an item
     */
    public void populate(DataSnapshot snapshot) {
        List<String> entryDates = new ArrayList<>();

        //Perhaps I should better handle if an invalid snapshot was provided here
        for(DataSnapshot child: snapshot.getChildren()) {
            try {
                JSONObject curEntryJSON = new JSONObject(child.getValue().toString());
                entryDates.add(0, curEntryJSON.getString(Attributes.ENTRY_DATE.KEY));
            } catch (JSONException e) {}
        }
        //Remove last entry to ignore the LAST ItemEntry
        entryDates.remove(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.fragment_spinner_item, entryDates.toArray(new String[entryDates.size()]));
        setAdapter(adapter);
    }
}
