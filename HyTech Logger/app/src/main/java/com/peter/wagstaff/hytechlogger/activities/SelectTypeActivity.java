package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.holders.ButtonTable;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for selecting an ItemType
public abstract class SelectTypeActivity extends AppCompatActivity {

    private ButtonTable<ItemType> typeTable;    //Table to display and select ItemTypes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        //When ItemType is selected, set the Global ItemType to it and start next activity
        typeTable = findViewById(R.id.tableLayout);
        typeTable.setListnerAction(new ListnerAction<ItemType>() {
            @Override
            public void doAction(ItemType type) {
                GlobalVariables.currentItemType = type;
                startActivity(nextIntent());
            }
        });

        //Populate the typeTable with ItemTypes
        populateTable();
    }

    /**
     * Populate the typeTable with all the active ItemTypes
     */
    private void populateTable() {
        typeTable.removeAllViews();
        for(ItemType itemType: GlobalVariables.ACTIVE_ITEM_TYPES) {
            typeTable.addRow(itemType.NAME, itemType);
        }
    }

    /**
     * Get the next Intent when an ItemType is selected, contained in method in case of future change or overriding
     * @return The next Intent
     */
    abstract Intent nextIntent();
}
