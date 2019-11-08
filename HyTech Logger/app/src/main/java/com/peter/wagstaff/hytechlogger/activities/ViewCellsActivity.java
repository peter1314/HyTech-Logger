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
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.firebase.UpdateAction;
import com.peter.wagstaff.hytechlogger.inputs.InputFormating;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.location.Location;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;

import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewCellsActivity extends AppCompatActivity {

    Set<Map<String, Object>> validLocationConfigs;
    double minVolt, maxVolt, minCap, maxCap, minIR, maxIR;

    List<DataEntry> allCells = new ArrayList<>();
    List<DataEntry> filteredCells = new ArrayList<>();

    private static final Map<String, Object> cabinetConfig = LocationBuilder.buildConfig(new String[]{"type"}, new Object[]{"cabinet"});
    private static final Map<String, Object> ht04Config = LocationBuilder.buildConfig(new String[]{"type", "iteration"}, new Object[]{"accumulator", 4});
    private static final Map<String, Object> ht05Config = LocationBuilder.buildConfig(new String[]{"type", "iteration"}, new Object[]{"accumulator", 5});
    private static final Map<String, Object> otherConfig = LocationBuilder.buildConfig(new String[]{"type"}, new Object[]{"other"});

    TableLayout cellTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cells);

        validLocationConfigs = new HashSet<>();
        final CheckBox cabinetCheckBox = findViewById(R.id.cabinet_checkbox);
        validLocationConfigs.add(cabinetConfig);
        final CheckBox ht04CheckBox = findViewById(R.id.ht04_checkbox);
        validLocationConfigs.add(ht04Config);
        final CheckBox ht05CheckBox = findViewById(R.id.ht05_checkbox);
        validLocationConfigs.add(ht05Config);
        final CheckBox otherCheckBox = findViewById(R.id.other_checkbox);
        validLocationConfigs.add(otherConfig);

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
                    validLocationConfigs.add(cabinetConfig);
                } else {
                    validLocationConfigs.remove(cabinetConfig);
                }
                filterCells();
            }
        });

        ht04CheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ht04CheckBox.isChecked()) {
                    validLocationConfigs.add(ht04Config);
                } else {
                    validLocationConfigs.remove(ht04Config);
                }
                filterCells();
            }
        });

        ht05CheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ht05CheckBox.isChecked()) {
                    validLocationConfigs.add(ht05Config);
                } else {
                    validLocationConfigs.remove(ht05Config);
                }
                filterCells();
            }
        });

        otherCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (otherCheckBox.isChecked()) {
                    validLocationConfigs.add(otherConfig);
                } else {
                    validLocationConfigs.remove(otherConfig);
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

        FirebaseExchange.onUpdate(FirebaseExchange.BRANCH, new UpdateAction() {
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
                            System.out.println("Unfiltered Entry: " + lastEntry);
                        }
                    }
                }
            }
        });
    }

    private void filterCells() {

        DataEntryFilter filter = new DataEntryFilter();
        System.out.println("Location configs: " + validLocationConfigs);
        filter.addTest(new LocationTest(CellDataEntry.LOCATION, validLocationConfigs));
        filter.addTest(new DecimalTest(CellDataEntry.VOLTAGE, minVolt, maxVolt));
        filter.addTest(new DecimalTest(CellDataEntry.DISCHARGE_CAP, minCap, maxCap));
        filter.addTest(new DecimalTest(CellDataEntry.INTERNAL_RES, minIR, maxIR));

        filteredCells = filter.filterDataEntries(allCells);
        updateScroll();
    }

    private void updateScroll() {

        cellTable.removeAllViews();

        for(DataEntry filteredCell: filteredCells) {
            System.out.println("Filtered Entry: " + filteredCell);
            addRow(filteredCell);
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
