package com.peter.wagstaff.hytechlogger.activities;

import com.peter.wagstaff.hytechlogger.activities.rowinjection.CellTableLayout;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.ListnerAction;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.LocationToggleBox;
import com.peter.wagstaff.hytechlogger.activities.rowinjection.MinMaxEditText;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.tests.DataEntryFilter;
import com.peter.wagstaff.hytechlogger.dataentry.tests.DecimalTest;
import com.peter.wagstaff.hytechlogger.dataentry.tests.LocationTest;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.location.LocationBuilder;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewCellsActivity extends AppCompatActivity {

    List<DataEntry> allCells = new ArrayList<>();

    Set<Map<String, Object>> validLocationConfigs = new HashSet<>();
    private static final Map<String, Object> cabinetConfig = LocationBuilder.buildConfig(new String[]{"type"}, new Object[]{"cabinet"});
    private static final Map<String, Object> ht04Config = LocationBuilder.buildConfig(new String[]{"type", "iteration"}, new Object[]{"accumulator", 4});
    private static final Map<String, Object> ht05Config = LocationBuilder.buildConfig(new String[]{"type", "iteration"}, new Object[]{"accumulator", 5});
    private static final Map<String, Object> otherConfig = LocationBuilder.buildConfig(new String[]{"type"}, new Object[]{"other"});

    CellTableLayout cellTable;
    MinMaxEditText minVoltageEditText, maxVoltageEditText, minCapacityEditText, maxCapacityEditText, minIREditText, maxIREditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cells);

        LocationToggleBox cabinetCheckBox = findViewById(R.id.cabinet_checkbox);
        cabinetCheckBox.setToggle(validLocationConfigs, cabinetConfig, new ListnerAction() {
            @Override
            public void doAction(Object input) { filterCells(); }
        });
        LocationToggleBox ht04CheckBox = findViewById(R.id.ht04_checkbox);
        ht04CheckBox.setToggle(validLocationConfigs, ht04Config, new ListnerAction() {
            @Override
            public void doAction(Object input) { filterCells();
            }
        });
        LocationToggleBox ht05CheckBox = findViewById(R.id.ht05_checkbox);
        ht05CheckBox.setToggle(validLocationConfigs, ht05Config, new ListnerAction() {
            @Override
            public void doAction(Object input) { filterCells();
            }
        });
        LocationToggleBox otherCheckBox = findViewById(R.id.other_checkbox);
        otherCheckBox.setToggle(validLocationConfigs, otherConfig, new ListnerAction() {
            @Override
            public void doAction(Object input) { filterCells();
            }
        });

        minVoltageEditText = findViewById(R.id.min_voltage_editText);
        minVoltageEditText.setUpdate(new ListnerAction() {
            @Override
            public void doAction(Object input) {
                filterCells();
            }
        });
        maxVoltageEditText = findViewById(R.id.max_voltage_editText);
        maxVoltageEditText.setUpdate(new ListnerAction() {
            @Override
            public void doAction(Object input) {
                filterCells();
            }
        });

        minCapacityEditText = findViewById(R.id.min_capacity_editText);
        minCapacityEditText.setUpdate(new ListnerAction() {
            @Override
            public void doAction(Object input) {
                filterCells();
            }
        });
        maxCapacityEditText = findViewById(R.id.max_capacity_editText);
        maxCapacityEditText.setUpdate(new ListnerAction() {
            @Override
            public void doAction(Object input) {
                filterCells();
            }
        });

        minIREditText = findViewById(R.id.min_ir_editText);
        minIREditText.setUpdate(new ListnerAction() {
            @Override
            public void doAction(Object input) {
                filterCells();
            }
        });
        maxIREditText = findViewById(R.id.max_ir_editText);
        maxIREditText.setUpdate(new ListnerAction() {
            @Override
            public void doAction(Object input) {
                filterCells();
            }
        });

        cellTable = findViewById(R.id.tableLayout);
        cellTable.setListnerAction(new ListnerAction<DataEntry>() {
            @Override
            public void doAction(DataEntry entry) {
                GlobalVariables.currentCellCode = entry.getData(CellDataEntry.CODE);
                startActivity(new Intent(ViewCellsActivity.this, ViewCellActivity.class));
            }
        });

        getAllCells();
    }

    private void getAllCells() {
        FirebaseExchange.onUpdate(FirebaseExchange.BRANCH, new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    allCells.clear();

                    for(DataSnapshot child: snapshot.getChildren()) {
                        if(child.child("LOGS").child("LAST").exists()) {
                            try {
                                allCells.add(new CellDataEntry(child.child("LOGS").child("LAST").getValue().toString()));
                            } catch (JSONException e) {}
                        }
                    }
                    filterCells();
                }
            }
        });
    }

    private void filterCells() {
        DataEntryFilter filter = new DataEntryFilter();
        filter.addTest(new LocationTest(CellDataEntry.LOCATION, validLocationConfigs));
        filter.addTest(new DecimalTest(CellDataEntry.VOLTAGE, minVoltageEditText.getValue(), maxVoltageEditText.getValue()));
        filter.addTest(new DecimalTest(CellDataEntry.DISCHARGE_CAP, minCapacityEditText.getValue(), maxCapacityEditText.getValue()));
        filter.addTest(new DecimalTest(CellDataEntry.INTERNAL_RES, minIREditText.getValue(), maxIREditText.getValue()));

        updateTable(filter.filterDataEntries(allCells));
    }

    private void updateTable(List<DataEntry> entries) {
        cellTable.removeAllViews();
        for(DataEntry filteredCell: entries) {
            cellTable.addRow(filteredCell);
        }
    }
}
