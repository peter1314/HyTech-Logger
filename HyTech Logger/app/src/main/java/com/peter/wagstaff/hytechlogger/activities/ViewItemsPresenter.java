package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.LinearLayout;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.LocationCheckBox;
import com.peter.wagstaff.hytechlogger.customFragments.holders.ButtonTable;
import com.peter.wagstaff.hytechlogger.customFragments.holders.MinMaxHolder;
import com.peter.wagstaff.hytechlogger.customFragments.holders.QueryHolder;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.ItemEntryFilter;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.DecimalRangeTest;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.LocationTest;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.QueryTest;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.itemEntry.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.location.LocationConfiguration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import androidx.appcompat.app.AppCompatActivity;

public class ViewItemsPresenter extends AppCompatActivity {

    List<ItemEntry> allItems = new ArrayList<>();
    Set<LocationConfiguration> validLocationConfigs = new HashSet<>();

    List<LocationCheckBox> locationCheckBoxes = new LinkedList();
    List<MinMaxHolder> minMaxCriteria = new LinkedList();
    List<QueryHolder> queryCriteria = new LinkedList();

    ButtonTable<ItemEntry> itemTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        setLocationToggleBoxes();
        LinearLayout locationButtonLayout = findViewById(R.id.location_button_layout);
        for(LocationCheckBox toggleBox: locationCheckBoxes) {
            toggleBox.setToggle(validLocationConfigs, new ListnerAction() {
                @Override
                public void doAction(Object input) { filterItems(); }
            });
            locationButtonLayout.addView(toggleBox);
        }

        setMinMaxHolders();
        LinearLayout criteriaLayout = findViewById(R.id.criteria_layout);
        for(MinMaxHolder minMaxHolder: minMaxCriteria) {
            minMaxHolder.setUpdate(new ListnerAction() {
                @Override
                public void doAction(Object input) {
                    filterItems();
                }
            });
            criteriaLayout.addView(minMaxHolder);
        }

        setQueryHolders();
        for(QueryHolder queryHolder : queryCriteria) {
            queryHolder.setUpdate(new ListnerAction() {
                @Override
                public void doAction(Object input) { filterItems();
                }
            });
            criteriaLayout.addView(queryHolder);
        }

        itemTable = findViewById(R.id.tableLayout);
        itemTable.setListnerAction(new ListnerAction<ItemEntry>() {
            @Override
            public void doAction(ItemEntry entry) {
                GlobalVariables.currentEntryCode = entry.getData(Attributes.CODE.KEY);
                startActivity(getSelectIntent());
            }
        });

        getAllItems();
    }

    private void getAllItems() {
        FirebaseExchange.onUpdate(getType().BRANCH, new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    allItems.clear();

                    for(DataSnapshot child: snapshot.getChildren()) {
                        if(child.child("LOGS").child("LAST").exists()) {
                            allItems.add(FirebaseExchange.entryFromSnapshot(getType().BRANCH, child.child("LOGS").child("LAST")));
                        }
                    }
                    filterItems();
                }
            }
        });
    }

    private void filterItems() {
        ItemEntryFilter filter = new ItemEntryFilter();
        filter.addTest(new LocationTest(Attributes.LOCATION.KEY, validLocationConfigs));

        for(MinMaxHolder minMaxHolder: minMaxCriteria) {
            filter.addTest(new DecimalRangeTest(minMaxHolder.getAttributeID(), minMaxHolder.getMin(), minMaxHolder.getMax()));
        }
        for(QueryHolder queryHolder : queryCriteria) {
            filter.addTest((new QueryTest(queryHolder.getAttributeID(), queryHolder.getQuery())));
        }
        updateTable(filter.filterDataEntries(allItems));
    }

    private void updateTable(List<ItemEntry> filteredItems) {
        itemTable.removeAllViews();
        for(ItemEntry item: filteredItems) {
            itemTable.addRow(item.getType().NAME + " " + item.getData(Attributes.CODE.KEY), item);
        }
    }

    void setLocationToggleBoxes() {
        locationCheckBoxes.clear();
        for(LocationConfiguration config: getType().LOCATION_CONFIGS) {
            locationCheckBoxes.add(new LocationCheckBox(this, config));
        }
    }

    void setMinMaxHolders() {
        minMaxCriteria.clear();
        for(Attribute attribute: getType().TEST_ATTRIBUTES) {
            if(attribute.INPUT_TYPE == InputType.TYPE_NUMBER_FLAG_SIGNED || attribute.INPUT_TYPE == InputType.TYPE_NUMBER_FLAG_DECIMAL )
                minMaxCriteria.add(new MinMaxHolder(this, attribute));
        }
    }

    void setQueryHolders() {
        queryCriteria.clear();
        for(Attribute attribute: getType().TEST_ATTRIBUTES) {
            if(attribute.INPUT_TYPE == InputType.TYPE_CLASS_TEXT)
                queryCriteria.add(new QueryHolder(this, attribute));
        }
    }

    private ItemType getType() { return GlobalVariables.currentType; }

    private Intent getSelectIntent() { return new Intent(ViewItemsPresenter.this, ViewItemPresenter.class); }
}
