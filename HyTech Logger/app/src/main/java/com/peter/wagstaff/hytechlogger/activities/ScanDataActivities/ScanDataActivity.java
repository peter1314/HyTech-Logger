package com.peter.wagstaff.hytechlogger.activities.ScanDataActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.zxing.Result;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.MainActivity;
import com.peter.wagstaff.hytechlogger.customviews.dialogboxes.CodeInputDialog;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public abstract class ScanDataActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ZXingScannerView.ResultHandler thisHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_cell);

        mScannerView = findViewById(R.id.zxing_scanner_view);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        thisHandler = this;

        final CodeInputDialog joinLineDialog = new CodeInputDialog(ScanDataActivity.this, "Enter Code", "", "") {

            @Override
            public void customNegative() {
                mScannerView.setResultHandler(thisHandler);
                mScannerView.startCamera();
            }

            @Override
            public boolean onPositiveClicked(final String userInput) {
                if(InputVerification.verifyCode(userInput)) {
                    GlobalVariables.currentEntryCode = userInput;
                    openDataEntry(userInput);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        };

        findViewById(R.id.enter_code_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mScannerView.stopCamera();
                joinLineDialog.show();
            }
        });
    }

    @Override
    public void handleResult(Result result) {
        final String readCode = result.getText();

        if(InputVerification.verifyCode(readCode)) {
            GlobalVariables.currentEntryCode = readCode;
            openDataEntry(readCode);
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_SHORT).show();
            mScannerView.setResultHandler(thisHandler);
            mScannerView.startCamera();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openDataEntry(final String code) {
        FirebaseExchange.onGrab(FirebaseExchange.TREE + "/" + getBranch(), new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.hasChild(code)) {
                    Intent intent = getDataExistsIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getDataNewIntent()));
                }
            }
        });
    }

    public abstract String getBranch();

    public abstract Intent getDataExistsIntent();

    public abstract Intent getDataNewIntent();
}
