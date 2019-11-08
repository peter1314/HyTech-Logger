package com.peter.wagstaff.hytechlogger.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;

import org.json.JSONException;

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
            CellDataEntry curEntry = null;
            try {
                curEntry = new CellDataEntry(child.getValue().toString());
                entryDates.add(0, curEntry.getData(CellDataEntry.ENTRY_DATE));
            } catch (JSONException e) {}
        }
        entryDates.remove(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, entryDates.toArray(new String[entryDates.size()]));
        setAdapter(adapter);
    }
}
