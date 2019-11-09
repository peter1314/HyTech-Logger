package com.peter.wagstaff.hytechlogger.activities;

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
import com.peter.wagstaff.hytechlogger.activities.rowinjection.EntryTableLayout;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.LocationRadioButton;
import com.peter.wagstaff.hytechlogger.customviews.LocationSpinner;
import com.peter.wagstaff.hytechlogger.dataentry.Attribute;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
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
    List<LocationRadioButton> locationRadioButtions = new LinkedList<>();
    LocationSpinner locationSpinner;
    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        entryCodeText = findViewById(R.id.cell_code_textView);
        entryCodeText.setText(getType() + " " + GlobalVariables.currentEntryCode);
        inputTable = findViewById(R.id.table_layout);
        inputTable.setTools(getLayoutInflater(), R.layout.input_text);
        locationSpinner = findViewById(R.id.spinner);
        enterButton = findViewById(R.id.enter_button);

        cellRowAttributes = getRowAttributes();
        for(Attribute attribute: cellRowAttributes) {
            inputEditTexts.add(inputTable.addRow(attribute.DISPLAY, attribute.DEFAULT, attribute.INPUT_TYPE));
        }

        branch = getBranch();
        FirebaseExchange.onGrab(FirebaseExchange.TREE + "/" + branch + "/" + GlobalVariables.currentEntryCode + "/LOGS/LAST", new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    populateFieldsFromEntry(FirebaseExchange.entryFromSnapshot(branch, snapshot));
                    isNew = false;
                }
            }
        });

        addLocationButtons();
        for(final LocationRadioButton locationButton: locationRadioButtions) {
            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationSpinner.populate(locationButton.getOptions());
                }
            });
        }

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
        for(int i=0; i < entry.rowAttributes().length; i++) {
            inputEditTexts.get(i).setText(entry.getData(entry.rowAttributes()[i].ID));
        }

        Location location = LocationBuilder.buildLocation(entry.getData(DataEntry.LOCATION.toString()));
        updateLocationButtons(location);
        locationSpinner.populate(location.getOptions());
        locationSpinner.setSelection(location.getCurrentOption());
    }

    private DataEntry buildEntryFromInputs() {
        DataEntryBuilder<DataEntry> cellEntryBuilder = new DataEntryBuilder();

        cellEntryBuilder.addString(DataEntry.CODE.ID, GlobalVariables.currentEntryCode);
        cellEntryBuilder.addString(DataEntry.ENTRY_DATE.toString(), InputFormating.READ_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        cellEntryBuilder.addString(DataEntry.AUTHOR.toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        for(int i = 0; i < cellRowAttributes.length; i++) {
            if(!cellEntryBuilder.addAttribute(cellRowAttributes[i], String.valueOf(inputEditTexts.get(i).getText()))) {
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
        cellEntryBuilder.addJSONObject(DataEntry.LOCATION.ID, entryLocation.toDict());

        return cellEntryBuilder.buildEntry();
    }

    abstract int getContentView();

    abstract String getType();

    abstract String getBranch();

    abstract Attribute[] getRowAttributes();

    abstract Intent nextIntent();

    abstract Location buildLocation();

    abstract void addLocationButtons();

    abstract void updateLocationButtons(Location location);
}
