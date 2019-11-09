package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.EntryTableLayout;
import com.peter.wagstaff.hytechlogger.customviews.EntrySpinner;
import com.peter.wagstaff.hytechlogger.customviews.LockedEditText;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ViewDataActivity extends AppCompatActivity {

    private String branch;
    private Attribute[] cellRowAttributes;

    private TextView cellCodeText, entryText;
    private EntryTableLayout<LockedEditText> displayTable;
    private List<LockedEditText> rowEditTexts = new LinkedList();
    private LockedEditText locationEditText;
    private EntrySpinner entrySpinner;
    private Button newEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        cellCodeText = findViewById(R.id.cell_code_textView);
        cellCodeText.setText(getType() + " " + GlobalVariables.currentEntryCode);
        entryText = findViewById(R.id.entry_textView);
        displayTable = findViewById(R.id.table_layout);
        displayTable.setTools(getLayoutInflater(), R.layout.locked_input_text);

        entrySpinner = findViewById(R.id.spinner);
        newEntryButton = findViewById(R.id.new_entry_button);

        cellRowAttributes = getRowAttributes();
        for(Attribute attribute: cellRowAttributes) {
            rowEditTexts.add(displayTable.addRow(attribute.DISPLAY));
        }
        locationEditText = displayTable.addRow(DataEntry.LOCATION.DISPLAY);

        branch = getBranch();
        FirebaseExchange.onUpdate(FirebaseExchange.TREE + "/" + branch + "/" + GlobalVariables.currentEntryCode + "/LOGS", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    populateFieldsFromEntry(FirebaseExchange.entryFromSnapshot(branch, snapshot.child("LAST")));
                    entrySpinner.populate(snapshot);
                }
            }
        });

        entrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String date = entrySpinner.getSelectedItem().toString();

                FirebaseExchange.onUpdate(FirebaseExchange.TREE + "/" + branch + "/" + GlobalVariables.currentEntryCode + "/LOGS/" + InputFormating.orderedDate(date), new DataUpdateAction() {
                    @Override
                    public void doAction(DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            populateFieldsFromEntry(FirebaseExchange.entryFromSnapshot(branch, snapshot));
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        newEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = nextIntent();
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    private void populateFieldsFromEntry(DataEntry entry) {
        entryText.setText(entry.getData(DataEntry.AUTHOR.ID) + ": " + entry.getData(DataEntry.ENTRY_DATE.ID));
        for(int i =0; i < entry.rowAttributes().length; i++) {
            rowEditTexts.get(i).setText(entry.getData(entry.rowAttributes()[i].ID));
        }
        Location location = LocationBuilder.buildLocation(entry.getData(DataEntry.LOCATION.ID));
        locationEditText.setText(location.fancyPrint());
    }

    abstract int getContentView();

    abstract String getType();

    abstract String getBranch();

    abstract Attribute[] getRowAttributes();

    abstract Intent nextIntent();
}
