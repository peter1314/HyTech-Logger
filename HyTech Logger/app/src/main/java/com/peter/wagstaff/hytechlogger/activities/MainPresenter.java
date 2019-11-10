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

public class MainPresenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button scanItemButton = findViewById(R.id.scan_item_button);
        final Button viewItemsButton = findViewById(R.id.view_items_button);
        final ImageButton profileButton = findViewById(R.id.profile_imageButton);

        scanItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startScanItemActivity();
            }
        });

        viewItemsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startViewItemsActivity();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainPresenter.this, ProfilePresenter.class));
            }
        });

        if (ContextCompat.checkSelfPermission(MainPresenter.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainPresenter.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    private void startScanItemActivity() {
        startActivity(new Intent(MainPresenter.this, SelectTypeScanPresenter.class));
    }

    private void startViewItemsActivity() {
        startActivity(new Intent(MainPresenter.this, SelectTypeViewPresenter.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
