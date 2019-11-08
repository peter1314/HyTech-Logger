package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.CellEntry;
import com.peter.wagstaff.hytechlogger.DataUpdate;
import com.peter.wagstaff.hytechlogger.InputFormating;
import com.peter.wagstaff.hytechlogger.InputVerification;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;

import org.json.JSONException;

import java.util.Calendar;
import DatabaseInteraction.CellDataEntry;
import DatabaseInteraction.CellDataEntryBuilder;
import androidx.appcompat.app.AppCompatActivity;

public class NewCellEntryActivity extends AppCompatActivity {

    private boolean isNew = true;
    EditText voltageEditText, voltageRecordedEditText, dischargeEditText, irEditText, dischargeRecordedEditText, lastChargedEditText;
    RadioButton cabinetButton, ht04Button, ht05Button, otherButton;
    Spinner locationSpinner;

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

        Button enterButton = findViewById(R.id.enter_button);

        DataUpdate.onUpdate("CELLS2/" + GlobalVariables.currentCellCode + "/LOGS/LAST", new DataUpdate() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    isNew = false;
                    CellDataEntry lastEntry = null;
                    try {
                        lastEntry = new CellDataEntry(snapshot.getValue().toString());
                    } catch (JSONException e) {}

                    voltageEditText.setText(lastEntry.getData(CellDataEntry.VOLTAGE));
                    voltageRecordedEditText.setText((lastEntry.getData(CellDataEntry.VOLTAGE_DATE)));
                    dischargeEditText.setText(lastEntry.getData(CellDataEntry.DISCHARGE_CAP));
                    irEditText.setText(lastEntry.getData(CellDataEntry.INTERNAL_RES));
                    dischargeRecordedEditText.setText(lastEntry.getData(CellDataEntry.CAPACITY_DATE));
                    lastChargedEditText.setText(lastEntry.getData(CellDataEntry.CHARGE_DATE));

                    String[] locationData = lastEntry.getData(CellDataEntry.LOCATION).split("\\.", 2);

                    if(locationData[0].equals("Cabinet")) {
                        cabinetButton.setChecked(true);
                        populateSpinnerCabinet(locationSpinner);
                        locationSpinner.setSelection(InputVerification.selectionFromString(locationData[1]));
                    } else if(locationData[0].equals("HT04")) {
                        ht04Button.setChecked(true);
                        populateSpinnerAccumulator(locationSpinner);
                        locationSpinner.setSelection(InputVerification.selectionFromString(locationData[1]));
                    } else if(locationData[0].equals("HT05")) {
                        ht05Button.setChecked(true);
                        populateSpinnerAccumulator(locationSpinner);
                        locationSpinner.setSelection(InputVerification.selectionFromString(locationData[1]));
                    } else if(locationData[0].equals("Other")) {
                        otherButton.setChecked(true);
                        populateSpinnerOther(locationSpinner);
                        locationSpinner.setSelection(InputVerification.selectionFromString(locationData[1]));
                    }
                }
            }
        });

        cabinetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateSpinnerCabinet(locationSpinner);
            }
        });

        ht04Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateSpinnerAccumulator(locationSpinner);
            }
        });

        ht05Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateSpinnerAccumulator(locationSpinner);
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateSpinnerOther(locationSpinner);
            }
        });

        enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CellDataEntry entry = buildEntryFromInputs();

                if(entry == null) {return;}

                GlobalVariables.rootRef.child("CELLS2/" + GlobalVariables.currentCellCode + "/LOGS/LAST").setValue(entry.toString());
                GlobalVariables.rootRef.child("CELLS2/" + GlobalVariables.currentCellCode + "/LOGS/" + InputFormating.ORDER_DATE_FORMAT.format(Calendar.getInstance().getTime())).setValue(entry.toString());

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

    private void populateSpinnerCabinet(Spinner spinner) {
        String[] spinnerArray  = new String[9];
        for(int i = 0; i < 9; i++) {
            spinnerArray[i] = "Shelf " + (i + 1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
        spinner.setAdapter(adapter);
    }

    private void populateSpinnerAccumulator(Spinner spinner) {
        String[] spinnerArray  = new String[72];
        for(int i = 0; i < 72; i++) {
            spinnerArray[i] = "Segment " + (i / 18 + 1) + ", Cell " + ((i % 18) + 1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
        spinner.setAdapter(adapter);
    }

    private void populateSpinnerOther(Spinner spinner) {
        String[] spinnerArray  = {"Shop Space", "Lost", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerArray);
        spinner.setAdapter(adapter);
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

        String location = "";

        if(cabinetButton.isChecked()) {
            location = "Cabinet.";
        } else if(ht04Button.isChecked()) {
            location = "HT04.";
        } else if(ht05Button.isChecked()) {
            location = "HT05.";
        } else if(otherButton.isChecked()) {
            location = "Other.";
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
            return null;
        }

        location = location + InputFormating.correctLocation(locationSpinner.getSelectedItem().toString());
        entryBuilder.addString(CellDataEntry.LOCATION, location);

        return entryBuilder.buildEntry();
    }
}
