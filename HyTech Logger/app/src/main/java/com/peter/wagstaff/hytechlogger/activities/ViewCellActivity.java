package com.peter.wagstaff.hytechlogger.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.CellEntry;
import com.peter.wagstaff.hytechlogger.DataUpdate;
import com.peter.wagstaff.hytechlogger.GlobalFunctions;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ViewCellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cell);

        final TextView cellCodeText = findViewById(R.id.cell_code_textView);
        cellCodeText.setText("Cell " + GlobalVariables.currentCellCode);

        final TextView entryText = findViewById(R.id.entry_textView);

        final EditText voltageEditText = findViewById(R.id.voltage_editText);
        voltageEditText.setEnabled(false);
        voltageEditText.setTextColor(Color.BLACK);
        final EditText voltageRecordedEditText = findViewById(R.id.voltage_recorded_editText);
        voltageRecordedEditText.setEnabled(false);
        voltageRecordedEditText.setTextColor(Color.BLACK);
        final EditText dischargeEditText = findViewById(R.id.capacity_editText);
        dischargeEditText.setEnabled(false);
        dischargeEditText.setTextColor(Color.BLACK);
        final EditText irEditText = findViewById(R.id.ir_editText);
        irEditText.setEnabled(false);
        irEditText.setTextColor(Color.BLACK);
        final EditText dischargeRecordedEditText = findViewById(R.id.min_voltage_editText);
        dischargeRecordedEditText.setEnabled(false);
        dischargeRecordedEditText.setTextColor(Color.BLACK);
        final EditText lastChargedEditText = findViewById(R.id.last_charged_editText);
        lastChargedEditText.setEnabled(false);
        lastChargedEditText.setTextColor(Color.BLACK);
        final EditText locationEditText = findViewById(R.id.location_editText);
        locationEditText.setEnabled(false);
        locationEditText.setTextColor(Color.BLACK);

        final Spinner entrySpinner = findViewById(R.id.spinner);
        final Button newEntryButton = findViewById(R.id.new_entry_button);

        DataUpdate.onUpdate("CELLS/" + GlobalVariables.currentCellCode + "/LOGS/LAST", new DataUpdate() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    CellEntry lastEntry = CellEntry.fromString(snapshot.getValue().toString());

                    entryText.setText(lastEntry.getAuthor() + ": " + lastEntry.getEntryDate());
                    voltageEditText.setText(lastEntry.getVoltage() + "");
                    voltageRecordedEditText.setText((lastEntry.getVoltageDate()));
                    dischargeEditText.setText(lastEntry.getDischargeCapacity() + "");
                    irEditText.setText(lastEntry.getIR() + "");
                    dischargeRecordedEditText.setText(lastEntry.getCapacityDate());
                    lastChargedEditText.setText(lastEntry.getChargeDate());
                    locationEditText.setText(GlobalFunctions.reformLocation(lastEntry.getLocation()));
                }
            }
        });

        populateSpinnerEntries(this, entrySpinner);

        entrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String date = entrySpinner.getSelectedItem().toString();

                DataUpdate.onUpdate("CELLS/" + GlobalVariables.currentCellCode + "/LOGS/" + GlobalFunctions.orderedDate(date), new DataUpdate() {
                    @Override
                    public void onUpdate(DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            CellEntry curEntry = CellEntry.fromString(snapshot.getValue().toString());

                            entryText.setText(curEntry.getAuthor() + ": " + curEntry.getEntryDate());
                            voltageEditText.setText(curEntry.getVoltage() + "");
                            voltageRecordedEditText.setText((curEntry.getVoltageDate()));
                            dischargeEditText.setText(curEntry.getDischargeCapacity() + "");
                            irEditText.setText(curEntry.getIR() + "");
                            dischargeRecordedEditText.setText(curEntry.getCapacityDate());
                            lastChargedEditText.setText(curEntry.getChargeDate());
                            locationEditText.setText(GlobalFunctions.reformLocation(curEntry.getLocation()));
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ViewCellActivity.this, NewCellEntryActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    private void populateSpinnerEntries(final Context context, final Spinner spinner) {

        DataUpdate.onUpdate("CELLS/" + GlobalVariables.currentCellCode + "/LOGS", new DataUpdate() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    List<String> entryDates = new ArrayList<>();

                    for(DataSnapshot child: snapshot.getChildren()) {

                        CellEntry curEntry = CellEntry.fromString(child.getValue().toString());
                        entryDates.add(0, curEntry.getEntryDate());
                    }
                    entryDates.remove(0);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, entryDates.toArray(new String[entryDates.size()]));
                    spinner.setAdapter(adapter);
                }
            }
        });
    }
}
