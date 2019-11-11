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
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public class NewItemEntryPresenter extends AppCompatActivity {

    private boolean isNew = true;
    private String branch;
    private Attribute[] cellRowAttributes;

    private AttributeTable<EditText> inputTable;
    private TextView entryCodeText;
    private List<EditText> inputEditTexts = new LinkedList();
    private RadioGroup locationButtonGroup;
    List<LocationRadioButton> locationRadioButtons = new LinkedList<>();
    LocationSpinner locationSpinner;
    private Button enterButton;

    private final ItemType ENTRY_TYPE = getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_entry);

        entryCodeText = findViewById(R.id.cell_code_textView);
        entryCodeText.setText(ENTRY_TYPE.NAME + " " + GlobalVariables.currentEntryCode);
        inputTable = findViewById(R.id.table_layout);
        inputTable.setTools(getLayoutInflater(), R.layout.fragment_input_text);
        locationButtonGroup = findViewById(R.id.location_radioGroup);

        locationSpinner = findViewById(R.id.spinner);
        enterButton = findViewById(R.id.enter_button);

        cellRowAttributes = ENTRY_TYPE.ROW_ATTRIBUTES;
        for(Attribute attribute: cellRowAttributes) {
            inputEditTexts.add(inputTable.addRow(attribute));
        }

        setLocationButtons();
        for(final LocationRadioButton locationButton: locationRadioButtons) {
            locationButtonGroup.addView((locationButton));
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationSpinner.populate(locationButton.getOptions());
                }
            });

        }

        branch = ENTRY_TYPE.BRANCH;
        FirebaseExchange.onGrab(branch + "/" + GlobalVariables.currentEntryCode + "/LOGS/LAST", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    populateFieldsFromEntry(FirebaseExchange.entryFromSnapshot(branch, snapshot));
                    isNew = false;
                }
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ItemEntry entry = buildEntryFromInputs();
                if(entry == null) return;

                String timeStamp = InputFormatting.ORDER_DATE_FORMAT.format(Calendar.getInstance().getTime());
                FirebaseExchange.addDataEntry(timeStamp, entry);

                if(isNew){
                    Intent intent = nextIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    onBackPressed();
                }
            }
        });
    }

    void populateFieldsFromEntry(ItemEntry entry) {
        for(int i = 0; i < ENTRY_TYPE.ROW_ATTRIBUTES.length; i++) {
            inputEditTexts.get(i).setText(entry.getData(ENTRY_TYPE.ROW_ATTRIBUTES[i].KEY));
        }

        Location location = Location.buildLocation(entry.getData(Attributes.LOCATION.KEY));
        updateLocationButtons(location);
        locationSpinner.populate(location.getOptions());
        locationSpinner.setSelection(location.getCurrentOption());
    }

    private ItemEntry buildEntryFromInputs() {
        ItemEntryBuilder itemEntryBuilder = new ItemEntryBuilder(getType());

        itemEntryBuilder.setString(Attributes.CODE.KEY, GlobalVariables.currentEntryCode);
        itemEntryBuilder.setString(Attributes.ENTRY_DATE.KEY, InputFormatting.READ_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        itemEntryBuilder.setString(Attributes.AUTHOR.KEY, FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        for(int i = 0; i < cellRowAttributes.length; i++) {
            if(!itemEntryBuilder.setAttribute(cellRowAttributes[i], String.valueOf(inputEditTexts.get(i).getText()))) {
                Toast.makeText(getApplicationContext(), "Invalid " + cellRowAttributes[i].NAME, Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        Location entryLocation = buildLocation();
        if(entryLocation == null) {
            Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
            return null;
        }

        entryLocation.addSpinnerInput(locationSpinner.getSelectedItem().toString());
        itemEntryBuilder.setJSONObject(Attributes.LOCATION.KEY, entryLocation.toDict());

        return itemEntryBuilder.getEntry();
    }

    private void setLocationButtons() {
        locationRadioButtons.clear();
        for(LocationConfiguration currentConfig: getType().LOCATION_CONFIGS) {
            locationRadioButtons.add(new LocationRadioButton(this, currentConfig));
        }
    }

    private void updateLocationButtons(Location location) {

        for(LocationRadioButton currentButton: locationRadioButtons) {
            LocationTest buttonTest = new LocationTest(Attributes.LOCATION.KEY, currentButton.CONFIG);
            if(buttonTest.testLocation(location)) {
                currentButton.setChecked(true);
            }
        }
    }

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

    private ItemType getType() { return GlobalVariables.currentType; }

    private Intent nextIntent() { return new Intent(NewItemEntryPresenter.this, ViewItemPresenter.class); }
}
