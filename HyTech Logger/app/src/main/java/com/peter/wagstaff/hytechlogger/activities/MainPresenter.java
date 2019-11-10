package com.peter.wagstaff.hytechlogger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.scanItemPresenters.ScanCellPresenter;
import com.peter.wagstaff.hytechlogger.activities.scanItemPresenters.ScanStockPresenter;
import com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters.ViewCellsPresenter;
import com.peter.wagstaff.hytechlogger.activities.viewItemsPresenters.ViewStocksPresenter;

public class MainPresenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button scanCellButton = findViewById(R.id.scan_cell_button);
        final Button scanStockButton = findViewById(R.id.scan_stock_button);
        final Button viewCellButton = findViewById(R.id.view_cells_button);
        final Button viewStockButton = findViewById(R.id.view_stocks_button);
        final ImageButton profileButton = findViewById(R.id.profile_imageButton);

        scanCellButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainPresenter.this, ScanCellPresenter.class));
            }
        });

        scanStockButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainPresenter.this, ScanStockPresenter.class));
            }
        });

        viewCellButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainPresenter.this, ViewCellsPresenter.class));
            }
        });

        viewStockButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Moving");
                startActivity(new Intent(MainPresenter.this, ViewStocksPresenter.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainPresenter.this, ProfileActivity.class));
            }
        });

        if (ContextCompat.checkSelfPermission(MainPresenter.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainPresenter.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
