package com.peter.wagstaff.hytechlogger.activities;

import DatabaseInteraction.CellDataEntry;
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
import com.peter.wagstaff.hytechlogger.CellEntry;
import com.peter.wagstaff.hytechlogger.DataUpdate;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ViewCellsActivity extends AppCompatActivity {

    boolean cabinet = true, ht04 = true, ht05 = true, other = true;
    double minVolt, maxVolt, minCap, maxCap, minIR, maxIR;

    List<CellDataEntry> allCells = new ArrayList<>();
    List<CellDataEntry> filteredCells = new ArrayList<>();

    TableLayout cellTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cells);

        final CheckBox cabinetCheckBox = findViewById(R.id.cabinet_checkbox);
        final CheckBox ht04CheckBox = findViewById(R.id.ht04_checkbox);
        final CheckBox ht05CheckBox = findViewById(R.id.ht05_checkbox);
        final CheckBox otherCheckBox = findViewById(R.id.other_checkbox);

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
                cabinet = cabinetCheckBox.isChecked();
                filterCells();
            }
        });

        ht04CheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ht04 = ht04CheckBox.isChecked();
                filterCells();
            }
        });

        ht05CheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ht05 = ht05CheckBox.isChecked();
                filterCells();
            }
        });

        otherCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                other = otherCheckBox.isChecked();
                filterCells();
            }
        });

        minVoltageEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(minVoltageEditText.getText().toString().trim().length() == 0) {
                    minVolt = 0;
                } else {
                    minVolt = Double.parseDouble(String.valueOf(minVoltageEditText.getText()));
                }
                filterCells();
                return false;
            }
        });

        maxVoltageEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(maxVoltageEditText.getText().toString().trim().length() == 0) {
                    maxVolt = 0;
                } else {
                    maxVolt = Double.parseDouble(String.valueOf(maxVoltageEditText.getText()));
                }
                filterCells();
                return false;
            }
        });

        minCapacityEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(minCapacityEditText.getText().toString().trim().length() == 0) {
                    minCap = 0;
                } else {
                    minCap = Double.parseDouble(String.valueOf(minCapacityEditText.getText()));
                }
                filterCells();
                return false;
            }
        });

        maxCapacityEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(maxCapacityEditText.getText().toString().trim().length() == 0) {
                    maxCap = 0;
                } else {
                    maxCap = Double.parseDouble(String.valueOf(maxCapacityEditText.getText()));
                }
                filterCells();
                return false;
            }
        });

        minIREditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(minIREditText.getText().toString().trim().length() == 0) {
                    minIR = 0;
                } else {
                    minIR = Double.parseDouble(String.valueOf(minIREditText.getText()));
                }
                filterCells();
                return false;
            }
        });

        maxIREditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(maxIREditText.getText().toString().trim().length() == 0) {
                    maxIR = 0;
                } else {
                    maxIR = Double.parseDouble(String.valueOf(maxIREditText.getText()));
                }
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

        filteredCells.clear();

        for(CellDataEntry curEntry: allCells) {
            boolean include = true;

            String locationString = curEntry.getData(CellDataEntry.LOCATION);
            Double voltage = Double.parseDouble(curEntry.getData(CellDataEntry.VOLTAGE));
            Double dischargeCap = Double.parseDouble(curEntry.getData(CellDataEntry.DISCHARGE_CAP));
            Double ir = Double.parseDouble(curEntry.getData(CellDataEntry.INTERNAL_RES));

            if(locationString.contains("Cabinet") && !cabinet) { include = false; }
            else if(locationString.contains("HT04") && !ht04) { include = false; }
            else if(locationString.contains("HT05") && !ht05) { include = false; }
            else if(locationString.contains("Other") && !other) { include = false; }
            else if((minVolt != 0 && voltage <= minVolt) || (maxVolt != 0 && voltage >= maxVolt)) { include = false; }
            else if((minCap != 0 && dischargeCap <= minCap) || (maxCap != 0 && dischargeCap >= maxCap)) { include = false; }
            if((minIR != 0 && ir <= minIR) || (maxIR != 0 && ir >= maxIR)) { include = false; }

            if(include) { filteredCells.add(curEntry); }
        }

        updateScroll();
    }

    private void updateScroll() {

        cellTable.removeAllViews();

        for(CellDataEntry includedCell: filteredCells) {
            addRow(includedCell);
        }
    }

    private void addRow(final CellDataEntry entry) {
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
