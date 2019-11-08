package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.customviews.LocationSpinner;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.firebase.UpdateAction;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import org.json.JSONException;
import java.util.Calendar;
import com.peter.wagstaff.hytechlogger.location.AccumulatorLocation;
import com.peter.wagstaff.hytechlogger.location.CabinetLocation;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;
import com.peter.wagstaff.hytechlogger.location.OtherLocation;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntryBuilder;
import androidx.appcompat.app.AppCompatActivity;

public class NewCellEntryActivity extends AppCompatActivity {

    private boolean isNew = true;
    EditText voltageEditText, voltageRecordedEditText, dischargeEditText, irEditText, dischargeRecordedEditText, lastChargedEditText;
    RadioButton cabinetButton, ht04Button, ht05Button, otherButton;
    LocationSpinner locationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cell_entry);

        TextView cellCodeText = findViewById(R.id.cell_code_textView);
        cellCodeText.setText("Cell " + GlobalVariables.currentCellCode);

        voltageEditText = findViewById(R.id.voltage_editText);
        voltageRecordedEditText = findViewById(R.id.voltage_recorded_editText);
        dischargeEditText = findViewById(R.id.capacity_editText);
        irEditText = findViewById(R.id.ir_editText);
        dischargeRecordedEditText = findViewById(R.id.min_voltage_editText);
        lastChargedEditText = findViewById(R.id.last_charged_editText);

        cabinetButton = findViewById(R.id.cabinet_radioButton);
        ht04Button = findViewById(R.id.ht04_radioButton);
        ht05Button = findViewById(R.id.ht05_radioButton);
        otherButton = findViewById(R.id.other_radioButton);

        locationSpinner = findViewById(R.id.spinner);

        final Button enterButton = findViewById(R.id.enter_button);

        FirebaseExchange.onGrab(FirebaseExchange.BRANCH + "/" + GlobalVariables.currentCellCode + "/LOGS/LAST", new UpdateAction() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    try {
                        isNew = false;
                        populateFieldsFromEntry(new CellDataEntry(snapshot.getValue().toString()));
                    } catch (JSONException e) {}
                }
            }
        });

        cabinetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { locationSpinner.populate(CabinetLocation.OPTIONS);}
        });
        ht04Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { locationSpinner.populate(AccumulatorLocation.OPTIONS); }});
        ht05Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { locationSpinner.populate(AccumulatorLocation.OPTIONS); }});
        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { locationSpinner.populate(OtherLocation.OPTIONS); }});

        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CellDataEntry entry = buildEntryFromInputs();
                if(entry == null) return;

                String timeStamp = InputFormating.ORDER_DATE_FORMAT.format(Calendar.getInstance().getTime());
                FirebaseExchange.addDataEntry(timeStamp, entry);

                if(isNew){
                    Intent intent = new Intent(NewCellEntryActivity.this, ViewCellActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    onBackPressed();
                }
            }
        });
    }

    private void populateFieldsFromEntry(CellDataEntry entry) {
        voltageEditText.setText(entry.getData(CellDataEntry.VOLTAGE));
        voltageRecordedEditText.setText((entry.getData(CellDataEntry.VOLTAGE_DATE)));
        dischargeEditText.setText(entry.getData(CellDataEntry.DISCHARGE_CAP));
        irEditText.setText(entry.getData(CellDataEntry.INTERNAL_RES));
        dischargeRecordedEditText.setText(entry.getData(CellDataEntry.CAPACITY_DATE));
        lastChargedEditText.setText(entry.getData(CellDataEntry.CHARGE_DATE));

        Location location = LocationBuilder.buildLocation(entry.getData(CellDataEntry.LOCATION));

        if(location.getType().equals("cabinet")) {
            cabinetButton.setChecked(true);
        } else if(location.getType().equals("accumulator")) {
            if(((AccumulatorLocation) location).getIteration() == 4) {
                ht04Button.setChecked(true);
            } else if(((AccumulatorLocation) location).getIteration() == 5) {
                ht05Button.setChecked(true);
            }
        } else if(location.getType().equals("other")) {
            otherButton.setChecked(true);
        }
        locationSpinner.populate(location.getOptions());
        locationSpinner.setSelection(location.getCurrentOption());
    }

    private CellDataEntry buildEntryFromInputs() {
        CellDataEntryBuilder entryBuilder = new CellDataEntryBuilder();

        entryBuilder.addString(CellDataEntry.CODE, GlobalVariables.currentCellCode);
        entryBuilder.addString(CellDataEntry.ENTRY_DATE, InputFormating.READ_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        entryBuilder.addString(CellDataEntry.AUTHOR, FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        if (!entryBuilder.addDecimal(CellDataEntry.VOLTAGE, String.valueOf(voltageEditText.getText()))) {
            Toast.makeText(getApplicationContext(), "Invalid Voltage", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!entryBuilder.addDate(CellDataEntry.VOLTAGE_DATE, String.valueOf(voltageRecordedEditText.getText()))) {
            Toast.makeText(getApplicationContext(), "Invalid Voltage Date", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!entryBuilder.addDecimal(CellDataEntry.DISCHARGE_CAP, String.valueOf(dischargeEditText.getText()))) {
            Toast.makeText(getApplicationContext(), "Invalid Discharge Capacity", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!entryBuilder.addDecimal(CellDataEntry.INTERNAL_RES, String.valueOf(irEditText.getText()))) {
            Toast.makeText(getApplicationContext(), "Invalid Internal Resistance", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!entryBuilder.addDate(CellDataEntry.CAPACITY_DATE, String.valueOf(dischargeRecordedEditText.getText()))) {
            Toast.makeText(getApplicationContext(), "Invalid Discharge Capacity Date", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!entryBuilder.addDate(CellDataEntry.CHARGE_DATE, String.valueOf(lastChargedEditText.getText()))) {
            Toast.makeText(getApplicationContext(), "Invalid Last Charged Date", Toast.LENGTH_SHORT).show();
            return null;
        }

        Location cellLocation;

        if(cabinetButton.isChecked()) {
            cellLocation = new CabinetLocation();
        } else if(ht04Button.isChecked()) {
            cellLocation = new AccumulatorLocation(4);
        } else if(ht05Button.isChecked()) {
            cellLocation = new AccumulatorLocation(5);
        } else if(otherButton.isChecked()) {
            cellLocation = new OtherLocation();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
            return null;
        }

        cellLocation.addSpinnerInput(locationSpinner.getSelectedItem().toString());
        entryBuilder.addJSONObject(CellDataEntry.LOCATION, cellLocation.toDict());

        return entryBuilder.buildEntry();
    }
}
