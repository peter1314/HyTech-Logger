package com.peter.wagstaff.hytechlogger.activities.NewDataEntryActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customviews.holders.EntryTableLayout;
import com.peter.wagstaff.hytechlogger.customviews.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.customviews.holders.MyRadioGroup;
import com.peter.wagstaff.hytechlogger.customviews.holders.LocationSpinner;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntryBuilder;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public abstract class NewDataEntryActivity extends AppCompatActivity {

    private boolean isNew = true;
    private String branch;
    private Attribute[] cellRowAttributes;

    private EntryTableLayout<EditText> inputTable;
    private TextView entryCodeText;
    private List<EditText> inputEditTexts = new LinkedList();
    private MyRadioGroup locationButtonGroup;
    List<LocationRadioButton> locationRadioButtions = new LinkedList<>();
    LocationSpinner locationSpinner;
    private Button enterButton;

    private final DataEntry NEW_ENTRY = getEntry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        entryCodeText = findViewById(R.id.cell_code_textView);
        entryCodeText.setText(NEW_ENTRY.getType() + " " + GlobalVariables.currentEntryCode);
        inputTable = findViewById(R.id.table_layout);
        inputTable.setTools(getLayoutInflater(), R.layout.input_text);
        locationButtonGroup = findViewById(R.id.location_radioGroup);

        locationSpinner = findViewById(R.id.spinner);
        enterButton = findViewById(R.id.enter_button);

        cellRowAttributes = NEW_ENTRY.getRowAttributes();
        for(Attribute attribute: cellRowAttributes) {
            inputEditTexts.add(inputTable.addRow(attribute.DISPLAY, attribute.DEFAULT, attribute.INPUT_TYPE));
        }

        setLocationButtons();
        for(final LocationRadioButton locationButton: locationRadioButtions) {
            locationButtonGroup.addRadioButton((locationButton));
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationSpinner.populate(locationButton.getOptions());
                }
            });

        }

        branch = NEW_ENTRY.getBranch();
        FirebaseExchange.onGrab(FirebaseExchange.TREE + "/" + branch + "/" + GlobalVariables.currentEntryCode + "/LOGS/LAST", new DataUpdateAction() {
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
                DataEntry entry = buildEntryFromInputs();
                if(entry == null) return;

                String timeStamp = InputFormating.ORDER_DATE_FORMAT.format(Calendar.getInstance().getTime());
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

    void populateFieldsFromEntry(DataEntry entry) {
        for(int i = 0; i < entry.getRowAttributes().length; i++) {
            inputEditTexts.get(i).setText(entry.getData(entry.getRowAttributes()[i].ID));
        }

        Location location = LocationBuilder.buildLocation(entry.getData(DataEntry.LOCATION.toString()));
        updateLocationButtons(location);
        locationSpinner.populate(location.getOptions());
        locationSpinner.setSelection(location.getCurrentOption());
    }

    private DataEntry buildEntryFromInputs() {
        DataEntryBuilder dataEntryBuilder = new DataEntryBuilder(getEntry());

        dataEntryBuilder.addString(DataEntry.CODE.ID, GlobalVariables.currentEntryCode);
        dataEntryBuilder.addString(DataEntry.ENTRY_DATE.toString(), InputFormating.READ_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        dataEntryBuilder.addString(DataEntry.AUTHOR.toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        for(int i = 0; i < cellRowAttributes.length; i++) {
            if(!dataEntryBuilder.addAttribute(cellRowAttributes[i], String.valueOf(inputEditTexts.get(i).getText()))) {
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
        dataEntryBuilder.addJSONObject(DataEntry.LOCATION.ID, entryLocation.toDict());

        return dataEntryBuilder.buildEntry();
    }

    abstract int getContentView();

    abstract DataEntry getEntry();

    abstract Intent nextIntent();

    abstract Location buildLocation();

    abstract void setLocationButtons();

    abstract void updateLocationButtons(Location location);
}
