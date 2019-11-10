package com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customFragments.holders.DataTableLayout;
import com.peter.wagstaff.hytechlogger.customFragments.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customFragments.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.ItemEntryFilter;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.DecimalRangeTest;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.LocationTest;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.QueryTest;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ViewDatasPresenter extends AppCompatActivity {

    List<ItemEntry> allDatas = new ArrayList<>();
    Set<LocationConfiguration> validLocationConfigs = new HashSet<>();

    List<LocationCheckBox> locationCheckBoxes = new LinkedList();
    List<MinMaxHolder> minMaxCriteria = new LinkedList();
    List<QueryHolder> queryCriteria = new LinkedList();

    DataTableLayout dataTable;
    private final ItemEntry entryOfType = getEntry();

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
        dataTable.setListnerAction(new ListnerAction<ItemEntry>() {
            @Override
            public void doAction(ItemEntry entry) {
                GlobalVariables.currentEntryCode = entry.getData(ItemEntry.CODE.KEY);
                startActivity(getSelectIntent());
            }
        });

        getAllDatas();
    }

    private void getAllDatas() {
        FirebaseExchange.onUpdate(entryOfType.getBranch(), new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    allDatas.clear();

                    for(DataSnapshot child: snapshot.getChildren()) {
                        if(child.child("LOGS").child("LAST").exists()) {
                            allDatas.add(FirebaseExchange.entryFromSnapshot(entryOfType.getBranch(), child.child("LOGS").child("LAST")));
                        }
                    }
                    filterDatas();
                }
            }
        });
    }

    private void filterDatas() {
        ItemEntryFilter filter = new ItemEntryFilter();
        filter.addTest(new LocationTest(ItemEntry.LOCATION.KEY, validLocationConfigs));

        for(MinMaxHolder minMaxHolder: minMaxCriteria) {
            filter.addTest(new DecimalRangeTest(minMaxHolder.getAttributeID(), minMaxHolder.getMin(), minMaxHolder.getMax()));
        }
        for(QueryHolder queryHolder : queryCriteria) {
            filter.addTest((new QueryTest(queryHolder.getAttributeID(), queryHolder.getQuery())));
        }
        updateTable(filter.filterDataEntries(allDatas));
    }

    private void updateTable(List<ItemEntry> entries) {
        dataTable.removeAllViews();
        for(ItemEntry filteredCell: entries) {
            dataTable.addRow(filteredCell);
        }
    }

    abstract void setLocationToggleBoxes();

    abstract void setMinMaxHolders();

    abstract void setQueryHolders();

    abstract ItemEntry getEntry();

    abstract Intent getSelectIntent();
}
