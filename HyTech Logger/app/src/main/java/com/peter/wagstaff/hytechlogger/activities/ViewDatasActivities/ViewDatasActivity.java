package com.peter.wagstaff.hytechlogger.activities.ViewDatasActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customviews.ListnerAction;
import com.peter.wagstaff.hytechlogger.customviews.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customviews.holders.DataTableLayout;
import com.peter.wagstaff.hytechlogger.customviews.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customviews.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.DataEntry;
import com.peter.wagstaff.hytechlogger.dataentry.tests.DataEntryFilter;
import com.peter.wagstaff.hytechlogger.dataentry.tests.DecimalTest;
import com.peter.wagstaff.hytechlogger.dataentry.tests.LocationTest;
import com.peter.wagstaff.hytechlogger.dataentry.tests.QueryTest;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ViewDatasActivity extends AppCompatActivity {

    List<DataEntry> allDatas = new ArrayList<>();
    Set<Map<String, Object>> validLocationConfigs = new HashSet<>();

    List<LocationCheckBox> locationCheckBoxes = new LinkedList();
    List<MinMaxHolder> minMaxCriteria = new LinkedList();
    List<QueryHolder> queryCriteria = new LinkedList();

    DataTableLayout dataTable;
    private final DataEntry entryOfType = getEntry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_datas);

        setLocationToggleBoxes();
        LinearLayout locationButtonLayout = findViewById(R.id.location_button_layout);
        for(LocationCheckBox toggleBox: locationCheckBoxes) {
            toggleBox.setToggle(validLocationConfigs, new ListnerAction() {
                @Override
                public void doAction(Object input) { filterDatas(); }
            });
            locationButtonLayout.addView(toggleBox);
        }

        setMinMaxHolders();
        LinearLayout criteriaLayout = findViewById(R.id.criteria_layout);
        for(MinMaxHolder minMaxHolder: minMaxCriteria) {
            minMaxHolder.setUpdate(new ListnerAction() {
                @Override
                public void doAction(Object input) {
                    filterDatas();
                }
            });
            criteriaLayout.addView(minMaxHolder);
        }

        setQueryHolders();
        for(QueryHolder queryHolder : queryCriteria) {
            queryHolder.setUpdate(new ListnerAction() {
                @Override
                public void doAction(Object input) { filterDatas();
                }
            });
            criteriaLayout.addView(queryHolder);
        }

        dataTable = findViewById(R.id.tableLayout);
        dataTable.setListnerAction(new ListnerAction<DataEntry>() {
            @Override
            public void doAction(DataEntry entry) {
                GlobalVariables.currentEntryCode = entry.getData(DataEntry.CODE.ID);
                startActivity(getSelectIntent());
            }
        });

        getAllDatas();
    }

    private void getAllDatas() {
        FirebaseExchange.onUpdate(FirebaseExchange.TREE + "/" + entryOfType.getBranch(), new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    allDatas.clear();

                    for(DataSnapshot child: snapshot.getChildren()) {
                        if(child.child("LOGS").child("LAST").exists()) {
                            try {
                                allDatas.add(new CellDataEntry(child.child("LOGS").child("LAST").getValue().toString()));
                            } catch (JSONException e) {}
                        }
                    }
                    filterDatas();
                }
            }
        });
    }

    private void filterDatas() {
        DataEntryFilter filter = new DataEntryFilter();
        filter.addTest(new LocationTest(DataEntry.LOCATION.ID, validLocationConfigs));

        for(MinMaxHolder minMaxHolder: minMaxCriteria) {
            filter.addTest(new DecimalTest(minMaxHolder.getAttributeID(), minMaxHolder.getMin(), minMaxHolder.getMax()));
        }
        for(QueryHolder queryHolder : queryCriteria) {
            filter.addTest((new QueryTest(queryHolder.getAttributeID(), queryHolder.getQuery())));
        }
        updateTable(filter.filterDataEntries(allDatas));
    }

    private void updateTable(List<DataEntry> entries) {
        dataTable.removeAllViews();
        for(DataEntry filteredCell: entries) {
            dataTable.addRow(filteredCell);
        }
    }

    abstract void setLocationToggleBoxes();

    abstract void setMinMaxHolders();

    abstract void setQueryHolders();

    abstract DataEntry getEntry();

    abstract Intent getSelectIntent();
}
