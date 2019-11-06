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
import com.peter.wagstaff.hytechlogger.GlobalFunctions;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
public class NewCellEntryActivity extends AppCompatActivity {

    private boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cell_entry);

        final TextView cellCodeText = findViewById(R.id.cell_code_textView);
        cellCodeText.setText("Cell " + GlobalVariables.currentCellCode);

        final EditText voltageEditText = findViewById(R.id.voltage_editText);
        final EditText voltageRecordedEditText = findViewById(R.id.voltage_recorded_editText);
        final EditText dischargeEditText = findViewById(R.id.capacity_editText);
        final EditText irEditText = findViewById(R.id.ir_editText);
        final EditText dischargeRecordedEditText = findViewById(R.id.min_voltage_editText);
        final EditText lastChargedEditText = findViewById(R.id.last_charged_editText);

        final RadioButton cabinetButton = findViewById(R.id.cabinet_radioButton);
        final RadioButton ht04Button = findViewById(R.id.ht04_radioButton);
        final RadioButton ht05Button = findViewById(R.id.ht05_radioButton);
        final RadioButton otherButton = findViewById(R.id.other_radioButton);

        final Spinner locationSpinner = findViewById(R.id.spinner);

        final Button enterButton = findViewById(R.id.enter_button);

        DataUpdate.onUpdate("CELLS/" + GlobalVariables.currentCellCode + "/LOGS/LAST", new DataUpdate() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    isNew = false;
                    CellEntry lastEntry = CellEntry.fromString(snapshot.getValue().toString());

                    voltageEditText.setText(lastEntry.getVoltage() + "");
                    voltageRecordedEditText.setText((lastEntry.getVoltageDate()));
                    dischargeEditText.setText(lastEntry.getDischargeCapacity() + "");
                    irEditText.setText(lastEntry.getIR() + "");
                    dischargeRecordedEditText.setText(lastEntry.getCapacityDate());
                    lastChargedEditText.setText(lastEntry.getChargeDate());

                    String[] locationData = lastEntry.getLocation().split("\\.", 2);

                    if(locationData[0].equals("Cabinet")) {
                        cabinetButton.setChecked(true);
                        populateSpinnerCabinet(locationSpinner);
                        locationSpinner.setSelection(GlobalFunctions.selectionFromString(locationData[1]));
                    } else if(locationData[0].equals("HT04")) {
                        ht04Button.setChecked(true);
                        populateSpinnerAccumulator(locationSpinner);
                        locationSpinner.setSelection(GlobalFunctions.selectionFromString(locationData[1]));
                    } else if(locationData[0].equals("HT05")) {
                        ht05Button.setChecked(true);
                        populateSpinnerAccumulator(locationSpinner);
                        locationSpinner.setSelection(GlobalFunctions.selectionFromString(locationData[1]));
                    } else if(locationData[0].equals("Other")) {
                        otherButton.setChecked(true);
                        populateSpinnerOther(locationSpinner);
                        locationSpinner.setSelection(GlobalFunctions.selectionFromString(locationData[1]));
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

                CellEntry entry = new CellEntry();
                entry.setCode(GlobalVariables.currentCellCode);

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat of = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(c);
                entry.setEntryDate(formattedDate);

                entry.setAuthor(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                boolean fieldsValid = true;

                String voltage = String.valueOf(voltageEditText.getText());
                if(!GlobalFunctions.verifyDouble(voltage)) {
                    Toast.makeText(getApplicationContext(), "Invalid Voltage", Toast.LENGTH_SHORT).show();
                    return;
                }
                entry.setVoltage(Double.parseDouble(voltage));

                String voltageDate = String.valueOf(voltageRecordedEditText.getText());
                if(!GlobalFunctions.verifyDate(voltageDate)) {
                    Toast.makeText(getApplicationContext(), "Invalid Voltage Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                entry.setVoltageDate(GlobalFunctions.correctDate(voltageDate));

                String disCap = String.valueOf(dischargeEditText.getText());
                if(!GlobalFunctions.verifyDouble(disCap)) {
                    Toast.makeText(getApplicationContext(), "Invalid Discharge Capacity", Toast.LENGTH_SHORT).show();
                    return;
                }
                entry.setDischargeCapacity(Double.parseDouble(disCap));

                String ir = String.valueOf(irEditText.getText());
                if(!GlobalFunctions.verifyDouble(ir)) {
                    Toast.makeText(getApplicationContext(), "Invalid Internal Resistance", Toast.LENGTH_SHORT).show();
                    return;
                }
                entry.setIR(Double.parseDouble(ir));

                String capDate = String.valueOf(dischargeRecordedEditText.getText());
                if(!GlobalFunctions.verifyDate(capDate)) {
                    Toast.makeText(getApplicationContext(), "Invalid Discharge Capacity Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                entry.setCapacityDate(GlobalFunctions.correctDate(capDate));

                String chargeDate = String.valueOf(lastChargedEditText.getText());
                if(!GlobalFunctions.verifyDate(chargeDate)) {
                    Toast.makeText(getApplicationContext(), "Invalid Last Charged Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                entry.setChargeDate(GlobalFunctions.correctDate(chargeDate));

                String location = "";

                if(cabinetButton.isChecked()) {
                    location = "Cabinet.";
                }
                else if(ht04Button.isChecked()) {
                    location = "HT04.";
                }
                else if(ht05Button.isChecked()) {
                    location = "HT05.";
                } else if(otherButton.isChecked()) {
                    location = "Other.";
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Location", Toast.LENGTH_SHORT).show();
                    return;
                }

                location = location + GlobalFunctions.correctLocation(locationSpinner.getSelectedItem().toString());
                entry.setLocation(location);

                GlobalVariables.rootRef.child("CELLS/" + GlobalVariables.currentCellCode + "/LOGS/LAST").setValue(entry.toString());
                GlobalVariables.rootRef.child("CELLS/" + GlobalVariables.currentCellCode + "/LOGS/" + of.format(c)).setValue(entry.toString());

                System.out.println(isNew);
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
}
