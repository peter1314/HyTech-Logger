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
import com.peter.wagstaff.hytechlogger.activities.acountActivities.ProfileActivity;

//Presenter for the main application page
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button scanItemButton = findViewById(R.id.scan_item_button);
        final Button viewItemsButton = findViewById(R.id.view_items_button);
        final ImageButton profileButton = findViewById(R.id.profile_imageButton);

        //Set action for the scanItemButton
        scanItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectTypeScanActivity.class));
            }
        });

        //Set action for the viewItemsButton
        viewItemsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectTypeViewActivity.class));
            }
        });

        //Set action for the profileButton
        profileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        //Check if the user has given camera permission, ask for it if needed
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onBackPressed() {
        //Back press from the main page should leave the app
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
