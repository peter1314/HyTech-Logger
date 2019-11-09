package com.peter.wagstaff.hytechlogger.activities;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.peter.wagstaff.hytechlogger.dataentry.CellDataEntry;
import com.peter.wagstaff.hytechlogger.dialogboxes.CodeInputDialog;
import com.google.zxing.Result;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

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

        final CodeInputDialog joinLineDialog = new CodeInputDialog(ScanActivity.this, "Enter Code", "", "") {

            @Override
            public void customNegative() {
                mScannerView.setResultHandler(thisHandler);
                mScannerView.startCamera();
            }

            @Override
            public boolean onPositiveClicked(final String userInput) {
                if(InputVerification.verifyCode(userInput)) {
                    GlobalVariables.currentEntryCode = userInput;

                    FirebaseExchange.onGrab(FirebaseExchange.TREE + "/" + CellDataEntry.BRANCH, new DataUpdateAction() {
                        @Override
                        public void doAction(DataSnapshot snapshot) {
                            if(snapshot.hasChild(userInput)) {
                                Intent intent = new Intent(ScanActivity.this, ViewCellActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                startActivity(new Intent(ScanActivity.this, NewCellEntryActivity.class));
                            }
                        }
                    });
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

            FirebaseExchange.onGrab(FirebaseExchange.TREE, new DataUpdateAction() {
                @Override
                public void doAction(DataSnapshot snapshot) {
                    if(snapshot.hasChild(readCode)) {
                        Intent intent = new Intent(ScanActivity.this, ViewCellActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(ScanActivity.this, NewCellEntryActivity.class));
                    }
                }
            });
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
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
