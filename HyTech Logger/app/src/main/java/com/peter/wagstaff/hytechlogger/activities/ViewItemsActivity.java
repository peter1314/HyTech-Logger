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
import com.peter.wagstaff.hytechlogger.firebase.FirebaseAdapter;
import com.peter.wagstaff.hytechlogger.itemEntry.ItemEntry;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.ItemEntryFilter;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.DecimalRangeTest;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.LocationTest;
import com.peter.wagstaff.hytechlogger.itemEntry.tests.QueryTest;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.attributes.Attribute;
import com.peter.wagstaff.hytechlogger.itemTypes.typeBuildingBlocks.Attributes;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.locations.LocationConfiguration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for viewing and searching all items of an ItemType
public class ViewItemsActivity extends AppCompatActivity {

    private List<ItemEntry> allItems = new ArrayList<>();   //List to store all items of an ItemType, items are represented by their last ItemEntry
    private Set<LocationConfiguration> validLocationConfigs = new HashSet<>();  //LocationConfigurations used to filter items
    private List<MinMaxHolder> minMaxCriteria = new LinkedList();   //MinMaxHolders used to filter items
    private List<QueryHolder> queryCriteria = new LinkedList();     //QueryHolders used to filter items
    private LinearLayout locationButtonLayout;  //LinearLayout to hold LocationCheckBoxes
    private LinearLayout criteriaLayout;    //LinearLayout to hold Attribute criteria
    private ButtonTable<ItemEntry> itemTable;   //Table to display and select items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        locationButtonLayout = findViewById(R.id.location_button_layout);
        criteriaLayout = findViewById(R.id.criteria_layout);
        itemTable = findViewById(R.id.tableLayout);

        //Fill all criteria selection layouts
        createLocationToggleBoxes();
        createMinMaxHolders();
        createQueryHolders();

        //Set action associated with item buttons
        itemTable.setListnerAction(new ListnerAction<ItemEntry>() {
            @Override
            public void doAction(ItemEntry entry) {
                //Set global code to the code of the selected item
                GlobalVariables.currentItemEntryCode = entry.getData(Attributes.CODE.KEY);
                //Go to next intent
                startActivity(getSelectIntent());
            }
        });

        //Set items using Firebase update
        setItemUpdate();
    }

    /**
     * Creates a Firebase update that gets all items under a branch
     */
    private void setItemUpdate() {
        FirebaseAdapter.onUpdate(getItemType().BRANCH, new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    //Clear items first
                    allItems.clear();
                    //Go through every item under the branch
                    for(DataSnapshot child: snapshot.getChildren()) {
                        //Add the last entry of the item to allItems
                        if(child.child("LOGS").child("LAST").exists()) {
                            allItems.add(FirebaseAdapter.entryFromSnapshot(getItemType(), child.child("LOGS").child("LAST")));
                        }
                    }
                    //Refilter items after update
                    filterItems();
                }
            }
        });
    }

    /**
     * Goes through all ItemEntries in allItems
     * Update itemTable with only those that pass all AttributeTests
     */
    private void filterItems() {
        //Create new filter
        ItemEntryFilter filter = new ItemEntryFilter();

        //Add the default LocationTest
        filter.addTest(new LocationTest(Attributes.LOCATION.KEY, validLocationConfigs));
        //Add all DecimalRangeTests that have non zero values
        for(MinMaxHolder minMaxHolder: minMaxCriteria) {
            if(minMaxHolder.getMin() != 0 || minMaxHolder.getMax() != 0) {
                filter.addTest(new DecimalRangeTest(minMaxHolder.ATTRIBUTE_ID, minMaxHolder.getMin(), minMaxHolder.getMax()));
            }
        }
        //Add all QueryTests
        for(QueryHolder queryHolder : queryCriteria) {
            filter.addTest((new QueryTest(queryHolder.ATTRIBUTE_ID, queryHolder.getQuery())));
        }
        //Update itemTable with the ItemEntries that pass all AttributeTests
        updateTable(filter.filterDataEntries(allItems));
    }

    /**
     * Clears the itemTable and adds all ItemEntries in a provided List
     * @param filteredItems List of ItemEntries to add to itemTable
     */
    private void updateTable(List<ItemEntry> filteredItems) {
        itemTable.removeAllViews();
        for(ItemEntry item: filteredItems) {
            itemTable.addRow(item.getType().NAME + " " + item.getData(Attributes.CODE.KEY), item);
        }
    }

    /**
     * Creates the LocationToggleBoxes based on the current ItemType
     */
    void createLocationToggleBoxes() {
        for(LocationConfiguration config: getItemType().LOCATION_CONFIGS) {
            LocationCheckBox toggleBox = new LocationCheckBox(this, config);
            toggleBox.setToggle(validLocationConfigs, new ListnerAction() {
                @Override
                public void doAction(Object input) { filterItems(); }
            });
            locationButtonLayout.addView(toggleBox);
        }
    }

    /**
     * Creates the MinMaxHolders based on the current ItemType
     */
    void createMinMaxHolders() {
        minMaxCriteria.clear();
        for(Attribute attribute: getItemType().TEST_ATTRIBUTES) {
            //Check for various number type inputs
            if(attribute.INPUT_TYPE == InputType.TYPE_NUMBER_FLAG_SIGNED
                    || attribute.INPUT_TYPE == InputType.TYPE_NUMBER_FLAG_DECIMAL
                    || attribute.INPUT_TYPE == InputType.TYPE_CLASS_NUMBER) {
                MinMaxHolder minMaxHolder = new MinMaxHolder(this, attribute);
                minMaxHolder.setUpdate(new ListnerAction() {
                    @Override
                    public void doAction(Object input) {
                        filterItems();
                    }
                });
                criteriaLayout.addView(minMaxHolder);
                minMaxCriteria.add(minMaxHolder);
            }
        }
    }

    /**
     * Creates the QueryHolders based on the current ItemType
     */
    void createQueryHolders() {
        queryCriteria.clear();
        for(Attribute attribute: getItemType().TEST_ATTRIBUTES) {
            if(attribute.INPUT_TYPE == InputType.TYPE_CLASS_TEXT) {
                QueryHolder queryHolder = new QueryHolder(this, attribute);
                queryHolder.setUpdate(new ListnerAction() {
                    @Override
                    public void doAction(Object input) { filterItems();
                    }
                });
                criteriaLayout.addView(queryHolder);
                queryCriteria.add(queryHolder);
            }
        }
    }

    /**
     * Get the current ItemType, contained in method in case of future change or overriding
     * @return Global currentItemType
     */
    private ItemType getItemType() { return GlobalVariables.currentItemType; }

    /**
     * Get the next Intent when an item is selected, contained in method in case of future change or overriding
     * @return The next Intent
     */
    private Intent getSelectIntent() { return new Intent(ViewItemsActivity.this, ViewItemActivity.class); }
}
