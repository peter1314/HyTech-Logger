package com.peter.wagstaff.hytechlogger.customFragments.holders;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.AppCompatSpinner;

public class EntrySpinner extends AppCompatSpinner {

    public EntrySpinner(Context context) {
        super(context);
    }

    public EntrySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EntrySpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void populate(DataSnapshot snapshot) {
        List<String> entryDates = new ArrayList<>();

        for(DataSnapshot child: snapshot.getChildren()) {
            try {
                JSONObject curEntryJSON = new JSONObject(child.getValue().toString());
                entryDates.add(0, curEntryJSON.getString(ItemEntry.ENTRY_DATE.KEY));
            } catch (JSONException e) {}
        }
        entryDates.remove(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.fragment_spinner_item, entryDates.toArray(new String[entryDates.size()]));
        setAdapter(adapter);
    }
}
