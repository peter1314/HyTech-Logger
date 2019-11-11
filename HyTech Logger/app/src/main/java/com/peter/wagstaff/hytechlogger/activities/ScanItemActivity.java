package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.zxing.Result;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.customFragments.dialogs.CodeInputBox;
import com.peter.wagstaff.hytechlogger.firebase.DataUpdateAction;
import com.peter.wagstaff.hytechlogger.firebase.FirebaseExchange;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

//Presenter for scanning (or manually entering) an item's code
public class ScanItemActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView; //Displays the camera input
    private ZXingScannerView.ResultHandler thisHandler;
    private Button enterCodeButton; //Button to manually enter a code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_item);

        mScannerView = findViewById(R.id.zxing_scanner_view);
        enterCodeButton = findViewById(R.id.enter_code_button);

        //Start the camera to scan a QR code
        thisHandler = this;
        mScannerView.setResultHandler(thisHandler);
        mScannerView.startCamera();

        //Finalizes the manual input dialog
        final CodeInputBox enterCodeDialog = getCodeInputDialog();

        //Add listener to the enterCodeButton
        enterCodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Stop the camera
                mScannerView.stopCamera();
                //Open enterCodeDialog
                enterCodeDialog.show();
            }
        });
    }

    /**
     * Returns a properly configured CodeInputBox
     * @return CodeInputBox capable of manually entering an item code
     */
    private CodeInputBox getCodeInputDialog() {
        return new CodeInputBox(ScanItemActivity.this, "Enter Code", "", "") {

            @Override
            public void customNegative() {
                //If the dialog is dismissed, restart the camera
                mScannerView.setResultHandler(thisHandler);
                mScannerView.startCamera();
            }

            @Override
            public boolean onPositiveClicked(final String userInput) {
                //Check if the manual input is valid
                if(InputVerification.verifyCode(userInput)) {
                    //If the input is valid, set the global ItemEntry code to the input
                    GlobalVariables.currentItemEntryCode = userInput;
                    openItem(userInput);
                    return true;
                }
                else {
                    //If the input is invalid, display an error Toast
                    Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        };
    }

    @Override
    public void handleResult(Result result) {
        final String readCode = result.getText();

        //Check if the scanned input is valid
        if(InputVerification.verifyCode(readCode)) {
            //If the input is valid, set the global ItemEntry code to the input
            GlobalVariables.currentItemEntryCode = readCode;
            openItem(readCode);
        }
        else {
            //If the input is invalid, display an error Toast and restart the camera
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

    /**
     * Opens an item given its code, behaves differently based on if the item already exists
     * @param code Code of item to open
     */
    public void openItem(final String code) {
        FirebaseExchange.onGrab(getBranch(), new DataUpdateAction() {
            @Override
            public void doAction(DataSnapshot snapshot) {
                if(snapshot.hasChild(code)) {
                    //If the item is already in the database
                    Intent intent = getItemExistsIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    //If the item is not already in the databse
                    startActivity(new Intent(getItemNewIntent()));
                }
            }
        });
    }

    /**
     * Gets the current branch the app is operating under, contained in method in case of future change or overriding
     * @return Branch of the Firebase database the app is currently operating under
     */
    private String getBranch() { return GlobalVariables.currentItemType.BRANCH; }

    /**
     * Get the next Intent when if the item exists, contained in method in case of future change or overriding
     * @return The next Intent
     */
    private Intent getItemExistsIntent() {
        Intent nextIntent = new Intent(ScanItemActivity.this, ViewItemActivity.class);
        nextIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return nextIntent;
    }

    /**
     * Get the next Intent when if the item does not exist, contained in method in case of future change or overriding
     * @return The next Intent
     */
    private Intent getItemNewIntent() { return new Intent(ScanItemActivity.this, ItemEntryActivity.class); }
}
