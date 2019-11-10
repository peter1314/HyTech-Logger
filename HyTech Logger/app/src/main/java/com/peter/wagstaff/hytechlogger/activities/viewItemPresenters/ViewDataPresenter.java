package com.peter.wagstaff.hytechlogger.activities.viewItemPresenters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.holders.EntryTableLayout;
import com.peter.wagstaff.hytechlogger.customFragments.holders.EntrySpinner;
import com.peter.wagstaff.hytechlogger.customFragments.LockedEditText;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.location.Location;

import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ViewDataPresenter extends AppCompatActivity {

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
        setContentView(R.layout.activity_view_data);

        cellCodeText = findViewById(R.id.cell_code_textView);
        cellCodeText.setText(getType().NAME + " " + GlobalVariables.currentEntryCode);
        entryText = findViewById(R.id.entry_textView);
        displayTable = findViewById(R.id.table_layout);
        displayTable.setTools(getLayoutInflater(), R.layout.fragment_locked_input_text);

        entrySpinner = findViewById(R.id.spinner);
        newEntryButton = findViewById(R.id.new_entry_button);

        cellRowAttributes = getType().ROW_ATTRIBUTES;
        for(Attribute attribute: cellRowAttributes) {
            rowEditTexts.add(displayTable.addRow(attribute.DISPLAY));
        }
        locationEditText = displayTable.addRow(Attributes.LOCATION.DISPLAY);

        branch = getType().BRANCH;
        FirebaseExchange.onUpdate( branch + "/" + GlobalVariables.currentEntryCode + "/LOGS", new DataUpdateAction() {
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

                FirebaseExchange.onUpdate(branch + "/" + GlobalVariables.currentEntryCode + "/LOGS/" + InputFormatting.orderedDate(date), new DataUpdateAction() {
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

    private void populateFieldsFromEntry(ItemEntry entry) {
        entryText.setText(entry.getData(Attributes.AUTHOR.KEY) + ": " + entry.getData(Attributes.ENTRY_DATE.KEY));
        for(int i = 0; i < entry.getType().ROW_ATTRIBUTES.length; i++) {
            rowEditTexts.get(i).setText(entry.getData(entry.getType().ROW_ATTRIBUTES[i].KEY));
        }
        Location location = Location.buildLocation(entry.getData(Attributes.LOCATION.KEY));
        locationEditText.setText(location.fancyPrint());
    }

    abstract ItemType getType();

    abstract Intent nextIntent();
}
