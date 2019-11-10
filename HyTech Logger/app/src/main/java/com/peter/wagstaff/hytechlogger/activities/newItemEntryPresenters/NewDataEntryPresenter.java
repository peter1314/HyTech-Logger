package com.peter.wagstaff.hytechlogger.activities.newItemEntryPresenters;

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
import com.peter.wagstaff.hytechlogger.customFragments.holders.EntryTableLayout;
import com.peter.wagstaff.hytechlogger.customFragments.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.customFragments.holders.LocationSpinner;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntryBuilder;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputFormatting;
import com.peter.wagstaff.hytechlogger.location.Location;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public abstract class NewDataEntryPresenter extends AppCompatActivity {

    private boolean isNew = true;
    private String branch;
    private Attribute[] cellRowAttributes;

    private EntryTableLayout<EditText> inputTable;
    private TextView entryCodeText;
    private List<EditText> inputEditTexts = new LinkedList();
    private RadioGroup locationButtonGroup;
    List<LocationRadioButton> locationRadioButtions = new LinkedList<>();
    LocationSpinner locationSpinner;
    private Button enterButton;

    private final ItemEntry NEW_ENTRY = getEntry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        entryCodeText = findViewById(R.id.cell_code_textView);
        entryCodeText.setText(NEW_ENTRY.getType() + " " + GlobalVariables.currentEntryCode);
        inputTable = findViewById(R.id.table_layout);
        inputTable.setTools(getLayoutInflater(), R.layout.fragment_input_text);
        locationButtonGroup = findViewById(R.id.location_radioGroup);

        locationSpinner = findViewById(R.id.spinner);
        enterButton = findViewById(R.id.enter_button);

        cellRowAttributes = NEW_ENTRY.getRowAttributes();
        for(Attribute attribute: cellRowAttributes) {
            inputEditTexts.add(inputTable.addRow(attribute.DISPLAY, attribute.DEFAULT, attribute.INPUT_TYPE));
        }

        setLocationButtons();
        for(final LocationRadioButton locationButton: locationRadioButtions) {
            locationButtonGroup.addView((locationButton));
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationSpinner.populate(locationButton.getOptions());
                }
            });

        }

        branch = NEW_ENTRY.getBranch();
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
        for(int i = 0; i < entry.getRowAttributes().length; i++) {
            inputEditTexts.get(i).setText(entry.getData(entry.getRowAttributes()[i].KEY));
        }

        Location location = Location.buildLocation(entry.getData(ItemEntry.LOCATION.KEY));
        updateLocationButtons(location);
        locationSpinner.populate(location.getOptions());
        locationSpinner.setSelection(location.getCurrentOption());
    }

    private ItemEntry buildEntryFromInputs() {
        ItemEntryBuilder itemEntryBuilder = new ItemEntryBuilder(getEntry());

        itemEntryBuilder.setString(ItemEntry.CODE.KEY, GlobalVariables.currentEntryCode);
        itemEntryBuilder.setString(ItemEntry.ENTRY_DATE.KEY, InputFormatting.READ_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        itemEntryBuilder.setString(ItemEntry.AUTHOR.KEY, FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

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
        itemEntryBuilder.setJSONObject(ItemEntry.LOCATION.KEY, entryLocation.toDict());

        return itemEntryBuilder.getEntry();
    }

    abstract int getContentView();

    abstract ItemEntry getEntry();

    abstract Intent nextIntent();

    abstract Location buildLocation();

    abstract void setLocationButtons();

    abstract void updateLocationButtons(Location location);
}
