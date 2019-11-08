package com.peter.wagstaff.hytechlogger.activities;

import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.tests.DataEntryFilter;
import com.peter.wagstaff.hytechlogger.dataentry.tests.DecimalTest;
import com.peter.wagstaff.hytechlogger.dataentry.tests.LocationTest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdate;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.R;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewCellsActivity extends AppCompatActivity {

    Set<String> validLocations;
    double minVolt, maxVolt, minCap, maxCap, minIR, maxIR;

    List<DataEntry> allCells = new ArrayList<>();
    List<DataEntry> filteredCells = new ArrayList<>();

    TableLayout cellTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cells);

        validLocations = new HashSet<>();
        final CheckBox cabinetCheckBox = findViewById(R.id.cabinet_checkbox);
        validLocations.add("Cabinet");
        final CheckBox ht04CheckBox = findViewById(R.id.ht04_checkbox);
        validLocations.add("HT04");
        final CheckBox ht05CheckBox = findViewById(R.id.ht05_checkbox);
        validLocations.add("HT05");
        final CheckBox otherCheckBox = findViewById(R.id.other_checkbox);
        validLocations.add("Other");

        cabinetCheckBox.setChecked(true);
        ht04CheckBox.setChecked(true);
        ht05CheckBox.setChecked(true);
        otherCheckBox.setChecked(true);

        final EditText minVoltageEditText = findViewById(R.id.min_voltage_editText);
        final EditText maxVoltageEditText = findViewById(R.id.max_voltage_editText);
        final EditText minCapacityEditText = findViewById(R.id.min_capacity_editText);
        final EditText maxCapacityEditText = findViewById(R.id.max_capacity_editText);
        final EditText minIREditText = findViewById(R.id.min_ir_editText);
        final EditText maxIREditText = findViewById(R.id.max_ir_editText);

        cellTable = (TableLayout) findViewById(R.id.tableLayout);
        getAllCells();

        cabinetCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (cabinetCheckBox.isChecked()) {
                    validLocations.add("Cabinet");
                } else {
                    validLocations.remove("Cabinet");
                }
                filterCells();
            }
        });

        ht04CheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ht04CheckBox.isChecked()) {
                    validLocations.add("HT04");
                } else {
                    validLocations.remove("HT04");
                }
                filterCells();
            }
        });

        ht05CheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ht05CheckBox.isChecked()) {
                    validLocations.add("HT05");
                } else {
                    validLocations.remove("HT05");
                }
                filterCells();
            }
        });

        otherCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (otherCheckBox.isChecked()) {
                    validLocations.add("Other");
                } else {
                    validLocations.remove("Other");
                }
                filterCells();
            }
        });

        minVoltageEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                minVolt = InputFormating.decimalFromString(minVoltageEditText.getText().toString());
                filterCells();
                return false;
            }
        });

        maxVoltageEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                maxVolt = InputFormating.decimalFromString(maxVoltageEditText.getText().toString());
                filterCells();
                return false;
            }
        });

        minCapacityEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                minCap = InputFormating.decimalFromString(minCapacityEditText.getText().toString());
                filterCells();
                return false;
            }
        });

        maxCapacityEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                maxCap = InputFormating.decimalFromString(maxCapacityEditText.getText().toString());
                filterCells();
                return false;
            }
        });

        minIREditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                minIR = InputFormating.decimalFromString(minIREditText.getText().toString());
                filterCells();
                return false;
            }
        });

        maxIREditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                maxIR = InputFormating.decimalFromString(maxIREditText.getText().toString());
                filterCells();
                return false;
            }
        });
    }

    private void getAllCells() {
        allCells.clear();

        DataUpdate.onUpdate("CELLS2", new DataUpdate() {
            @Override
            public void onUpdate(DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    allCells.clear();
                    cellTable.removeAllViews();

                    for(DataSnapshot child: snapshot.getChildren()) {
                        if(child.child("LOGS").child("LAST").exists()) {
                            CellDataEntry lastEntry = null;
                            try {
                                lastEntry = new CellDataEntry(child.child("LOGS").child("LAST").getValue().toString());
                            } catch (JSONException e) {}
                            allCells.add(lastEntry);
                            addRow(lastEntry);
                        }
                    }
                }
            }
        });
    }

    private void filterCells() {

        DataEntryFilter filter = new DataEntryFilter();
        filter.addTest(new LocationTest(CellDataEntry.LOCATION, validLocations));
        filter.addTest(new DecimalTest(CellDataEntry.VOLTAGE, minVolt, maxVolt));
        filter.addTest(new DecimalTest(CellDataEntry.DISCHARGE_CAP, minCap, maxCap));
        filter.addTest(new DecimalTest(CellDataEntry.INTERNAL_RES, minIR, maxIR));

        filteredCells = filter.filterDataEntries(allCells);
        updateScroll();
    }

    private void updateScroll() {

        cellTable.removeAllViews();

        for(DataEntry includedCell: filteredCells) {
            addRow(includedCell);
        }
    }

    private void addRow(final DataEntry entry) {
        TableRow newRow = new TableRow(this);
        newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        newRow.setGravity(Gravity.CENTER_HORIZONTAL);

        Button cellButton = new Button(this);
        cellButton.setText(entry.getData(CellDataEntry.CODE));
        cellButton.setWidth(cellTable.getWidth());
        cellButton.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
        cellButton.setTextColor(ContextCompat.getColor(this, R.color.pure_white));

        cellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.currentCellCode = entry.getData(CellDataEntry.CODE);
                startActivity(new Intent(ViewCellsActivity.this, ViewCellActivity.class));
            }
        });

        newRow.addView(cellButton);

        cellTable.addView(newRow);
    }
}
