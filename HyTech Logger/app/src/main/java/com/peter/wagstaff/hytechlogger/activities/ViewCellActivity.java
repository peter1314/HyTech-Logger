package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.customviews.EntrySpinner;
import com.peter.wagstaff.hytechlogger.customviews.LockedEditText;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.firebase.UpdateAction;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import org.json.JSONException;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import androidx.appcompat.app.AppCompatActivity;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;

public class ViewCellActivity extends AppCompatActivity {

    TextView entryText;
    LockedEditText voltageEditText, voltageRecordedEditText, dischargeEditText, irEditText, dischargeRecordedEditText, lastChargedEditText, locationEditText;
    EntrySpinner entrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cell);

        TextView cellCodeText = findViewById(R.id.cell_code_textView);
        cellCodeText.setText("Cell " + GlobalVariables.currentCellCode);

        entryText = findViewById(R.id.entry_textView);

        voltageEditText = findViewById(R.id.voltage_editText);
        voltageRecordedEditText = findViewById(R.id.voltage_recorded_editText);
        dischargeEditText = findViewById(R.id.capacity_editText);
        irEditText = findViewById(R.id.ir_editText);
        dischargeRecordedEditText = findViewById(R.id.min_voltage_editText);
        lastChargedEditText = findViewById(R.id.last_charged_editText);
        locationEditText = findViewById(R.id.location_editText);

        entrySpinner = findViewById(R.id.spinner);
        Button newEntryButton = findViewById(R.id.new_entry_button);

        FirebaseExchange.onUpdate(FirebaseExchange.BRANCH + "/" + GlobalVariables.currentCellCode + "/LOGS", new UpdateAction() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    populateFields(snapshot.child("LAST"));
                    entrySpinner.populate(snapshot);
                }
            }
        });

        entrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String date = entrySpinner.getSelectedItem().toString();

                FirebaseExchange.onGrab(FirebaseExchange.BRANCH + "/" + GlobalVariables.currentCellCode + "/LOGS/" + InputFormating.orderedDate(date), new UpdateAction() {
                    @Override
                    public void onUpdate(DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            populateFields(snapshot);
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        newEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ViewCellActivity.this, NewCellEntryActivity.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    private void populateFields(DataSnapshot snapshot) {
        CellDataEntry lastEntry = null;
        try {
            lastEntry = new CellDataEntry(snapshot.getValue().toString());
        } catch (JSONException e) {}

        entryText.setText(lastEntry.getData(CellDataEntry.AUTHOR) + ": " + lastEntry.getData(CellDataEntry.ENTRY_DATE));
        voltageEditText.setText(lastEntry.getData(CellDataEntry.VOLTAGE) + "");
        voltageRecordedEditText.setText((lastEntry.getData(CellDataEntry.VOLTAGE_DATE)));
        dischargeEditText.setText(lastEntry.getData(CellDataEntry.DISCHARGE_CAP) + "");
        irEditText.setText(lastEntry.getData(CellDataEntry.INTERNAL_RES) + "");
        dischargeRecordedEditText.setText(lastEntry.getData(CellDataEntry.CAPACITY_DATE));
        lastChargedEditText.setText(lastEntry.getData(CellDataEntry.CHARGE_DATE));

        Location location = LocationBuilder.buildLocation(lastEntry.getData(CellDataEntry.LOCATION));
        locationEditText.setText(location.fancyPrint());
    }
}
