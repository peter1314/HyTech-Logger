package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.ListnerAction;
import com.peter.wagstaff.hytechlogger.customFragments.holders.TypeTableLayout;
import com.peter.wagstaff.hytechlogger.itemTypes.CellType;
import com.peter.wagstaff.hytechlogger.itemTypes.ItemType;
import com.peter.wagstaff.hytechlogger.itemTypes.StockType;
import androidx.appcompat.app.AppCompatActivity;

public abstract class SelectTypePresenter extends AppCompatActivity {

    TypeTableLayout typeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        typeTable = findViewById(R.id.tableLayout);
        typeTable.setListnerAction(new ListnerAction<ItemType>() {
            @Override
            public void doAction(ItemType type) {
                GlobalVariables.currentType = type;
                startActivity(nextIntent());
            }
        });

        populateTable();
    }

    private void populateTable() {
        typeTable.removeAllViews();

        for(ItemType itemType: GlobalVariables.ACTIVE_ITEM_TYPES) {
            typeTable.addRow(itemType);
        }
    }

    abstract Intent nextIntent();
}
