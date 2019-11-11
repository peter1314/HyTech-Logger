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
import com.peter.wagstaff.hytechlogger.customFragments.holders.AttributeTable;
import com.peter.wagstaff.hytechlogger.customFragments.holders.EntrySpinner;
import com.peter.wagstaff.hytechlogger.customFragments.LockedEditText;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.locations.Location;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for viewing an item's ItemEntries
public class ViewItemActivity extends AppCompatActivity {

    private String branch;  //Branch in the Firebase database of the current ItemType
    private TextView itemCodeText,  //TextView to display the item code
                        entryText;  //TextView to display the current entry's author and date
    private AttributeTable<LockedEditText> displayTable;    //Table to display an ItemEntry's Attributes
    private List<LockedEditText> rowEditTexts = new LinkedList();   //List of LockedEditTexts in the AttributeTable
    private LockedEditText locationEditText;    //LockedEditText to display an ItemEntry's Location
    private EntrySpinner entrySpinner;  //Spinner to display all ItemEntries for this the item
    private Button newEntryButton;  //Button to make new entry

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        itemCodeText = findViewById(R.id.cell_code_textView);
        entryText = findViewById(R.id.entry_textView);
        displayTable = findViewById(R.id.table_layout);
        displayTable.setTools(getLayoutInflater(), R.layout.fragment_locked_input_text);
        entrySpinner = findViewById(R.id.spinner);
        newEntryButton = findViewById(R.id.new_entry_button);

        //Set item code to ITEM.NAME CODE
        itemCodeText.setText(getItemType().NAME + " " + GlobalVariables.currentItemEntryCode);

        //Add all the row Attributes of the item's ItemType to rowEditTexts
        for(Attribute attribute: getItemType().ROW_ATTRIBUTES) {
            rowEditTexts.add(displayTable.addRow(attribute.DISPLAY));
        }

        locationEditText = displayTable.addRow(Attributes.LOCATION.DISPLAY);    //Add location as well

        setEntrySpinnerUpdate();    //Set entrySpinner using Firebase update
        setEntrySpinnerAction();    //Set the behavior when a spinner entry is selected

        //Add listener to newEntryButton to go to next Intent
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { startActivity(getNextIntent()); }
        });
    }

    /**
     * Creates a Firebase update that gets all entries under an Item and puts them in the ItemSpinner
     */
    private void setEntrySpinnerUpdate() {
        branch = getItemType().BRANCH;

        //Create a Firebase update to get all entries for an item
        FirebaseExchange.onUpdate( branch + "/" + GlobalVariables.currentItemEntryCode + "/LOGS", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    //Populate the fields with the item's last entry
                    populateFieldsFromEntry(FirebaseExchange.entryFromSnapshot(getItemType(), snapshot.child("LAST")));
                    //Populate the entry fields using all of the item's entries
                    entrySpinner.populate(snapshot);
                }
            }
        });
    }

    /**
     * Sets a listener on the entrySpinner which populates the fields based on the selected ItemEntry
     */
    private void setEntrySpinnerAction() {
        entrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Gets the MM/DD/YYYY date of the selected ItemEntry
                String date = entrySpinner.getSelectedItem().toString();
                //Reformats to YYYYMMDD, which is the code of the ItemEntry in the Firebase database
                String orderedDate = InputFormatting.orderedDate(date);

                //Grabs associated ItemEntry from the database and populates the fields using it
                FirebaseExchange.onGrab(branch + "/" + GlobalVariables.currentItemEntryCode + "/LOGS/" + orderedDate, new DataUpdateAction() {
                    @Override
                    public void doAction(DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            populateFieldsFromEntry(FirebaseExchange.entryFromSnapshot(getItemType(), snapshot));
                        }
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    /**
     * Populates the views fields from an ItemEntry
     * @param entry ItemEntry who's attributes will be displayed
     */
    private void populateFieldsFromEntry(ItemEntry entry) {
        entryText.setText(entry.getData(Attributes.AUTHOR.KEY) + ": " + entry.getData(Attributes.ENTRY_DATE.KEY));
        for(int i = 0; i < entry.getType().ROW_ATTRIBUTES.length; i++) {
            rowEditTexts.get(i).setText(entry.getData(entry.getType().ROW_ATTRIBUTES[i].KEY));
        }
        Location location = Location.buildLocation(entry.getData(Attributes.LOCATION.KEY));
        locationEditText.setText(location.fancyPrint());
    }

    /**
     * Get the current ItemType, contained in method in case of future change or overriding
     * @return Global currentItemType
     */
    private ItemType getItemType() { return GlobalVariables.currentItemType; }

    /**
     * Get the next Intent when an item is selected, contained in method in case of future change or overriding
     * @return The next Intent
     */
    private Intent getNextIntent() {
        Intent nextIntent = new Intent(ViewItemActivity.this, ItemEntryActivity.class);
        //The next Intent does not add to the history
        nextIntent.setFlags(nextIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        return nextIntent;
    }
}
