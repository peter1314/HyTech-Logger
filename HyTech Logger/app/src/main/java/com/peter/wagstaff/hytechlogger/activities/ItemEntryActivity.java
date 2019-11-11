package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.holders.AttributeTable;
import com.peter.wagstaff.hytechlogger.customFragments.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.customFragments.holders.LocationSpinner;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntryBuilder;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.LocationTest;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.locations.Location;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for creating an ItemEntry
public class ItemEntryActivity extends AppCompatActivity {

    private boolean isNew = true;   //Stores if this is an entry on a new item
    private ItemType ENTRY_TYPE = getItemType(); //The current Global ItemType and of the current ItemEntry
    private TextView headerText; //Header text displaying the current ItemType and code
    private AttributeTable<EditText> inputTable;    //Table to display and enter Attributes
    private Attribute[] RowAttributes;  //Attributes to be displayed and entered as rows on the inputTable
    private List<EditText> inputEditTexts = new LinkedList(); //List of EditTexts on the inputTable
    private RadioGroup locationButtonGroup; //Group of RadioButtons to enter the ItemEntry's Location
    private List<LocationRadioButton> locationRadioButtons = new LinkedList<>(); //List of LocationRadioButtons in the locationButtonGroup
    private LocationSpinner locationSpinner; //LocationSpinner to enter the ItemEntry's Location
    private Button enterButton; //Button to submit this ItemEntry


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_entry);

        headerText = findViewById(R.id.cell_code_textView);
        inputTable = findViewById(R.id.table_layout);
        locationButtonGroup = findViewById(R.id.location_radioGroup);
        locationSpinner = findViewById(R.id.spinner);
        enterButton = findViewById(R.id.enter_button);

        //Set the header text to the type and code of the current item
        headerText.setText(ENTRY_TYPE.NAME + " " + GlobalVariables.currentItemEntryCode);

        //Populate the row attributes
        populateRowAttributes();

        //Populate the location buttons
        populateLocationButtons();

        //Sets the fields based on the last ItemEntry for this item
        setEntryUpdate();

        //Add listener on enterButton to submit the ItemEntry and go to the next Intent
        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Create a new ItemEntry from the user's inputs
                ItemEntry newEntry = buildEntryFromInputs();
                //Stop if the ItemEntry is not valid
                if(newEntry == null) return;

                //Get the current date in YYYYMMDD format
                String timeStamp = InputFormatting.ORDER_DATE_FORMAT.format(Calendar.getInstance().getTime());
                //Put the new entry in the Firebase database at its timestamp
                FirebaseExchange.addDataEntry(timeStamp, newEntry);

                //Goes to a new Intent or back depending on if the ItemEntry was on a new item
                if(isNew){
                    startActivity(nextIntent());
                    finish();
                } else {
                    onBackPressed();
                }
            }
        });
    }

    /**
     * Populates the row Attributes based on the current ItemType
     */
    private void populateRowAttributes() {
        inputTable.setTools(getLayoutInflater(), R.layout.fragment_input_text);
        RowAttributes = ENTRY_TYPE.ROW_ATTRIBUTES;
        for(Attribute attribute: RowAttributes) {
            inputEditTexts.add(inputTable.addRow(attribute));
        }
    }

    /**
     * Populates the LocationButtons based on the current ItemType
     */
    private void populateLocationButtons() {
        locationRadioButtons.clear();
        for(LocationConfiguration currentConfig: getItemType().LOCATION_CONFIGS) {
            LocationRadioButton currentButton = new LocationRadioButton(this, currentConfig);
            currentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationSpinner.populate(currentButton.getOptions());
                }
            });
            locationRadioButtons.add(currentButton);
            locationButtonGroup.addView(currentButton);

        }
    }

    /**
     * Creates a Firebase listener to grab the last ItemEntry for this item and initializes fields with it
     */
    private void setEntryUpdate() {
        FirebaseExchange.onGrab(ENTRY_TYPE.BRANCH + "/" + GlobalVariables.currentItemEntryCode + "/LOGS/LAST", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    initializeFieldsFromEntry(FirebaseExchange.entryFromSnapshot(ENTRY_TYPE, snapshot));
                    //Remember if the item is not new
                    isNew = false;
                }
            }
        });
    }

    /**
     * Initializes the activity's fields from an ItemEntry
     * @param entry ItemEntry to initialize fields with
     */
    private void initializeFieldsFromEntry(ItemEntry entry) {
        for(int i = 0; i < ENTRY_TYPE.ROW_ATTRIBUTES.length; i++) {
            inputEditTexts.get(i).setText(entry.getData(ENTRY_TYPE.ROW_ATTRIBUTES[i].KEY));
        }
        Location location = Location.buildLocation(entry.getData(Attributes.LOCATION.KEY));
        initializeLocationButtons(location);
        locationSpinner.populate(location.getOptions());
        locationSpinner.setSelection(location.getCurrentOption());
    }

    /**
     * Initializes the LocationButtons based on a provided location
     * @param location Location to intializes the buttons with
     */
    private void initializeLocationButtons(Location location) {

        for(LocationRadioButton currentButton: locationRadioButtons) {
            LocationTest buttonTest = new LocationTest(Attributes.LOCATION.KEY, currentButton.CONFIG);
            if(buttonTest.testLocation(location)) {
                currentButton.setChecked(true);
            }
        }
    }

    /**
     * Builds an ItemEntry based on the user input
     * @return The finished ItemEntry or null if it failed
     */
    private ItemEntry buildEntryFromInputs() {
        ItemEntryBuilder itemEntryBuilder = new ItemEntryBuilder(getItemType());

        //Set generic Attributes
        itemEntryBuilder.setString(Attributes.CODE.KEY, GlobalVariables.currentItemEntryCode);
        itemEntryBuilder.setString(Attributes.ENTRY_DATE.KEY, InputFormatting.READ_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        itemEntryBuilder.setString(Attributes.AUTHOR.KEY, FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        //Set row Attributes
        for(int i = 0; i < RowAttributes.length; i++) {
            if(!itemEntryBuilder.setAttribute(RowAttributes[i], String.valueOf(inputEditTexts.get(i).getText()))) {
                //Display appropriate error and return null if any Attribute is set invalidly
                Toast.makeText(getApplicationContext(), "Invalid " + RowAttributes[i].NAME, Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        //Build a Location based on the selected LocationButton
        Location entryLocation = buildLocation();
        if(entryLocation == null) {
            //Display error and return null if no LocationButton was selected
            Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
            return null;
        }

        //Set the location
        itemEntryBuilder.setJSONObject(Attributes.LOCATION.KEY, entryLocation.toDict());

        //Return the finished ItemEntry
        return itemEntryBuilder.getItemEntry();
    }

    /**
     * Builds a Location based on the selected LocationButton and the LocationSpinner
     * @return
     */
    private Location buildLocation() {
        Location itemLocation = null;

        for(LocationRadioButton currentButton: locationRadioButtons) {
            if(currentButton.isChecked()) {
                itemLocation = currentButton.CONFIG.ASSOCIATED_LOCATION;
            }
        }
        if(itemLocation == null) return null;

        itemLocation.addSpinnerInput(locationSpinner.getSelectedItem().toString());
        return itemLocation;
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
    private Intent nextIntent() {
        Intent nextIntent = new Intent(ItemEntryActivity.this, ViewItemActivity.class);
        nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return nextIntent;
    }
}
